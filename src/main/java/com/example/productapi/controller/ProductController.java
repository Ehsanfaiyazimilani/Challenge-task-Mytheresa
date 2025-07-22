package com.example.productapi.controller;

import com.example.productapi.model.Product;
import com.example.productapi.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<Product> getProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer priceLessThan,
            @RequestParam(defaultValue = "0") int page,         
            @RequestParam(defaultValue = "20") int size         
    ) {
        if (priceLessThan != null && priceLessThan < 0) {
            throw new IllegalArgumentException("priceLessThan must be non-negative");
        }
        if (page < 0) {
            throw new IllegalArgumentException("page must be non-negative");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("size must be positive");
        }

        return service.getProducts(category, priceLessThan, page, size);
    }
}
