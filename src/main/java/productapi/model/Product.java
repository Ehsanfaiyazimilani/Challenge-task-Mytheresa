package productapi.model;

// Product class represents a product entity in the system
public class Product {
    // SKU (Stock Keeping Unit) - unique identifier for the product
    private String sku;
    // Name of the product
    private String name;
    // Category to which the product belongs (e.g., boots, sneakers)
    private String category;
    // Price object containing price details such as original price, final price, discount, and currency
    private Price price;

    // Constructor to initialize all fields of the Product
    public Product(String sku, String name, String category, Price price) {
        this.sku = sku;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    // Getter for SKU
    public String getSku() {
        return sku;
    }

    // Setter for SKU
    public void setSku(String sku) {
        this.sku = sku;
    }

    // Getter for product name
    public String getName() {
        return name;
    }

    // Setter for product name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for product category
    public String getCategory() {
        return category;
    }

    // Setter for product category
    public void setCategory(String category) {
        this.category = category;
    }

    // Getter for price object
    public Price getPrice() {
        return price;
    }

    // Setter for price object
    public void setPrice(Price price) {
        this.price = price;
    }
}
