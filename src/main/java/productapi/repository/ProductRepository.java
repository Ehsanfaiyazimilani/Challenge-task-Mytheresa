package productapi.repository;

import productapi.model.Product;
import java.util.List;

public interface ProductRepository {
    List<Product> getAllProducts();
}