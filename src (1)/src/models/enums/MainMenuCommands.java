package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainMenuCommands {
    GoTo("\\s*go\\s+to\\s+-m (?<name>.*)");
    private String command;
    MainMenuCommands(String command) {
        this.command = command;
    }

    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(command).matcher(input);
        if (matcher.matches()) {
            return matcher;
        }
    return null;
    }
}
