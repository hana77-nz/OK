package models;

public class Result {
   public boolean successful;
   public String message;

    public Result(boolean success, String message) {
        this.successful = success;
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
