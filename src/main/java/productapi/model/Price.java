package productapi.model;

// Price model class represents price details for a product
public class Price {

    // Original price before any discount
    private int original;
    // Final price after applying discount
    private int finalPrice;
    // Discount percentage as a string (e.g. "30%")
    private String discountPercentage;
    // Currency code (e.g. "EUR")
    private String currency;

    // Constructor with all fields, including discount percentage
    public Price(int original, int finalPrice, String discountPercentage, String currency) {
        this.original = original;
        this.finalPrice = finalPrice;
        this.discountPercentage = discountPercentage;
        this.currency = currency;
    }

    // Constructor without discount percentage (for products with no discount)
    public Price(int original, int finalPrice, String currency) {
        this.original = original;
        this.finalPrice = finalPrice;
        this.currency = currency;
    }

    // Getter for original price
    public int getOriginal() {
        return original;
    }

    // Setter for original price
    public void setOriginal(int original) {
        this.original = original;
    }

    // Getter for final price
    public int getFinalPrice() {
        return finalPrice;
    }

    // Setter for final price
    public void setFinalPrice(int finalPrice) {
        this.finalPrice = finalPrice;
    }

    // Getter for discount percentage
    public String getDiscountPercentage() {
        return discountPercentage;
    }

    // Setter for discount percentage
    public void setDiscountPercentage(String discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    // Getter for currency code
    public String getCurrency() {
        return currency;
    }

    // Setter for currency code
    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
