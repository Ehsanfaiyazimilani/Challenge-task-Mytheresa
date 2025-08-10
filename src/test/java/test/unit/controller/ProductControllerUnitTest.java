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
@ContextConfiguration(classes = productapi.ProductApiApplication.class)
public class ProductControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    @DisplayName("GET /products returns discounted filtered products")
    void testGetFilteredDiscountedProducts() throws Exception {
        // Given
        Product product = new Product(
                "000001",
                "BV Lean leather ankle boots",
                "boots",
                new Price(89000, 62300, "30%", "EUR")
        );

        when(productService.getFilteredProducts("boots", 80000))
                .thenReturn(List.of(product));

        // When & Then
        mockMvc.perform(get("/products")
                        .param("category", "boots")
                        .param("priceLessThan", "80000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].sku", is("000001")))
                .andExpect(jsonPath("$[0].name", is("BV Lean leather ankle boots")))
                .andExpect(jsonPath("$[0].category", is("boots")))
                .andExpect(jsonPath("$[0].price.original", is(89000)))
                .andExpect(jsonPath("$[0].price.finalPrice").value(62300))
                .andExpect(jsonPath("$[0].price.discountPercentage", is("30%")))
                .andExpect(jsonPath("$[0].price.currency", is("EUR")));
    }
}