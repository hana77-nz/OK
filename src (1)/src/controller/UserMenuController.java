package controller;

import models.*;

import java.util.ArrayList;
import java.util.Comparator;

public class UserMenuController {
    public Result editName(String firstName, String lastName, String password){
        User user = App.getLoggedInUser();
        if(!user.checkPassword(password))
            return new Result(false, "Incorrect password. Please try again.");
        if(user.getFirstName().equals(firstName) || user.getLastName().equals(lastName))
            return new Result(false, "The new name must be different from the current name.");
        if(firstName.length() < 3 || lastName.length() < 3)
            return new Result(false, "Name is too short.");
        if(!User.validationOfName(firstName) || !User.validationOfName(lastName))
            return new Result(false, "Incorrect name format.");
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return new Result(true, "Name updated successfully.");
    }
    public Result editEmail(String email, String password){
        User user = App.getLoggedInUser();
        if(!user.checkPassword(password))
            return new Result(false, "Incorrect password. Please try again.");
        if(user.getEmail().equals(email))
            return new Result(false, "The new email must be different from the current email.");
        if(!User.validationOfEmail(email))
            return new Result(false, "Incorrect email format.");
        if(App.getAccountByEmail(email)!=null)
            return new Result(false, "Email already exists.");
        user.setEmail(email);
        return new Result(true, "Email updated successfully.");
    }
    public Result editPassword(String newPassword, String oldPassword){
        User user = App.getLoggedInUser();
        if(!user.checkPassword(oldPassword))
            return new Result(false, "Incorrect password. Please try again.");
        if(newPassword.equals(oldPassword))
            return new Result(false, "The new password must be different from the old password.");
        if(!User.validationOfPassword(newPassword))
            return new Result(false, "The new password is weak.");
        user.setPassword(newPassword);
        return new Result(true, "Password updated successfully.");
    }
    public User getLoggedInUser(){
        return App.getLoggedInUser();
    }

    public Result addAddress(String country, String city, String street, String postal) {
        if(!Address.validationOfPost(postal))
            return new Result(false, "Invalid postal code. It should be a 10-digit number.");
        User user = App.getLoggedInUser();
        if(user.getAddressByPost(postal)!=null)
            return new Result(false, "This postal code is already associated with an existing address.");
        Address address = new Address(user.getNewAddressId(), country, city, street, postal);
        user.addAddress(address);
        return new Result(true, "Address successfully added with id " + address.getId()+".");
    }
    public Result deleteAddress(int id) {
        User user = App.getLoggedInUser();
        Address address = user.getAddressById(id);
        if(address == null)
            return new Result(false, "No address found.");
        user.removeAddress(address);
        return new Result(true, "Address with id "+id+" deleted successfully.");
    }
    public ArrayList<Address> listAddresses(){
        return App.getLoggedInUser().getAddresses();
    }

    public Result addCreditCard(String cardNumber, String expiryDate, String cvv, double value) {
        CreditCard card = new CreditCard();
        if(!card.setNumber(cardNumber))
            return new Result(false, "The card number must be exactly 16 digits.");
        if(!card.setExpirationDate(expiryDate))
            return new Result(false, "Invalid expiration date. Please enter a valid date in MM/YY format.");
        if(!card.setCvv(cvv))
            return new Result(false, "The CVV code must be 3 or 4 digits.");
        if(!card.setInitialValue(value))
            return new Result(false, "The initial value cannot be negative.");
        if(App.getLoggedInUser().getCreditCardByNumber(cardNumber) != null)
            return new Result(false, "This card is already saved in your account.");
        card.setId(App.getLoggedInUser().getCreditCardCounter());
        App.getLoggedInUser().addCreditCard(card);
        return new Result(true, "Credit card with Id "+card.getId()+" has been added successfully.");
    }

