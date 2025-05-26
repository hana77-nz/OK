package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum StoreMenuCommands implements Command {
        AddProduct("\\s*add\\s+product\\s+-n\\s+\"(?<name>.*)\"\\s+-pc (?<producerCost>.*) -p (?<price>.*)" +
                " -about\\s+\"(?<about>.*)\"\\s+-np (?<NumberOfProductToSell>.*)"),
        ApplyDiscount("\\s*apply\\s+discount\\s+-p (?<productId>.*) -d" +
                "\\s+(?<discountPercentage>-?\\d+(\\.\\d+)?)\\s+-q (?<quantity>.*)"),
        ShowProfit("\\s*show\\s+profit\\s*"),
        ShowListOfProducts("\\s*show\\s+list\\s+of\\s+products\\s*"),
        AddStock("\\s*add\\s+stock -product (?<productId>.*) -amount (?<amount>.*)"),
        UpdatePrice("\\s*update\\s+price\\s+-product (?<productId>.*) -price\\s+(?<newPrice>-?\\d+(\\.\\d+)?)\\s*");

        private String command;
        StoreMenuCommands(String command) {
            this.command = command;
        }

        public Matcher getMatcher(String input){
            Matcher matcher = Pattern.compile(command).matcher(input);
            if(matcher.matches())
                return matcher;
            return null;
        }

}
