package views;

import controller.ProductMenuController;
import controller.StoreMenuController;
import models.Product;
import models.Result;
import models.Review;
import models.enums.ProductMenuCommands;
import models.enums.StoreMenuCommands;
import views.AppMenu;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class ProductMenu extends AppMenu {
    ProductMenuController controller = new ProductMenuController();
    private int productPageNumber = 0;
    private ArrayList<Product> products;
    private String sortBy;

    @Override
    public boolean run(String input) {
        if(super.run(input))
            return true;
        Matcher matcher;
        if((matcher = ProductMenuCommands.ShowProducts.getMatcher(input)) != null){ // 8 9
            productPageNumber = 0;
            sortBy = matcher.group("sortBy").trim();
            products = controller.sortProducts(sortBy);
            showProduct();
        }else if((matcher = ProductMenuCommands.ShowPastProducts.getMatcher(input)) != null){
            if(productPageNumber == 0){
                System.out.println("No more products available.");
                return true;
            }
            productPageNumber--;
            showProduct();
        }else if((matcher = ProductMenuCommands.ShowNextProducts.getMatcher(input)) != null){
            if ((productPageNumber + 1) * 10 >= products.size()) {
                System.out.println("No more products available.");
                return true;
            } else {
                productPageNumber++;
                showProduct();
            }


        }else if((matcher = ProductMenuCommands.RateProduct.getMatcher(input)) != null){ //9
            int rate = Integer.parseInt(matcher.group("rate").trim());
            int id = Integer.parseInt(matcher.group("id").trim());
            String message = matcher.group("message");
            Result result = controller.addReview(id, rate, message);
            System.out.println(result);
        }else if((matcher = ProductMenuCommands.AddToCart.getMatcher(input)) != null){ //9
            int productId = Integer.parseInt(matcher.group("productId").trim());
            int amount = Integer.parseInt(matcher.group("amount").trim());
            Result result = controller.addToCart(productId, amount);
            System.out.println(result);
        }else if((matcher = ProductMenuCommands.ShowInformation.getMatcher(input)) != null){

            int id = Integer.parseInt(matcher.group("productId").trim());
            Product product = controller.getProductById(id);
            if(product == null){
                System.out.println("No product found.");
                return true;
            }
            System.out.println("Product Details  \n" +
                    "------------------------------------------------");
            String msg = "";
            if(product.getQuantity() == 0)
                msg = "**(Sold out!)**";
            else if(product.hasDiscount())
                msg = "**(On Sale! "+product.getDiscount().getQuantity()+" units discounted)**";
            System.out.println("Name: " + product.getFeatures().getName() + "  " + msg);
            System.out.println("ID: " + product.getFeatures().getId());
            System.out.printf("Rating: %.1f/5  \n", product.getFeatures().getRate());

            if(product.hasDiscount())
                System.out.printf("Price: ~$%.1f~ → $%.1f (-%d%%)\n", product.getFeatures().getPrice(),
                        product.calculateDiscount(), product.getDiscount().getPercentage());
            else
                System.out.printf("Price: $%.1f\n", product.getFeatures().getPrice());

            System.out.println("Brand: " + product.getFeatures().getStore().getBrand());
            System.out.println("Number of Products Remaining: " + product.getQuantity());
            System.out.println("About this item:  ");
            System.out.println(product.getFeatures().getDescription() + "\n");
            System.out.println("Customer Reviews:  \n" +
                    "------------------------------------------------");
            for(Review review : product.getFeatures().getReviews()){
                System.out.println(review.getUser().getFirstName() + " " + review.getUser().getLastName()+" ("+review.getRate()+"/5)");
                if(review.getText() != null)
                    System.out.println("\""+review.getText()+"\"  ");
                System.out.println("------------------------------------------------");
            }
        }else {
            System.out.println("invalid command");
            return false;
        }
        return true;
    }
    public void showProduct(){
        System.out.println("Product List (Sorted by: "
                +sortBy.substring(0, 1).toUpperCase() + sortBy.substring(1)+")"
                + "\n------------------------------------------------");

        for(int i = productPageNumber*10; i < Math.min(products.size(), productPageNumber*10+10); i++){
            Product product = products.get(i);
            printProduct(product);
            System.out.println("------------------------------------------------");
        }
        System.out.println("(Showing "+(productPageNumber*10+1)+"-"+(productPageNumber*10+10)+" out of "+products.size()+")");
        if(products.size() > productPageNumber*10+10)
            System.out.println("Use `show next 10 products' to see more.");
    }

    public void printProduct(Product p){
        String msg = "";
        if(p.getQuantity() == 0)
            msg = "**(Sold out!)**";
        else if(p.hasDiscount())
            msg = "**(On Sale! "+p.getDiscount().getQuantity()+" units discounted)**";

        System.out.println("ID: " + p.getFeatures().getId() + "  " + msg);
        System.out.println("Name: " + p.getFeatures().getName());
        System.out.printf("Rate: %.1f/5  \n", p.getFeatures().getRate());

        if(p.hasDiscount())
            System.out.printf("Price: ~$%.1f~ → $%.1f (-%d%%)\n", p.getFeatures().getPrice(),
                    p.calculateDiscount(), p.getDiscount().getPercentage());
        else
            System.out.printf("Price: $%.1f\n", p.getFeatures().getPrice());
        System.out.println("Brand: " + p.getFeatures().getStore().getBrand());
        System.out.println("Stock: " + p.getQuantity());
    }
}
