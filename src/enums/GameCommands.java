package enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameCommands {
    BuildPlayer("build\\s+player\\s+(?<name>.+)\\s+(?<nationality>.+)\\s+(?<shooting>\\d+)\\s+" +
            "(?<pace>\\d+)\\s+(?<dribbling>\\d+)\\s+(?<physic>\\d+)\\s+(?<passing>\\d+)\\s+(?<defending>\\d+)"),
    BuildGoalKeeper("build\\s+goalie\\s+(?<name>.+)\\s+(?<nationality>.+)\\s+(?<diving>\\d+)\\s+" +
            "(?<handling>\\d+)\\s+(?<reflex>\\d+)\\s+(?<positioning>\\d+)\\s+(?<kicking>\\d+)\\s+(?<speed>\\d+)"),
    BuyPlayer("buy\\s+(?<name>.+)"),
    SellPlayer("sell\\s+(?<name>.+)"),
    Select("select\\s+(?<position>.+)\\s+(?<name>.+)"),
    Decoration("set\\s+decoration\\s+(?<position>.+)\\s+(?<decoration>.+)"),
    PlayStyle("set\\s+play\\s+style\\s+(?<position>.+)\\s+(?<style>.+)"),
    ShowLineup("show\\s+lineup"),
    ShowMoney("show\\s+money"),
    TeamPower("calculate\\s+team\\s+power");
    public final String regex;

    GameCommands(String regex) {
        this.regex = regex;
    }

    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(this.regex).matcher(input);
        if (matcher.matches()) {
            return matcher;
        }
        return null;

    }
}
