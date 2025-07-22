package com.example.productapi.data;

import com.example.productapi.model.Product;
import java.util.List;

public interface ProductRepository {
    List<Product> getAllProducts();
}
