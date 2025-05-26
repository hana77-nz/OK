package models;

import models.enums.AppMenuCommands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class App {
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Store> stores = new ArrayList<>();
    private static ArrayList<Product> products = new ArrayList<>();
    private static int productCounter = 101;
    private static AccountInfo loggedInAccount = null;
    private static AppMenuCommands menu = AppMenuCommands.MainMenu;

    public static void deleteAccount(AccountInfo account) {
        if (account.isUser()){
            users.remove(account);
        }else {
            stores.remove(account);
        }
    }

    public static ArrayList<Product> sortProductsByRate() {
        ArrayList<Product> sortedProducts = new ArrayList<>(products);  //clone??
        Collections.sort(sortedProducts, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                int rateComparison = Double.compare(o2.getFeatures().getRate(), o1.getFeatures().getRate());
                if (rateComparison != 0) {
                    return rateComparison;
                }
                return Integer.compare(o1.getFeatures().getId(), o2.getFeatures().getId());
            }
        } );
       return sortedProducts;
    }

    public static ArrayList<Product> sortProductsByPrice() {
        ArrayList<Product> sortedProducts = new ArrayList<>(products);
        Collections.sort(sortedProducts, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                int priceComparison = Double.compare(o2.calculateDiscount(), o1.calculateDiscount());
                if (priceComparison != 0) {
                    return priceComparison;
                }
                return Integer.compare(o1.getFeatures().getId(), o2.getFeatures().getId());
            }
        });
        return sortedProducts;
    }

    public static ArrayList<Product> sortProductsByPriceLowToHigh() {
        ArrayList<Product> sortedProducts = new ArrayList<>(products);
        Collections.sort(sortedProducts, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                int priceComparison = Double.compare(o1.calculateDiscount(), o2.calculateDiscount());
                if (priceComparison != 0) {
                    return priceComparison;
                }
                return Integer.compare(o1.getFeatures().getId(), o2.getFeatures().getId());
            }
        });
        return sortedProducts;
    }

    public static ArrayList<Product> sortProductsByNumberOfSold() {
        ArrayList<Product> sortedProducts = new ArrayList<>(products);
        Collections.sort(sortedProducts, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                int soldComparison = Integer.compare(o2.getSoldedNumber(), o1.getSoldedNumber());
                if (soldComparison != 0) {
                    return soldComparison;
                }
                return Integer.compare(o1.getFeatures().getId(), o2.getFeatures().getId());
            }
        });
        return sortedProducts;
    }

    public static Product getProductOfStoreById(int id, Store store) {
        for (Product product : products) {
            if (product.getFeatures().getId() == id && product.getStore().equals(store)) {
                return product;
            }
        }
        return null;
    }


    public static Product getProductById(int id) {
        for (Product product : products) {
            if(product.getFeatures().getId() == id) {
                return product;
            }
        }
        return null;
    }

    public static int getNewStoreId() {    //????????????
       return productCounter;
    }

    public static void addProduct(Product product) {
        products.add(product);
        productCounter++;
    }

    public static User getLoggedInUser() {
        if (loggedInAccount == null || loggedInAccount.isStore()) {
            return null;
        }
        return (User) loggedInAccount;
    }

    public static Store getLoggedInStore() {
        if (loggedInAccount == null || loggedInAccount.isUser()) {
            return null;
        }
        return (Store) loggedInAccount;
    }

    public static AccountInfo getAccountByEmail(String email) {
        for (User user : users)
            if(user.getEmail().equals(email))  // case-sensitive
                return user;
        for (Store store : stores)
            if(store.getEmail().equals(email))  // case-sensitive
                return store;
        return null;
    }

    public static User getUserByEmail(String email) {
        AccountInfo account = getAccountByEmail(email);
        if(account == null || account.isStore())
            return null;
        return (User)account;
    }
    public static Store getStoreByEmail(String email) {
        AccountInfo account = getAccountByEmail(email);
        if(account == null || account.isUser())
            return null;
        return (Store)account;
    }

    public static ArrayList<Product> getProductsOfStore(Store store) {
        ArrayList<Product> result = new ArrayList<>();
        for(Product product : products){
            if(product.getStore().equals(store))
                result.add(product);
        }
        return result;
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void addStore(Store store) {
        stores.add(store);
    }

    public static ArrayList<Store> getStores() {
        return stores;
    }

    public static AppMenuCommands getMenu() {
        return menu;
    }

    public static void setMenu(AppMenuCommands menu) {
        App.menu = menu;
        menu.showMessage();
    }

    public static void logout(){
        App.loggedInAccount = null;
    }

    public static AccountInfo getLoggedInAccount(){
        return loggedInAccount;
    }

    public static void loginUser(User user){
        loggedInAccount = user;
    }

    public static void loginStore(Store store){
        loggedInAccount = store;
    }

    public static ArrayList<Product> getProducts() {
        return products;
    }
}
