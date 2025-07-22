package com.example.productapi.unit.service;

import com.example.productapi.model.Price;
import com.example.productapi.model.Product;
import com.example.productapi.repository.ProductRepository;
import com.example.productapi.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    private ProductService productService;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);

        List<Product> mockProducts = List.of(
                new Product("000001", "BV Lean leather ankle boots", "boots", null),
                new Product("000002", "BV Lean leather sneakers", "sneakers", null),
                new Product("000003", "Ash leather boots", "boots", null),
                new Product("000004", "Naima embellished suede sandals", "sandals", null)
        );

        when(productRepository.getAllProducts()).thenReturn(mockProducts);
    }

    @Test
    @DisplayName("Returns all products without filters")
    void shouldReturnAllProductsIfNoFilter() {
        List<Product> result = productService.getFilteredProducts(null, null);

        assertThat(result).hasSize(4);
    }

    @Test
    @DisplayName("Filters by category only")
    void shouldFilterByCategory() {
        List<Product> result = productService.getFilteredProducts("boots", null);

        assertThat(result).hasSize(2);
        assertThat(result).allMatch(p -> p.getCategory().equals("boots"));
    }

    @Test
    @DisplayName("Filters by price only")
    void shouldFilterByPrice() {
        List<Product> result = productService.getFilteredProducts(null, 80000);

        // Product 000001 has discount and becomes 62300
        assertThat(result).anyMatch(p -> p.getSku().equals("000001"));
    }

    @Test
    @DisplayName("Filters by category and price")
    void shouldFilterByCategoryAndPrice() {
        List<Product> result = productService.getFilteredProducts("boots", 80000);

        // Only 000001 matches (boots + discounted price 62300)
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getSku()).isEqualTo("000001");
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
        assertThat(price.getFinalPrice()).isEqualTo(62300);
        assertThat(price.getDiscountPercentage()).isEqualTo("30%");
        assertThat(price.getCurrency()).isEqualTo("EUR");
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
    }
}

