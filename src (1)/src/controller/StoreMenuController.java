package controller;

import models.*;

import java.util.ArrayList;

public class StoreMenuController {
    public Result addProduct(String name, double producerCost, double price, String about, int numberOfProductToSell) {
    if(producerCost > price)
        return new Result(false, "Selling price must be greater than or equal to the producer cost.");
    if(numberOfProductToSell <= 0)
        return new Result(false, "Number of products must be a positive number.");
    Product product = new Product();
    product.setFeatures(new ProductFeatures(name, producerCost, price, about,
            App.getNewStoreId(), App.getLoggedInStore()));
    product.setStore(App.getLoggedInStore());
    product.setQuantity(numberOfProductToSell);
    App.addProduct(product);
    App.getLoggedInStore().addCost(producerCost * numberOfProductToSell);
    return new Result(true, "Product \""+name+"\" has been added successfully with ID "
            +product.getFeatures().getId()+".");
}

    public Result applyDiscount(int id, int percentage, int quantity) {
        Discount discount = new Discount();
        if(!discount.setPercentage(percentage))
            return new Result(false, "Discount percentage must be between 1 and 100.");
        Product product = App.getProductOfStoreById(id, App.getLoggedInStore());
        if(product == null)
            return new Result(false, "No product found.");
        int rem = product.getQuantity();
        if(product.hasDiscount())
            rem -= product.getDiscount().getQuantity();
        if(quantity > product.getQuantity())
            return new Result(false,
                    "Oops! Not enough stock to apply the discount to that many items.");
        if(product.getDiscount() != null)
            product.getDiscount().setQuantity(product.getDiscount().getQuantity() + quantity);
        else {
            discount.setQuantity(quantity);
            product.setDiscount(discount);
        }
        return new Result(true, "A "+percentage+"% discount has been applied to "+quantity+" units" +
                " of product ID "+id+".");
    }

    public ArrayList<Product> getListOfProducts() {
        ArrayList<Product> products = new ArrayList<>();
        for(Product product : App.getProducts()){
            if(product.getStore().equals(App.getLoggedInStore()))
                products.add(product);
        }
        return products;
    }

    public Result addStock(int productId, int amount) {
        Product product = App.getProductOfStoreById(productId, App.getLoggedInStore());
        if(product == null)
            return new Result(false, "No product found.");
        if(amount <= 0)
            return new Result(false, "Amount must be a positive number.");
        product.setQuantity(product.getQuantity() + amount);
        App.getLoggedInStore().addCost(amount * product.getFeatures().getProducerCost());
        return new Result(true, amount + " units of \""+product.getFeatures().getName()+"\"" +
                " have been added to the stock.");
    }

    public Result updatePrice(int id, double newPrice) {
        Product product = App.getProductOfStoreById(id, App.getLoggedInStore());
        if(product == null)
            return new Result(false, "No product found.");
        if(newPrice <= 0)
            return new Result(false, "Price must be a positive number.");
        product.getFeatures().setPrice(newPrice);
        return new Result(true, product.getFeatures().getName());
    }

    public double getProfit() {
        return App.getLoggedInStore().getProfit();
    }

    public double getTotalCost() {
        return App.getLoggedInStore().getTotalCost();
    }
}

