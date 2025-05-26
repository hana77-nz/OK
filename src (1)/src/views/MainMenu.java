package views;

import controller.MainMenuController;
import models.App;
import models.Result;
import models.enums.AppMenuCommands;
import models.enums.MainMenuCommands;

import java.util.regex.Matcher;

public class MainMenu extends AppMenu {
    MainMenuController controller = new MainMenuController();
    @Override
    public boolean run(String input) {
        if(super.run(input))
            return true;
        Matcher matcher;
        if((matcher = MainMenuCommands.GoTo.getMatcher(input)) != null){
            AppMenuCommands menu = AppMenuCommands.fromString(matcher.group("name").trim());
            if(menu == null){
                System.out.println("invalid command");
                return false;
            }
            Result result = controller.ChangeMenu(menu);
            if(result.successful)
                App.setMenu(menu);
            else
                System.out.println(result);
        }else {
            System.out.println("invalid command");
            return false;
        }
        return true;
    }

}
