package test.unit.service;

import com.example.productapi.model.Price;
import com.example.productapi.model.Product;
import com.example.productapi.repository.ProductRepository;
import com.example.productapi.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository);

        // محصولات نمونه با قیمت اصلی - تخفیف در سرویس اعمال می‌شود
        List<Product> mockProducts = List.of(
                new Product("000001", "BV Lean leather ankle boots", "boots",
                        new Price(89000, 89000, "EUR")),
                new Product("000002", "BV Lean leather sneakers", "sneakers",
                        new Price(59000, 59000, "EUR")),
                new Product("000003", "Ash leather boots", "boots",
                        new Price(71000, 71000, "EUR")),
                new Product("000004", "Naima embellished suede sandals", "sandals",
                        new Price(79500, 79500, "EUR"))
        );

        when(productRepository.getAllProducts()).thenReturn(mockProducts);
    }

    @Test
    @DisplayName("Returns all products without filters")
    void shouldReturnAllProductsIfNoFilter() {
        List<Product> result = productService.getFilteredProducts(null, null);

        assertThat(result).hasSize(4);
        verify(productRepository, times(1)).getAllProducts();
    }

    @Test
    @DisplayName("Filters by category only")
    void shouldFilterByCategory() {
        List<Product> result = productService.getFilteredProducts("boots", null);

        assertThat(result).hasSize(2);
        assertThat(result).allMatch(p -> p.getCategory().equals("boots"));
        verify(productRepository, times(1)).getAllProducts();
    }

    @Test
    @DisplayName("Filters by price only")
    void shouldFilterByPrice() {
        List<Product> result = productService.getFilteredProducts(null, 80000);

        // محصولات boots با تخفیف 30% قیمت نهایی کمتری دارند
        assertThat(result).hasSizeGreaterThan(0);
        assertThat(result).allMatch(p -> p.getPrice().getFinalPrice() <= 80000);
        verify(productRepository, times(1)).getAllProducts();
    }

    @Test
    @DisplayName("Filters by category and price")
    void shouldFilterByCategoryAndPrice() {
        List<Product> result = productService.getFilteredProducts("boots", 80000);

        // محصولات boots با تخفیف 30% که قیمت نهایی‌شان زیر 80000 است
        assertThat(result).hasSizeGreaterThan(0);
        assertThat(result).allMatch(p -> p.getCategory().equals("boots"));
        assertThat(result).allMatch(p -> p.getPrice().getFinalPrice() <= 80000);
        verify(productRepository, times(1)).getAllProducts();
    }

    @Test
    @DisplayName("Applies 30% discount to category 'boots'")
    void shouldApplyDiscountToBoots() {
        List<Product> result = productService.getFilteredProducts("boots", null);

        Product discountedProduct = result.stream()
                .filter(p -> p.getSku().equals("000001"))
                .findFirst()
                .orElseThrow();

        Price price = discountedProduct.getPrice();
        assertThat(price.getOriginal()).isEqualTo(89000);
        assertThat(price.getFinalPrice()).isEqualTo(62300);  // 30% تخفیف
        assertThat(price.getDiscountPercentage()).isEqualTo("30%");
        assertThat(price.getCurrency()).isEqualTo("EUR");
        verify(productRepository, times(1)).getAllProducts();
    }

    @Test
    @DisplayName("No discount applied to non-boots")
    void shouldNotApplyDiscountToOtherCategories() {
        List<Product> result = productService.getFilteredProducts("sneakers", null);

        Product product = result.get(0);
        Price price = product.getPrice();

        assertThat(price.getOriginal()).isEqualTo(59000);
        assertThat(price.getFinalPrice()).isEqualTo(59000);
        assertThat(price.getDiscountPercentage()).isNull();
        assertThat(price.getCurrency()).isEqualTo("EUR");
        verify(productRepository, times(1)).getAllProducts();
    }
}