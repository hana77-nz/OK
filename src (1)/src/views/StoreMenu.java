package views;

import controller.StoreMenuController;
import models.Product;
import models.Result;
import models.enums.StoreMenuCommands;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class StoreMenu extends AppMenu {
    StoreMenuController controller = new StoreMenuController();
    @Override
    public boolean run(String input) {
        if(super.run(input))
            return true;
        Matcher matcher;
        if((matcher = StoreMenuCommands.AddProduct.getMatcher(input)) != null){
            String name = matcher.group("name");
            double producerCost = Double.parseDouble(matcher.group("producerCost").trim());
            double price = Double.parseDouble(matcher.group("price").trim());
            String about = matcher.group("about");
            int numberOfProductToSell = Integer.parseInt(matcher.group("NumberOfProductToSell").trim());
            Result result = controller.addProduct(name, producerCost, price, about, numberOfProductToSell);
            System.out.println(result);
        }else if((matcher = StoreMenuCommands.ApplyDiscount.getMatcher(input)) != null){
            int id = Integer.parseInt(matcher.group("productId").trim());
            int percentage = Integer.parseInt(matcher.group("discountPercentage").trim());
            int quantity = Integer.parseInt(matcher.group("quantity").trim());
            Result result = controller.applyDiscount(id, percentage, quantity);
            System.out.println(result);
        }else if((matcher = StoreMenuCommands.ShowProfit.getMatcher(input)) != null){
            double getProfit = controller.getProfit();
            double getTotalCost = controller.getTotalCost();
            System.out.printf("Total Profit: $%.1f\n" +
                    "(Revenue: $%.1f - Costs: $%.1f)\n", getProfit - getTotalCost, getProfit, getTotalCost);
        }else if((matcher = StoreMenuCommands.ShowListOfProducts.getMatcher(input)) != null){

            ArrayList<Product> product = controller.getListOfProducts();
            if(product.isEmpty()){
                System.out.println("No products available in the store.");
                return true;
            }
            System.out.println("Store Products (Sorted by date added)\n" +
                    "------------------------------------------------");

            for(Product product1 : product){
                String message = "";
                if(product1.getQuantity() == 0)
                    message = "(**Sold out!**)";
                else if(product1.hasDiscount())
                    message = "**(On Sale! "+product1.getDiscount().getQuantity()+" units discounted)**";

                System.out.println("ID: " + product1.getFeatures().getId() + "  " + message);
                System.out.println("Name: " + product1.getFeatures().getName());
                if(product1.hasDiscount())
                    System.out.printf("Price: ~$%.1f~ → $%.1f (-%d%%)\n", product1.getFeatures().getPrice(),
                            product1.calculateDiscount(), product1.getDiscount().getPercentage());
                else
                    System.out.printf("Price: $%.1f\n", product1.getFeatures().getPrice());
                System.out.println("Stock: " + product1.getQuantity());
                System.out.println("Sold: " + product1.getSoldedNumber());
                System.out.println("------------------------------------------------");
            }
        }else if((matcher = StoreMenuCommands.AddStock.getMatcher(input)) != null){  //8
            int id = Integer.parseInt(matcher.group("productId").trim());
            int amount = Integer.parseInt(matcher.group("amount").trim());
            Result result = controller.addStock(id, amount);
            System.out.println(result);
        }else if((matcher = StoreMenuCommands.UpdatePrice.getMatcher(input)) != null){
            int id = Integer.parseInt(matcher.group("productId").trim());
            double newPrice = Double.parseDouble(matcher.group("newPrice").trim());
            Result result = controller.updatePrice(id, newPrice);
            if(!result.successful)
                System.out.println(result);
            else
                System.out.printf("Price of \""+result+"\" has been updated to $%.1f.\n", newPrice);
        }else {
            System.out.println("invalid command");
            return false;
        }
        return true;
    }
}
