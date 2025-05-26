package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
    GoBAck("\\s*go\\s+back\\s*"),
    Exit("\\s*exit\\s*");

    private String command;
    Commands(String command) {
        this.command = command;
    }
    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(command).matcher(input);
        if (matcher.matches()){
            return matcher;
        }
        return null;
    }
}
