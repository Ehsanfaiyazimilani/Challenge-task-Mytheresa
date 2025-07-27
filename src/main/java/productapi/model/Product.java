package productapi.model;

public class Product {
    private String sku;
    private String name;
    private String category;
    private Price price;

    public Product(String sku, String name, String category, Price price) {
        this.sku = sku;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    // Getters and Setters
    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }
}