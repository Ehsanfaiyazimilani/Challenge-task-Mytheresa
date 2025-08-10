package productapi.service;

import productapi.model.Price;
import productapi.model.Product;
import productapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    // Constructor injection of the ProductRepository
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Method to get products filtered by category and max price with discounts applied
    public List<Product> getFilteredProducts(String category, Integer maxPrice) {
        List<Product> products = productRepository.getAllProducts();

        return products.stream()
                .map(this::applyDiscountLogic)  // Apply discount logic to each product
                .filter(product -> filterByCategory(product, category))  // Filter by category if provided
                .filter(product -> filterByPrice(product, maxPrice))  // Filter by max price if provided
                .collect(Collectors.toList());
    }

    // Apply discount to product if conditions match (e.g., 30% discount on boots)
    private Product applyDiscountLogic(Product product) {
        // Copy product to avoid modifying the original data
        Product discountedProduct = new Product(
                product.getSku(),
                product.getName(),
                product.getCategory(),
                new Price(product.getPrice().getOriginal(),
                        product.getPrice().getFinalPrice(),
                        product.getPrice().getCurrency())
        );

        // Apply 30% discount if product category is 'boots'
        if ("boots".equals(product.getCategory())) {
            int originalPrice = product.getPrice().getOriginal();
            int discountedPrice = Math.round(originalPrice * 0.7f); // 30% off

            Price discountedPriceObj = new Price(
                    originalPrice,
                    discountedPrice,
                    "30%",  // Discount percentage as string
                    product.getPrice().getCurrency()
            );

            discountedProduct.setPrice(discountedPriceObj);
        }

        return discountedProduct;
    }

    // Filter product by category, or accept all if category is null
    private boolean filterByCategory(Product product, String category) {
        return category == null || category.equals(product.getCategory());
    }

    // Filter product by max price, or accept all if maxPrice is null
    private boolean filterByPrice(Product product, Integer maxPrice) {
        return maxPrice == null || product.getPrice().getFinalPrice() <= maxPrice;
    }
}
