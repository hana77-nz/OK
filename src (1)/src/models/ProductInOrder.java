package models;

public class ProductInOrder {
    private ProductFeatures features;
    private int quantity;
    private double eachPrice;

    public ProductInOrder(ProductFeatures features, int quantity, double eachPrice) {
        this.features = features;
        this.quantity = quantity;
        this.eachPrice = eachPrice;
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

    public double getEachPrice() {
        return eachPrice;
    }

    public void setEachPrice(double eachPrice) {
        this.eachPrice = eachPrice;
    }
}
