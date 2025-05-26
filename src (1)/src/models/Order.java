package models;

import java.util.ArrayList;

public class Order {
    private int id;
    private double paid;
    private Address shippingAddress;
    private ArrayList<ProductInOrder> products = new ArrayList<>();

    public Order(int id, double paid, Address shippingAddress, ArrayList<ProductInOrder> products) {
        this.id = id;
        this.paid = paid;
        this.shippingAddress = shippingAddress;
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public ArrayList<ProductInOrder> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ProductInOrder> products) {
        this.products = products;
    }
}
