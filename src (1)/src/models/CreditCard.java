package models;


import java.util.regex.Pattern;

public class CreditCard {
    private int id;
    private String number;
    private Date expirationDate;
    private String cvv;
    private double initialValue;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public boolean setNumber(String number) {
        if(!CreditCard.validationOfNumber(number))
            return false;
        this.number = number;
        return true;    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public boolean setExpirationDate(String date) {
        if(Date.fromString(date) == null)
            return false;
        this.expirationDate = Date.fromString(date);
        return true;
    }

    public String getCvv() {
        return cvv;
    }

    public boolean setCvv(String cvv) {
        if(!CreditCard.validationOfCvv(cvv))
            return false;
           this.cvv = cvv;
        return true;
    }

    public double getInitialValue() {
        return initialValue;
    }

    public boolean setInitialValue(double value) {
        if(value < 0)
            return false;
        this.initialValue = value;
        return true;
    }

    public boolean addValue(double value){
        this.initialValue += value;
        return true;
    }

    public static boolean validationOfNumber(String number) {
        String regex = "^[0-9]{16}$";
        return Pattern.compile(regex).matcher(number).matches();
    }
    public static boolean validationOfCvv(String cvv) {
        String regex = "^[0-9]?[0-9]{3}$";
        return Pattern.compile(regex).matcher(cvv).matches();
    }


}

