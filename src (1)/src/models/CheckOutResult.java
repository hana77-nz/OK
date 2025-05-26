package models;

public class CheckOutResult {
    public Result result;
    public Order order;

    public CheckOutResult(Result result, Order order) {
        this.result = result;
        this.order = order;
    }
}
