package test.unit.controller;

import productapi.controller.ProductController;
import productapi.model.Price;
import productapi.model.Product;
import productapi.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
// Setup Spring MVC test environment only for ProductController
@ContextConfiguration(classes = productapi.ProductApiApplication.class)
// Load full application context for configuration
public class ProductControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;  // Inject MockMvc to simulate HTTP calls to controller

    @MockBean
    private ProductService productService;  // Mock ProductService to control its behavior

    @Test
    @DisplayName("GET /products returns discounted filtered products")
        // Test method with descriptive name to clarify the test purpose
    void testGetFilteredDiscountedProducts() throws Exception {
        // Given: prepare a sample product with discount applied
        Product product = new Product(
                "000001",
                "BV Lean leather ankle boots",
                "boots",
                new Price(89000, 62300, "30%", "EUR")  // Original price 89000, discounted final price 62300
        );

        // Mock the service method to return the prepared product list when called with specific params
        when(productService.getFilteredProducts("boots", 80000))
                .thenReturn(List.of(product));

        // When & Then: perform GET request with parameters and verify the JSON response and HTTP status
        mockMvc.perform(get("/products")
                        .param("category", "boots")
                        .param("priceLessThan", "80000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Expect HTTP 200 OK
                .andExpect(jsonPath("$", hasSize(1)))  // Expect exactly one product in response list
                .andExpect(jsonPath("$[0].sku", is("000001")))  // Validate product SKU
                .andExpect(jsonPath("$[0].name", is("BV Lean leather ankle boots")))  // Validate name
                .andExpect(jsonPath("$[0].category", is("boots")))  // Validate category
                .andExpect(jsonPath("$[0].price.original", is(89000)))  // Validate original price
                .andExpect(jsonPath("$[0].price.finalPrice").value(62300))  // Validate discounted final price
                .andExpect(jsonPath("$[0].price.discountPercentage", is("30%")))  // Validate discount percentage string
                .andExpect(jsonPath("$[0].price.currency", is("EUR")));  // Validate currency code
    }
}
