package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProductMenuCommands implements Command {
    ShowProducts("\\s*show\\s+products\\s+-sortBy (?<sortBy>.*)"),
    ShowNextProducts("\\s*show\\s+next\\s+10\\s+products\\s*"),
    ShowPastProducts("\\s*show\\s+past\\s+10\\s+products\\s*"),
    RateProduct("\\s*Rate\\s+product\\s+-r\\s+(?<rate>-?\\d)\\s*(-m \"(?<message>.*)\")?\\s+-id\\s+(?<id>-?\\d+)\\s*"),
    ShowInformation("\\s*show\\s+information\\s+of\\s+-id\\s*(?<productId>-?\\d+)\\s*"),
    AddToCart("\\s*add\\s+to\\s+cart\\s+-product\\s+(?<productId>-?\\d+)\\s+-quantity (?<amount>.*)");

    private String command;
    ProductMenuCommands(String command) {
        this.command = command;
    }

    public Matcher getMatcher(String input){
        Matcher matcher = Pattern.compile(command).matcher(input);
        if(matcher.matches())
            return matcher;
        return null;
    }
}
