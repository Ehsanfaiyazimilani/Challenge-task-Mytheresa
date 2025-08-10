package test.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = productapi.ProductApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// Starts the Spring context and runs tests with a random port
@AutoConfigureMockMvc
// Enables and configures MockMvc for testing HTTP endpoints
public class ProductApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;  // Inject MockMvc to simulate HTTP requests

    @Test
    void fullRequestShouldReturnCorrectDiscountedProducts() throws Exception {
        // Perform a GET request to /products with category "boots" and price less than 80000
        mockMvc.perform(get("/products?category=boots&priceLessThan=80000"))
                .andExpect(status().isOk())  // Expect HTTP 200 OK
                .andExpect(jsonPath("$", hasSize(greaterThan(0))))  // Expect non-empty product list
                .andExpect(jsonPath("$[0].price.finalPrice", lessThanOrEqualTo(80000)));  // First product's final price <= 80000
    }
}
