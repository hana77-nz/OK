package models;


import java.util.regex.Pattern;

public class Address {
    private int id;
    private String country;
    private String city;
    private String street;
    private String post;


    public Address(int id, String country, String city, String street, String post) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.street = street;
        this.post = post;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country; // خطا داریم؟
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {  //خطا؟
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPost() {
        return post;
    }

    public boolean setPost(String post) {
        if (!validationOfPost(post)) {
            return false;
        }
        this.post = post;
        return true;
    }

    public static boolean validationOfPost(String post) {
        String regex = "^[0-9]{10}$";
        return Pattern.compile(regex).matcher(post).matches();
    }
    @Override
    public String toString() {
        return street + ", " + city + ", " + country;
    }
}
