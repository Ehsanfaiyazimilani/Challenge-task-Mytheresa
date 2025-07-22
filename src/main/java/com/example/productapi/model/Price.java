package com.example.productapi.model;

public class Price {
    private int original;
    private int finalPrice;
    private String discountPercentage;
    private String currency = "EUR";

    public Price() {}

    public Price(int original, int finalPrice, String discountPercentage) {
        this.original = original;
        this.finalPrice = finalPrice;
        this.discountPercentage = discountPercentage;
    }

    // Getters and Setters
    public int getOriginal() { return original; }
    public void setOriginal(int original) { this.original = original; }

    public int getFinalPrice() { return finalPrice; }
    public void setFinalPrice(int finalPrice) { this.finalPrice = finalPrice; }

    public String getDiscountPercentage() { return discountPercentage; }
    public void setDiscountPercentage(String discountPercentage) { this.discountPercentage = discountPercentage; }

    public String getCurrency() { return currency; }
}

