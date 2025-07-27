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

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getFilteredProducts(String category, Integer maxPrice) {
        List<Product> products = productRepository.getAllProducts();

        return products.stream()
                .map(this::applyDiscountLogic) // اعمال تخفیف
                .filter(product -> filterByCategory(product, category))
                .filter(product -> filterByPrice(product, maxPrice))
                .collect(Collectors.toList());
    }

    private Product applyDiscountLogic(Product product) {
        // کپی محصول برای جلوگیری از تغییر داده اصلی
        Product discountedProduct = new Product(
                product.getSku(),
                product.getName(),
                product.getCategory(),
                new Price(product.getPrice().getOriginal(),
                        product.getPrice().getFinalPrice(),
                        product.getPrice().getCurrency())
        );

        // اعمال تخفیف 30% برای کتگوری boots
        if ("boots".equals(product.getCategory())) {
            int originalPrice = product.getPrice().getOriginal();
            int discountedPrice = Math.round(originalPrice * 0.7f); // 30% تخفیف

            Price discountedPriceObj = new Price(
                    originalPrice,
                    discountedPrice,
                    "30%",
                    product.getPrice().getCurrency()
            );

            discountedProduct.setPrice(discountedPriceObj);
        }

        return discountedProduct;
    }

    private boolean filterByCategory(Product product, String category) {
        return category == null || category.equals(product.getCategory());
    }

    private boolean filterByPrice(Product product, Integer maxPrice) {
        return maxPrice == null || product.getPrice().getFinalPrice() <= maxPrice;
    }
}