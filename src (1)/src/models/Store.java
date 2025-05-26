package models;

public class Store extends AccountInfo{
    private String brand;
    private double profit = 0;
    private double totalCost = 0;

    public static boolean validationOfBrand(String brand) {
        return brand != null && brand.length() >= 3;
    }

    public String getBrand() {
        return brand;
    }

    public boolean setBrand(String brand) {
        if (!Store.validationOfBrand(brand)) {
            return false;
        }
        this.brand = brand;
        return true;
    }

    public double getProfit() {
        return profit;
    }

    public void addProfit(double profit) {
        this.profit += profit;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double Cost) {
        this.totalCost = Cost;
    }

    @Override
    public boolean isUser() {
        return false;
    }

    @Override
    public boolean isStore() {
        return true;
    }

    public void addCost(double cost) {
        this.totalCost += cost;
    }

}
