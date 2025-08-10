package productapi.data;

// Import the Price model class
import productapi.model.Price;
// Import the Product model class
import productapi.model.Product;
// Import the ProductRepository interface to implement
import productapi.repository.ProductRepository;
// Spring annotation for marking this class as a repository component
import org.springframework.stereotype.Repository;

import java.util.List;

// Mark this class as a repository bean in the Spring context
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    // Implementation of the method to return all products
    @Override
    public List<Product> getAllProducts() {
        // Returning a fixed list of product objects with their prices and details
        return List.of(
                new Product("000001", "BV Lean leather ankle boots", "boots",
                        new Price(89000, 89000, "EUR")), // SKU, name, category, price object
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
