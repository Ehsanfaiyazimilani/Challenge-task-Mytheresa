package com.example.productapi.model;

public class Product {
    private String sku;
    private String name;
    private String category;
    private int originalPrice; 
    private Price price;       

    public Product(String sku, String name, String category, int originalPrice) {
        this.sku = sku;
        this.name = name;
        this.category = category;
        this.originalPrice = originalPrice;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

   public String getSku() {
    return sku;
}

public void setSku(String sku) {
    this.sku = sku;
}

public String getCategory() {
    return category;
}

public void setCategory(String category) {
    this.category = category;
}
}
