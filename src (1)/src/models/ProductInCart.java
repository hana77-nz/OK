package models;

public class ProductInCart {
    private ProductFeatures features;
    private int quantity;
    private Discount discount = null;

    public boolean hasDiscount() {
        return discount != null && discount.getQuantity() > 0;
    }

    public double calculateDiscount() {
        if(hasDiscount()) {
            return (100 - discount.getPercentage()) * features.getPrice() / 100;
        }
        return features.getPrice();
    }

    public ProductFeatures getFeatures() {
        return features;
    }

    public void setFeatures(ProductFeatures features) {
        this.features = features;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }
}
