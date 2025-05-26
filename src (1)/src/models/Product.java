package models;

import java.util.ArrayList;
import java.util.Date;

public class Product {
   private ProductFeatures features;
   private int quantity;
   private Store store;
   private Discount discount;
   private int soldedNumber = 0;

    public ProductFeatures getFeatures() {
        return features;
    }

    public void setFeatures(ProductFeatures features) {
        this.features = features;
    }

    public double calculateDiscount() {
        if(hasDiscount()) {
            return (100 - discount.getPercentage()) * features.getPrice() / 100;
        }
        return features.getPrice();
    }

    public boolean hasDiscount() {
        if (discount == null) {
            return false;}
            return discount.getQuantity() > 0;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public int getSoldedNumber() {
        return soldedNumber;
    }

    public void addSoldedNumber(int number) {
        this.soldedNumber += number;
    }
}
