package models;

import java.util.regex.Pattern;

public class Date {
    public int year;
    public int month;
    public Date(int year, int month) {
        this.year = year;
        this.month = month;
    }

    public static Date fromString(String str){
        String reg = "^\\d\\d\\s*/\\d\\d\\s*$";
        if(!Pattern.compile(reg).matcher(str).matches())
            return null;
        int month = Integer.parseInt(str.split("/")[0].trim());
        int year = Integer.parseInt(str.split("/")[1].trim());
        if(month < 1 || month > 12)
            return null;
        return new Date(year, month);
    }
}
