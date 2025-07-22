@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProductApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void fullRequestShouldReturnCorrectDiscountedProducts() throws Exception {
        mockMvc.perform(get("/products?category=boots&priceLessThan=80000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))))
                .andExpect(jsonPath("$[0].price.final", lessThanOrEqualTo(80000)));
    }
}
