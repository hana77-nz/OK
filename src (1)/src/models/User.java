package models;

import java.util.ArrayList;
import java.util.regex.Pattern;

import models.Address;

public class User extends AccountInfo {
    private String firstName;
    private String lastName;
    private ArrayList<Order> orders = new ArrayList<>();
    private ArrayList<Address> addresses = new ArrayList<>();
    private ArrayList<CreditCard> creditCards = new ArrayList<>();
    private int addressCounter = 1;
    private int creditCardCounter = 1;
    private int orderCounter = 101;
    private Cart cart = new Cart();


    public Order getOrderById(int orderId) {
        for (Order order : orders) {
            if (order.getId() == orderId) {
                return order;
            }
        }
        return null;
    }

    public void addOrder(Order order) {
        orders.add(order);
        orderCounter++;
    }

    public int getNewOrderId() {
        return orderCounter;
    }

    public void addCreditCard(CreditCard creditCard) {
        creditCards.add(creditCard);
        creditCardCounter++;
    }

    public CreditCard getCreditCardByNumber(String number) {
        for (CreditCard creditCard1 : creditCards) {
            if (creditCard1.getNumber().equals(number)) {
                return creditCard1;
            }
        }
      return null;
    }

    public CreditCard getCreditCardById(int id) {
        for (CreditCard creditCard1 : creditCards) {
            if(creditCard1.getId() == id){
                return creditCard1;
            }
        }
        return null;
    }



    public void addAddress(Address address) {
        addresses.add(address);
        addressCounter++;
    }

    public Address getAddressByPost(String post) {
        for (Address address : addresses) {
            if(address.getPost().equals(post)){
                return address;
            }
        }
        return null;
    }

    public Address getAddressById(int id){
        for(Address address : addresses){
            if(address.getId() == id)
                return address;
        }
        return null;
    }

    public void removeAddress(Address address) {
        addresses.remove(address);
    }

    public int getNewAddressId(){
        return addressCounter;
    }

    public static boolean validationOfName(String name) {
        String regex = "^[A-Z][a-z]{2,}$";
        return Pattern.compile(regex).matcher(name).matches();
    }

    public static boolean validationOfPassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z0-9]+$";
        return Pattern.compile(regex).matcher(password).matches();
    }

    public static boolean validationOfEmail(String email) {
        String regex =  "^(?!.*\\..*\\..*\\..*)[a-zA-Z0-9.]+@[a-z]+\\.com";
        return Pattern.compile(regex).matcher(email).matches();
    }



    public String getFirstName() {
        return firstName;
    }

    public boolean setFirstName(String firstName) {
        if (!User.validationOfName(firstName)) {
            return false;
        }
        this.firstName = firstName;
        return true;
    }

    public String getLastName() {
      return lastName;
    }

    public boolean setLastName(String lastName) {
        if (!User.validationOfName(lastName)) {
            return false;
        }
        this.lastName = lastName;
        return true;
    }

    public ArrayList<Order> getOrders() {
        return this.orders;
    }

//    public void setOrders(ArrayList<Order> orders) {
//        this.orders = orders;
//    }

    public ArrayList<Address> getAddresses() {
        return addresses;
    }

//    public void setAddresses(ArrayList<Address> addresses) {
//        this.addresses = addresses;
//    }

//    public ArrayList<CreditCard> getCreditCards() {
//        return creditCards;
//    }

//    public void setCreditCards(ArrayList<CreditCard> creditCards) {
//        this.creditCards = creditCards;
//    }
//
    public int getAddressCounter() {
        return addressCounter;
    }
//
//    public void setAddressCounter(int addressCounter) {
//        this.addressCounter = addressCounter;
//    }
//
    public int getCreditCardCounter() {    //getnewcreditcardnumber
        return creditCardCounter;
    }
//
//    public void setCreditCardCounter(int creditCardCounter) {
//        this.creditCardCounter = creditCardCounter;
//    }
//
//    public int getOrderCounter() {
//        return orderCounter;
//    }
//
//    public void setOrderCounter(int orderCounter) {
//        this.orderCounter = orderCounter;
//    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public boolean isUser() {
        return true;
    }

    @Override
    public boolean isStore() {
        return false;
    }
}
