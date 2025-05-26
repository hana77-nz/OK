package models.enums;
import views.*;

public enum AppMenuCommands {
    LoginMenu(new LoginMenu(), "LoginMenu"),
    MainMenu(new MainMenu(), "MainMenu"),
    StoreMenu(new StoreMenu(), "StoreMenu"),
    ProductMenu(new ProductMenu(), "ProductMenu"),
    UserMenu(new UserMenu(), "UserMenu"),
    Exit(new ExitMenu(), "");

    public AppMenu menu;
    public String text;

    AppMenuCommands(AppMenu menu, String text) {
        this.menu = menu;
        this.text = text;
    }

    public void run(String input) {
        menu.run(input);
    }

    public void showMessage() {
        menu.showMessage(text);
    }

    public static AppMenuCommands fromString(String input) {
        for (AppMenuCommands menu : AppMenuCommands.values()) {
            if (menu.text.equals(input)) {
                return menu;
            }
        }
        return null;
    }
}
