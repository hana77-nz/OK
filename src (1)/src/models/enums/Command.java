package models.enums;

import java.util.regex.Matcher;

public interface Command {
    public Matcher getMatcher(String input);
}
