package test.unit.service;

import productapi.model.Price;
import productapi.model.Product;
import productapi.repository.ProductRepository;
import productapi.service.ProductService;
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
// Use Mockito extension to enable mocking annotations
public class ProductServiceTest {

    private ProductService productService;

    @Mock
    private ProductRepository productRepository;
    // Mock the repository dependency

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository);
        // Initialize service with mocked repository

        // Prepare sample products with original prices; discounts are applied in service
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

        // Mock the getAllProducts method to return the sample list
        when(productRepository.getAllProducts()).thenReturn(mockProducts);
    }

    @Test
    @DisplayName("Returns all products without filters")
    void shouldReturnAllProductsIfNoFilter() {
        // Call service with no filters
        List<Product> result = productService.getFilteredProducts(null, null);

        // Verify all 4 products are returned
        assertThat(result).hasSize(4);
        // Verify repository was called exactly once
        verify(productRepository, times(1)).getAllProducts();
    }

    @Test
    @DisplayName("Filters by category only")
    void shouldFilterByCategory() {
        // Call service filtering by category "boots"
        List<Product> result = productService.getFilteredProducts("boots", null);

        // Check the result contains exactly 2 products with category "boots"
        assertThat(result).hasSize(2);
        assertThat(result).allMatch(p -> p.getCategory().equals("boots"));
        verify(productRepository, times(1)).getAllProducts();
    }

    @Test
    @DisplayName("Filters by price only")
    void shouldFilterByPrice() {
        // Call service filtering products with price <= 80000
        List<Product> result = productService.getFilteredProducts(null, 80000);

        // Result size should be greater than 0
        assertThat(result).hasSizeGreaterThan(0);
        // All products must have finalPrice <= 80000 (after discount if any)
        assertThat(result).allMatch(p -> p.getPrice().getFinalPrice() <= 80000);
        verify(productRepository, times(1)).getAllProducts();
    }

    @Test
    @DisplayName("Filters by category and price")
    void shouldFilterByCategoryAndPrice() {
        // Call service filtering by category "boots" and price <= 80000
        List<Product> result = productService.getFilteredProducts("boots", 80000);

        // Check results meet both conditions
        assertThat(result).hasSizeGreaterThan(0);
        assertThat(result).allMatch(p -> p.getCategory().equals("boots"));
        assertThat(result).allMatch(p -> p.getPrice().getFinalPrice() <= 80000);
        verify(productRepository, times(1)).getAllProducts();
    }

    @Test
    @DisplayName("Applies 30% discount to category 'boots'")
    void shouldApplyDiscountToBoots() {
        // Call service filtering by category "boots"
        List<Product> result = productService.getFilteredProducts("boots", null);

        // Find specific product with SKU "000001"
        Product discountedProduct = result.stream()
                .filter(p -> p.getSku().equals("000001"))
                .findFirst()
                .orElseThrow();

        // Check discount applied correctly
        Price price = discountedProduct.getPrice();
        assertThat(price.getOriginal()).isEqualTo(89000);
        assertThat(price.getFinalPrice()).isEqualTo(62300);  // 30% discount applied
        assertThat(price.getDiscountPercentage()).isEqualTo("30%");
        assertThat(price.getCurrency()).isEqualTo("EUR");
        verify(productRepository, times(1)).getAllProducts();
    }

    @Test
    @DisplayName("No discount applied to non-boots")
    void shouldNotApplyDiscountToOtherCategories() {
        // Call service filtering by category "sneakers"
        List<Product> result = productService.getFilteredProducts("sneakers", null);

        Product product = result.get(0);
        Price price = product.getPrice();

        // Validate no discount applied, original price equals final price, and discountPercentage is null
        assertThat(price.getOriginal()).isEqualTo(59000);
        assertThat(price.getFinalPrice()).isEqualTo(59000);
        assertThat(price.getDiscountPercentage()).isNull();
        assertThat(price.getCurrency()).isEqualTo("EUR");
        verify(productRepository, times(1)).getAllProducts();
    }
}
