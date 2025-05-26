package controller;

import models.*;

import java.util.ArrayList;

public class ProductMenuController {
    public ArrayList<Product> sortProducts(String sortedBy) {
        if(sortedBy.equals("rate"))
            return App.sortProductsByRate();
        if(sortedBy.equals("higher price to lower"))
            return App.sortProductsByPrice();
        if(sortedBy.equals("lower price to higher"))
            return App.sortProductsByPriceLowToHigh();
        if(sortedBy.equals("number of sold"))
            return App.sortProductsByNumberOfSold();
        return new ArrayList<>();
    }

    public Result addReview(int id, int rate, String message) {
        Product product = App.getProductById(id);
        if(product == null)
            return new Result(false, "No product found.");
        if(rate > 5 || rate < 1)
            return new Result(false,  "Rating must be between 1 and 5.");
        if(App.getLoggedInUser() == null)
            return new Result(false, "You must be logged in to rate a product.");
        product.getFeatures().addReview(new Review(message, rate, App.getLoggedInUser()));
        return new Result(true, "Thank you! Your rating and review have been submitted successfully.");
    }

    public Product getProductById(int id) {
        return App.getProductById(id);
    }

    public Result addToCart(int productId, int amount) {
        User user = App.getLoggedInUser();
        if(user == null)
            return new Result(false, "You must be logged in to add items to your cart.");
        Product product = App.getProductById(productId);
        if(product == null)
            return new Result(false, "No product found.");
        if(amount <= 0)
            return new Result(false, "Quantity must be a positive number.");
        if(amount > product.getQuantity())
            return new Result(false, "Only "+product.getQuantity()+
                    " units of \""+product.getFeatures().getName()+"\" are available.");


        if(user.getCart().getProductById(productId) != null)
            user.getCart().getProductById(productId).setQuantity(
                    user.getCart().getProductById(productId).getQuantity() + amount);
        else {
            ProductInCart productInCart = new ProductInCart();
            productInCart.setQuantity(amount);
            productInCart.setFeatures(product.getFeatures());
            if(product.hasDiscount()) {
                Discount discount = new Discount();
                discount.setQuantity(amount);
                discount.setPercentage(product.getDiscount().getPercentage());
                productInCart.setDiscount(discount);
            }
            user.getCart().addProduct(productInCart);
        }
        product.setQuantity(product.getQuantity() - amount);
        if(product.hasDiscount()){
            product.getDiscount().setQuantity(product.getDiscount().getQuantity() - amount);
        }
        return new Result(true, "\""+product.getFeatures().getName()+
                "\" (x"+amount+") has been added to your cart.");
    }












}
