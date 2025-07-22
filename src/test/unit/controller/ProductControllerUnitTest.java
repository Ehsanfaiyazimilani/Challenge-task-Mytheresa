package com.example.productapi.unit.controller;

import com.example.productapi.controller.ProductController;
import com.example.productapi.model.Price;
import com.example.productapi.model.Product;
import com.example.productapi.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    @DisplayName("GET /products returns discounted filtered products")
    void testGetFilteredDiscountedProducts() throws Exception {
        Product product = new Product(
                "000001",
                "BV Lean leather ankle boots",
                "boots",
                new Price(89000, 62300, "30%", "EUR")
        );

        when(productService.getProducts("boots", 80000))
                .thenReturn(List.of(product));

        mockMvc.perform(get("/products")
                        .param("category", "boots")
                        .param("priceLessThan", "80000")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].sku").value("000001"))
                .andExpect(jsonPath("$[0].price.original").value(89000))
                .andExpect(jsonPath("$[0].price.final").value(62300))
                .andExpect(jsonPath("$[0].price.discount_percentage").value("30%"))
                .andExpect(jsonPath("$[0].price.currency").value("EUR"));
    }

    @Test
    @DisplayName("GET /products returns empty list when no product matches")
    void testGetProductsNoMatch() throws Exception {
        when(productService.getProducts("sneakers", 10000)).thenReturn(List.of());

        mockMvc.perform(get("/products")
                        .param("category", "sneakers")
                        .param("priceLessThan", "10000")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

@Test
@DisplayName("GET /products returns all products when no filters applied")
void testGetAllProductsWithoutFilters() throws Exception {
    Product p1 = new Product("000004", "Naima embellished suede sandals", "sandals", new Price(79500, 79500, null, "EUR"));
    Product p2 = new Product("000005", "Nathane leather sneakers", "sneakers", new Price(59000, 59000, null, "EUR"));

    when(productService.getProducts(null, null)).thenReturn(List.of(p1, p2));

    mockMvc.perform(get("/products").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].sku", notNullValue()))
            .andExpect(jsonPath("$[1].sku", notNullValue()));
}
