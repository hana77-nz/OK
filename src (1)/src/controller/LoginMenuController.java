package controller;
import models.*;
import java.util.ArrayList;


public class LoginMenuController {
    public Result createUser(String firstName, String lastName, String password
            , String reEnteredPassword, String email){
        User user = new User();
        if(firstName.length() < 3 || lastName.length() < 3)
            return new Result(false, "Name is too short.");
        if(!user.setFirstName(firstName) || !user.setLastName(lastName))
            return new Result(false, "Incorrect name format.");
        if(!setPasswordAndEmail(user, password, reEnteredPassword, email).successful)
            return setPasswordAndEmail(user, password, reEnteredPassword, email);
        App.addUser(user);
        return new Result(true, "User account for "+firstName+" "+lastName+" created successfully.");
    }
    public Result createStore(String brand, String password
            , String reEnteredPassword, String email){
        Store store = new Store();
        if(!store.setBrand(brand))
            return new Result(false, "Brand name is too short.");
        if(!setPasswordAndEmail(store, password, reEnteredPassword, email).successful)
            return setPasswordAndEmail(store, password, reEnteredPassword, email);
        App.addStore(store);
        return new Result(true, "Store account for \""+brand+"\" created successfully.");
    }
    private Result setPasswordAndEmail(AccountInfo account, String password, String reEnteredPassword, String email){
        if(!account.setPassword(password))
            return new Result(false, "Incorrect password format.");
        if(!password.equals(reEnteredPassword))
            return new Result(false, "Re-entered password is incorrect.");
        if(!account.setEmail(email))
            return new Result(false, "Incorrect email format.");
        if(App.getAccountByEmail(email) != null)
            return new Result(false, "Email already exists.");
        return new Result(true, "");
    }

    public Result loginUser(String password, String email){
        User user = App.getUserByEmail(email);
        if(user == null)
            return new Result(false, "No user account found with the provided email.");
        if(!user.checkPassword(password))
            return new Result(false, "Password is incorrect.");
        App.loginUser(user);
        return new Result(true, "User logged in successfully. ");
    }
    public Result loginStore(String password, String email){
        Store store = App.getStoreByEmail(email);
        if(store == null)
            return new Result(false, "No store account found with the provided email.");
        if(!store.checkPassword(password))
            return new Result(false, "Password is incorrect.");
        App.loginStore(store);
        return new Result(true, "Store logged in successfully. ");
    }
    public Result logout(){
        if(App.getLoggedInAccount() == null)
            return new Result(false, "You should login first.");
        App.logout();
        return new Result(true, "Logged out successfully. ");
    }

    public Result deleteAccount(String password, String reEnteredPassword) {
        AccountInfo account = App.getLoggedInAccount();
        if(account == null)
            return new Result(false, "You should login first.");
        if(!password.equals(reEnteredPassword))
            return new Result(false, "Re-entered password is incorrect.");
        if(!account.checkPassword(password))
            return new Result(false, "Password is incorrect.");
        if(account.isUser()){
            UserMenuController userController = new UserMenuController();
            User user = (User) account;
            for(ProductInCart product : user.getCart().getProducts())
                userController.removeFromCart(product.getFeatures().getId(), product.getQuantity());
            App.getUsers().remove(user);
        }else{
            Store store = (Store) account;
            for(Product product : App.getProductsOfStore(store)) {
                for (User user : App.getUsers()) {
                    ArrayList<ProductInCart> productInCarts = new ArrayList<>();
                    for (ProductInCart productInCart : user.getCart().getProducts()) {
                        if (productInCart.getFeatures().getId() == product.getFeatures().getId())
                            productInCarts.add(productInCart);
                    }
                    for (ProductInCart productInCart : productInCarts) {
                        user.getCart().getProducts().remove(productInCart);
                    }
                }
                App.getProducts().remove(product);
            }
        }
        App.deleteAccount(account);

        return new Result(true, "Account deleted successfully. ");
    }








}
