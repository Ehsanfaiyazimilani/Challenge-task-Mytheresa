package com.example.productapi.repository;

import com.example.productapi.model.Product;
import java.util.List;

public interface ProductRepository {
    List<Product> getAllProducts();
}