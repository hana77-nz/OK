package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum UserMenuCommands {
    ListOrder("\\s*list\\s+my\\s+orders\\s*"),
    ShowOrderDetails("\\s*show\\s+order\\s+details\\s+-id\\s+(?<id>-?\\d+)\\s*"),
    EditName("\\s*edit\\s+name\\s+-fn (?<firstName>.*) -ln (?<lastName>.*) -p (?<password>.*)"),
    EditEmail("\\s*edit\\s+email\\s+-e (?<email>.*) -p (?<password>.*)"),
    EditPassword("\\s*edit\\s+password\\s+-np (?<newPassword>.*) -op (?<oldPassword>.*)"),
    ShowMyInfo("\\s*show\\s+my\\s+info\\s*"),
    AddAddress("\\s*add\\s+address\\s+-country (?<country>.*) -city (?<city>.*) -street (?<street>.*) -postal (?<postal>.*)"),
    DeleteAddress("\\s*delete\\s+address\\s+-id\\s+(?<id>-?\\d+)\\s*"),
    ListAddresses("\\s*list\\s+my\\s+addresses\\s*"),
    AddCreditCard("\\s*add\\s+a\\s+credit\\s+card\\s+-number (?<number>.*) -ed (?<expiryDate>.*) -cvv (?<cvv>.*)" +
            " -initialValue (?<initialValue>-?\\d+(\\.\\d+)?)\\s*"),
    ChargeCreditCard("\\s*Charge\\s+credit\\s+card\\s+-a\\s+(?<amount>-?\\d+(\\.\\d+)?)\\s+-id\\s+(?<id>-?\\d+)\\s*"),
        CheckCreditCardBalance("\\s*Check\\s+credit\\s+card\\s+balance\\s+-id\\s+(?<id>-?\\d+)\\s*"),
    ShowProductsInCart("\\s*show\\s+products\\s+in\\s+cart\\s*"),
    Checkout("\\s*checkout\\s+-card\\s+(?<cardId>-?\\d+)\\s+-address\\s+(?<addressId>\\d+)\\s*"),
    RemoveFromCart("\\s*remove\\s+from\\s+cart\\s+-product\\s+(?<productId>-?\\d+)\\s+-quantity\\s+(?<amount>-?\\d+)\\s*");

    private String command;
    UserMenuCommands(String command) {
        this.command = command;
    }

    public Matcher getMatcher(String input){
        Matcher matcher = Pattern.compile(command).matcher(input);
        if(matcher.matches())
            return matcher;
        return null;
    }
}
