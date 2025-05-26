package models;

public class Discount{
    private int percentage;
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPercentage() {
        return percentage;
    }

    public boolean setPercentage(int percentage) {
        if(percentage < 1 || percentage > 100)
            return false;
        this.percentage = percentage;
        return true;
    }

}
