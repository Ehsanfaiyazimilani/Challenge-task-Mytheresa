package com.example.productapi.data;

import com.example.productapi.model.Price;
import com.example.productapi.model.Product;
import com.example.productapi.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    @Override
    public List<Product> getAllProducts() {
        return List.of(
                new Product("000001", "BV Lean leather ankle boots", "boots",
                        new Price(89000, 89000, "EUR")),
                new Product("000002", "BV Lean leather ankle boots", "boots",
                        new Price(99000, 99000, "EUR")),
                new Product("000003", "BV Lean leather ankle boots", "boots",
                        new Price(71000, 71000, "EUR")),
                new Product("000004", "Naima embellished suede sandals", "sandals",
                        new Price(79500, 79500, "EUR")),
                new Product("000005", "Nathane leather sneakers", "sneakers",
                        new Price(59000, 59000, "EUR"))
        );
    }
}