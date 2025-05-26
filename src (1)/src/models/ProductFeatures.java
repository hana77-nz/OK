package models;

import java.util.ArrayList;



public class ProductFeatures {
    private String name;
    private double producerCost;
    private double price;
    private String description;
    private int id;
    private double rate;
    private Store store;
    private ArrayList<Review> reviews = new ArrayList<>();

    public ProductFeatures(String name, double producerCost, double price, String description, int id, Store store) {
        this.name = name;
        this.producerCost = producerCost;
        this.price = price;
        this.description = description;
        this.id = id;
        //this.rate = rate;
        this.store = store;
      //  this.reviews = reviews;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getProducerCost() {
        return producerCost;
    }

    public void setProducerCost(double producerCost) {
        this.producerCost = producerCost;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getRate() {
        if (reviews.isEmpty()) {
            return 2.5;
        }
            double total = 0;
            for (Review review : reviews){
                total += review.getRate();
            }
        return total / reviews.size();
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }


}
