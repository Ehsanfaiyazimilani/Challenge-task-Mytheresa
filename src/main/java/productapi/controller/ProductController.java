package productapi.controller;

// Importing the Product model class
import productapi.model.Product;
// Importing the ProductService class to use business logic
import productapi.service.ProductService;
// Spring annotations and classes for dependency injection and HTTP responses
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Marks this class as a REST controller to handle HTTP requests
@RestController
// Base URL mapping for all endpoints in this controller
@RequestMapping("/products")
public class ProductController {

    // Service layer to handle product-related business logic
    private final ProductService service;

    // Constructor injection of ProductService using Spring's @Autowired
    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    // Handles GET requests to /products endpoint
    @GetMapping
    public ResponseEntity<List<Product>> getProducts(
            // Optional query parameter for filtering products by category
            @RequestParam(required = false) String category,
            // Optional query parameter for filtering products with price less than this value
            @RequestParam(required = false) Integer priceLessThan) {

        // Call service method to get filtered list of products
        List<Product> products = service.getFilteredProducts(category, priceLessThan);
        // Return HTTP 200 OK response with the list of products in JSON
        return ResponseEntity.ok(products);
    }
}
