package productapi.repository;

import productapi.model.Product;
import java.util.List;

// Interface for product data access operations
public interface ProductRepository {
    // Method to get a list of all products
    List<Product> getAllProducts();
}
