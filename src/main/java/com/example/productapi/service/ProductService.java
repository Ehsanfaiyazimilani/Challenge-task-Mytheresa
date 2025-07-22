package com.example.productapi.service;

import com.example.productapi.data.ProductRepository;
import com.example.productapi.model.Price;
import com.example.productapi.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getProducts(String category, Integer priceLessThan, int page, int size) {
        logger.info("getProducts called with category={}, priceLessThan={}, page={}, size={}",
                category, priceLessThan, page, size);

        long start = System.currentTimeMillis();

        List<Product> filteredProducts = repository.getAllProducts().stream()
                .filter(p -> category == null || p.getCategory().equalsIgnoreCase(category))
                .filter(p -> priceLessThan == null || p.getOriginalPrice() <= priceLessThan)
                .collect(Collectors.toList());

        logger.info("Number of products after filtering: {}", filteredProducts.size());

        List<Product> pagedProducts = filteredProducts.stream()
                .skip((long) page * size)
                .limit(size)
                .map(this::applyDiscount)
                .collect(Collectors.toList());

        long end = System.currentTimeMillis();
        logger.info("Returning {} products for page {} (processed in {} ms)", pagedProducts.size(), page, (end - start));
        return pagedProducts;
    }

    private Product applyDiscount(Product p) {
        int originalPrice = p.getOriginalPrice();
        int discount = 0;

        if ("boots".equalsIgnoreCase(p.getCategory())) {
            discount = 30;
        }
        if ("000003".equals(p.getSku())) {
            discount = Math.max(discount, 15);
        }

        int finalPrice = (discount > 0) ? originalPrice * (100 - discount) / 100 : originalPrice;
        String discountPercentage = (discount > 0) ? discount + "%" : null;

        p.setPrice(new Price(originalPrice, finalPrice, discountPercentage));

        if (discount > 0) {
            logger.debug("Applied discount {} to product SKU={}, originalPrice={}, finalPrice={}",
                    discount + "%", p.getSku(), originalPrice, finalPrice);
        }

        return p;
    }
}
