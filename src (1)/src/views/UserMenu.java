package views;

import controller.MainMenuController;
import controller.UserMenuController;
import models.*;
import models.enums.MainMenuCommands;
import models.enums.AppMenuCommands;
import models.enums.UserMenuCommands;
import views.AppMenu;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class UserMenu extends AppMenu {
    UserMenuController controller = new UserMenuController();
    @Override
    public boolean run(String input) {
        if(super.run(input))
            return true;
        Matcher matcher;
        if((matcher = UserMenuCommands.ListOrder.getMatcher(input)) != null){
            ArrayList<Order> orders = controller.getOrders();
            if(orders.size() == 0){
                System.out.println("No orders found.");
                return true;
            }
            System.out.println("order History\n" +
                    "━━━━━━━━━━━━━━━━━━━━━━━━━━");
            for(Order order : orders){
                int totalItems = 0;
                for(ProductInOrder product : order.getProducts()){
                    totalItems += product.getQuantity();
                }
                System.out.printf("\n" +
                        "Order ID: "+order.getId()+"  \n" +
                        "Shipping Address: "+order.getShippingAddress()+" \n" +
                        "Total Items Ordered: "+totalItems+"  \n" +
                        "\n" +
                        "Products (Sorted by Name):  \n");
                for(int i= 0; i < order.getProducts().size(); i++)
                    System.out.println("  "+(i+1)+"- "
                            +order.getProducts().get(i).getFeatures().getName()+"  ");
                System.out.println("\n" +
                        "━━━━━━━━━━━━━━━━━━━━━━━━━━ ");
            }
        }else if((matcher = UserMenuCommands.ShowOrderDetails.getMatcher(input)) != null){
            int orderId = Integer.parseInt(matcher.group("id").trim());
            Order order = controller.orderDetail(orderId);
            if(order == null){
                System.out.println("Order not found.");
                return true;
            }
            System.out.println("Products in this order:\n");
            for(int i = 0; i<order.getProducts().size(); i++){
                ProductInOrder product = order.getProducts().get(i);
                String each = "";
                if(order.getProducts().get(i).getQuantity() > 1)
                    each = "each";
                System.out.printf((i+1) + "- Product Name: "+product.getFeatures().getName()+"  \n" +
                        "    ID: "+product.getFeatures().getId()+"  \n" +
                        "    Brand: "+product.getFeatures().getStore().getBrand()+"  \n" +
                        "    Rating: %.1f/5  \n" +
                        "    Quantity: "+order.getProducts().get(i).getQuantity()+"  \n" +
                        "    Price: $%.1f "+each+"  \n" +
                        "\n", product.getFeatures().getRate(), product.getEachPrice());
            }
            System.out.printf("━━━━━━━━━━━━━━━━━━━━━━━━━━  \n" +
                    "**Total Cost: $%.1f**  \n", order.getPaid());
        }else if((matcher = UserMenuCommands.EditName.getMatcher(input)) != null){
            String firstName = matcher.group("firstName").trim();
            String lastName = matcher.group("lastName").trim();
            String password = matcher.group("password").trim();
            Result result = controller.editName(firstName, lastName, password);
            System.out.println(result);
        }else if((matcher = UserMenuCommands.EditEmail.getMatcher(input)) != null){
            String email = matcher.group("email").trim();
            String password = matcher.group("password").trim();
            Result result = controller.editEmail(email, password);
            System.out.println(result);
        }else if((matcher = UserMenuCommands.EditPassword.getMatcher(input)) != null){
            String oldPassword = matcher.group("oldPassword").trim();
            String newPassword = matcher.group("newPassword").trim();
            Result result = controller.editPassword(newPassword, oldPassword);
            System.out.println(result);
        }else if((matcher = UserMenuCommands.ShowMyInfo.getMatcher(input)) != null){
            User user = controller.getLoggedInUser();
            System.out.println("Name: "+user.getFirstName()+" "+user.getLastName()+"\n" +
                    "Email: "+user.getEmail());
        }else if((matcher = UserMenuCommands.AddAddress.getMatcher(input)) != null){
            String country = matcher.group("country").trim();
            String city = matcher.group("city").trim();
            String street = matcher.group("street").trim();
            String postal = matcher.group("postal").trim();
            Result result = controller.addAddress(country, city, street, postal);
            System.out.println(result);
        }else if((matcher = UserMenuCommands.DeleteAddress.getMatcher(input)) != null){
            String id = matcher.group("id").trim();
            Result result = controller.deleteAddress(Integer.parseInt(id));
            System.out.println(result);
        }else if((matcher = UserMenuCommands.ListAddresses.getMatcher(input)) != null){
            ArrayList<Address> result = controller.listAddresses();
            if(result.isEmpty()){
                System.out.println("No addresses found. Please add an address first.");
                return true;
            }
            System.out.println("Saved Addresses\n" +
                    "━━━━━━━━━━━━━━━━━━━━━━━━━━");
            for(Address address : result){
                System.out.println();
                System.out.println("Address "+address.getId()+":\n" +
                        "Postal Code: "+address.getPost()+"\n" +
                        "Country: "+address.getCountry()+"\n" +
                        "City: "+address.getCity()+"\n" +
                        "Street: "+address.getStreet());
                System.out.println("\n" +
                        "━━━━━━━━━━━━━━━━━━━━━━━━━━");
            }
        }else if((matcher = UserMenuCommands.AddCreditCard.getMatcher(input)) != null){
            String cardNumber = matcher.group("number").trim();
            String expiryDate = matcher.group("expiryDate").trim();
            String cvv = matcher.group("cvv").trim();
            double value = Double.parseDouble(matcher.group("initialValue").trim());

            Result result = controller.addCreditCard(cardNumber, expiryDate, cvv, value);
            System.out.println(result);
        }else if((matcher = UserMenuCommands.ChargeCreditCard.getMatcher(input)) != null){
            int id = Integer.parseInt(matcher.group("id").trim());
            double amount = Double.parseDouble(matcher.group("amount").trim());
            Result result = controller.chargeCreditCard(amount, id);
            if(!result.successful)
                System.out.println(result);
            else
                System.out.printf("$%.1f has been added to the credit card "+id+". " +
                        "New balance: $%.1f.\n", amount, Double.parseDouble(result.message));
        }else if((matcher = UserMenuCommands.CheckCreditCardBalance.getMatcher(input)) != null){
            int id = Integer.parseInt(matcher.group("id").trim());
            Result result = controller.checkCreditCardBalance(id);
            if(!result.successful)
                System.out.println(result);
            else
                System.out.printf("Current balance : $%.1f\n", Double.parseDouble(result.message));
        }else if((matcher = UserMenuCommands.Checkout.getMatcher(input)) != null){
            int cardId = Integer.parseInt(matcher.group("cardId").trim());
            int addressId = Integer.parseInt(matcher.group("addressId").trim());
            CheckOutResult result = controller.checkOut(cardId, addressId);
            if(!result.result.successful)
                System.out.println(result.result);
            else
                System.out.printf("Order has been placed successfully!\n" +
                        "Order ID: "+result.order.getId()+"\n" +
                        "Total Paid: $%.1f\n" +
                        "Shipping to: "+result.order.getShippingAddress()+"\n", result.order.getPaid());
        }else if((matcher = UserMenuCommands.RemoveFromCart.getMatcher(input)) != null){
            int productId = Integer.parseInt(matcher.group("productId").trim());
            int amount = Integer.parseInt(matcher.group("amount").trim());
            Result result = controller.removeFromCart(productId, amount);
            System.out.println(result);
        }else if((matcher = UserMenuCommands.ShowProductsInCart.getMatcher(input)) != null){
            ArrayList<ProductInCart> products = controller.getProductsInCart();
            if(products.isEmpty()){
                System.out.println("Your shopping cart is empty.");
                return true;
            }
            System.out.println("Your Shopping Cart:\n" +
                    "------------------------------------");
            for(ProductInCart product : products){
                System.out.printf("Product ID  : "+product.getFeatures().getId()+"\n" +
                                "Name        : "+product.getFeatures().getName()+"\n" +
                                "Quantity    : "+product.getQuantity()+"\n" +
                                "Price       : $%.1f (each)\n" +
                                "Total Price : $%.1f\n" +
                                "Brand       : "+product.getFeatures().getStore().getBrand()+"\n" +
                                "Rating      : %.1f/5\n" +
                                "------------------------------------\n", product.calculateDiscount(),
                        product.calculateDiscount() * product.getQuantity(),
                        product.getFeatures().getRate());
            }
        }else {
            System.out.println("invalid command");
            return false;
        }
        return true;
    }
}
