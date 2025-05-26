package models.enums;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public enum LoginMenuCommands implements Command {
    CreateUser("\\s*create\\s+a\\s+user\\s+account\\s+-fn (?<firstName>.*) -ln (?<lastName>.*)" +
            " -p (?<password>.*) -rp (?<reEnteredPassword>.*) -e (?<email>.*)"),
    CreateStore("\\s*create\\s+a\\s+store\\s+account\\s+-b\\s+\"(?<brand>.*)\"" +
            "\\s+-p (?<password>.*) -rp (?<reEnteredPassword>.*) -e (?<email>.*)"),
    LoginUser("\\s*login\\s+as\\s+user\\s+-e (?<email>.*) -p (?<password>.*)"),
    LoginStore("\\s*login\\s+as\\s+store\\s+-e (?<email>.*) -p (?<password>.*)"),
    Logout("\\s*logout\\s*"),
    DeleteAccount("\\s*delete\\s+account\\s+-p (?<password>.*) -rp (?<reEnteredPassword>.*)");

    private String command;

    LoginMenuCommands(String command) {
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
