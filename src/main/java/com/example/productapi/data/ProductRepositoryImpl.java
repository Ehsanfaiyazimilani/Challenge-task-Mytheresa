package com.example.productapi.data;

import com.example.productapi.model.Product;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final List<Product> products = new ArrayList<>();

    @PostConstruct
    public void init() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = getClass().getClassLoader().getResourceAsStream("products.json");
            JsonNode root = mapper.readTree(is).get("products");
            for (JsonNode node : root) {
                String sku = node.get("sku").asText();
                String name = node.get("name").asText();
                String category = node.get("category").asText();
                int price = node.get("price").asInt();

                Product product = new Product(sku, name, category, price);  // originalPrice set here
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return products;
    }
}
