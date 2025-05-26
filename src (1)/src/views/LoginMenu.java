package views;

import controller.LoginMenuController;
import models.App;
import models.Result;
import models.enums.AppMenuCommands;
import models.enums.LoginMenuCommands;

import java.util.Locale;
import java.util.regex.Matcher;

public class LoginMenu extends AppMenu {
    LoginMenuController controller = new LoginMenuController();

    @Override
    public boolean run(String input) {
        if (super.run(input)) {
            return true;
        }
            Matcher matcher;
            if ((matcher = LoginMenuCommands.CreateUser.getMatcher(input)) != null) {
                String firstName = matcher.group("firstName").trim();
                String lastName = matcher.group("lastName").trim();
                String password = matcher.group("password").trim();
                String reEnteredPassword = matcher.group("reEnteredPassword").trim();
                String email = matcher.group("email").trim();

                Result result = controller.createUser(firstName, lastName, password, reEnteredPassword, email);
                System.out.println(result);
            } else if ((matcher = LoginMenuCommands.CreateStore.getMatcher(input)) != null) {
                String brand = matcher.group("brand"); //???
                String password = matcher.group("password").trim();
                String reEnteredPassword = matcher.group("reEnteredPassword").trim();
                String email = matcher.group("email").trim();

                Result result = controller.createStore(brand, password, reEnteredPassword, email);
                System.out.println(result);
            } else if ((matcher = LoginMenuCommands.LoginUser.getMatcher(input)) != null) {
                String password = matcher.group("password").trim();
                String email = matcher.group("email").trim();

                Result result = controller.loginUser(password, email);
                if (result.successful) {
                    System.out.print(result);
                    App.setMenu(AppMenuCommands.MainMenu);
                } else {
                    System.out.println(result);
                }
            } else if ((matcher = LoginMenuCommands.LoginStore.getMatcher(input)) != null) {
                String password = matcher.group("password").trim();
                String email = matcher.group("email").trim();

                Result result = controller.loginStore(password, email);
                if (result.successful) {
                    System.out.print(result);
                    App.setMenu(AppMenuCommands.MainMenu);
                } else {
                    System.out.println(result);
                }
            } else if ((matcher = LoginMenuCommands.Logout.getMatcher(input)) != null) {
                Result result = controller.logout();
                if (result.successful) {
                    System.out.print(result);
                    App.setMenu(AppMenuCommands.MainMenu);
                } else {
                    System.out.println(result);
                }
            } else if ((matcher = LoginMenuCommands.DeleteAccount.getMatcher(input)) != null) {
                String password = matcher.group("password").trim();
                String reEnteredPassword = matcher.group("reEnteredPassword").trim();

                Result result = controller.deleteAccount(password, reEnteredPassword);
                if (result.successful) {
                    System.out.print(result);
                    App.setMenu(AppMenuCommands.MainMenu);
                } else {
                    System.out.println(result);
                }
            } else {
                System.out.println("invalid command");
                return false;
            }
            return true;

    }

}
