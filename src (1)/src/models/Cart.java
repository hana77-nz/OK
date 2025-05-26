package models;

import java.util.ArrayList;

public class Cart {
    private ArrayList<ProductInCart> products = new ArrayList<>();

    public double calculateTotal() {
        double total = 0;
        for (ProductInCart product : products) {
            total += product.calculateDiscount() * product.getQuantity();
        }
        return total;
    }

    public ProductInCart getProductById(int id) {
        for (ProductInCart product : products) {
            if (product.getFeatures().getId() == id) {
                return product;
            }
        }
        return null;
    }

        public void addProduct(ProductInCart product) {
            products.add(product);
        }

        public ArrayList<ProductInCart> getProducts() {
            return products;
        }


    }