    public Result chargeCreditCard(double amount, int id){
        if(amount <= 0)
            return new Result(false, "The amount must be greater than zero.");
        CreditCard card = App.getLoggedInUser().getCreditCardById(id);
        if(card == null)
            return new Result(false, "No credit card found.");
        card.addValue(amount);
        return new Result(true, card.getInitialValue() + "");
    }
    public Result checkCreditCardBalance(int id){
        CreditCard card = App.getLoggedInUser().getCreditCardById(id);
        if(card == null)
            return new Result(false, "No credit card found.");
        return new Result(true, ""+card.getInitialValue());
    }

    public ArrayList<ProductInCart> getProductsInCart() {
        ArrayList<ProductInCart> products =  App.getLoggedInUser().getCart().getProducts();
        products.sort(Comparator.comparing((ProductInCart p) -> p.getFeatures().getName()));
        return products;
    }

    public CheckOutResult checkOut(int cardId, int addressId) {
        User user = App.getLoggedInUser();
        if(user.getCart().getProducts().isEmpty())
            return new CheckOutResult(new Result(false, "Your shopping cart is empty."), null);
        Address address = user.getAddressById(addressId);
        if(address == null)
            return new CheckOutResult(new Result(false, "The provided address ID is invalid."), null);
        CreditCard creditCard = user.getCreditCardById(cardId);
        if(creditCard == null)
            return new CheckOutResult(new Result(false, "The provided card ID is invalid."), null);
        double price = user.getCart().calculateTotal();
        if(price > creditCard.getInitialValue())
            return new CheckOutResult(new Result(false, "Insufficient balance in the selected card."), null);

        creditCard.addValue(-price);
        ArrayList<ProductInOrder> productInOrders = new ArrayList<>();
        for(ProductInCart product : user.getCart().getProducts()) {
            product.getFeatures().getStore().addProfit(
                    product.calculateDiscount() * product.getQuantity());
            App.getProductById(product.getFeatures().getId()).addSoldedNumber(product.getQuantity());
            productInOrders.add(new ProductInOrder(product.getFeatures(), product.getQuantity(),
                    product.calculateDiscount()));
        }
        Order order = new Order(user.getNewOrderId(), price, address, productInOrders);
        user.getCart().getProducts().clear();
        user.addOrder(order);
        return new CheckOutResult(new Result(true, ""), order);
    }

    public Order orderDetail(int orderId) {
        Order order = App.getLoggedInUser().getOrderById(orderId);
        if(order != null)
            order.getProducts().sort(Comparator.comparing((ProductInOrder p) -> p.getFeatures().getId()));
        return order;
    }

    public ArrayList<Order> getOrders() {
        ArrayList<Order> orders = App.getLoggedInUser().getOrders();
        for (Order order : orders) {
            order.getProducts().sort(Comparator.comparing((ProductInOrder p) -> p.getFeatures().getName()));
        }
        return orders;
    }

    public Result removeFromCart(int productId, int amount) {
        User user = App.getLoggedInUser();
        if(user.getCart().getProducts().isEmpty())
            return new Result(false, "Your shopping cart is empty.");
        ProductInCart product = user.getCart().getProductById(productId);
        if(product == null)
            return new Result(false, "The product with ID "+productId+" is not in your cart.");
        if(amount <= 0)
            return new Result(false, "Quantity must be a positive number.");
        if(amount > product.getQuantity())
            return new Result(false, "You only have "+product.getQuantity()+" of \""
                    +product.getFeatures().getName()+"\" in your cart.");

        product.setQuantity(product.getQuantity() - amount);
        App.getProductById(productId).setQuantity(
                App.getProductById(productId).getQuantity() + amount);
        if(product.hasDiscount())
            App.getProductById(productId).getDiscount().setQuantity(
                    App.getProductById(productId).getDiscount().getQuantity() + amount);

        if(product.getQuantity() == 0) {
            user.getCart().getProducts().remove(product);
            return new Result(true, "\"" + product.getFeatures().getName() +
                    "\" has been completely removed from your cart.");
        }else
            return new Result(true, "Successfully removed "+amount+" of \""
                    +product.getFeatures().getName()+"\" from your cart.");
    }


}
