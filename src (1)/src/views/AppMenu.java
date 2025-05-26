package views;

import models.enums.AppMenuCommands;
import models.enums.Commands;
import models.App;


import java.util.regex.Matcher;

public abstract class AppMenu {
    public boolean run(String input) {
        Matcher matcher;

        if((matcher = Commands.GoBAck.getMatcher(input)) != null) {
            if(App.getMenu() == AppMenuCommands.MainMenu){
                System.out.println("invalid command");
                return true;
            }
            App.setMenu(AppMenuCommands.MainMenu);
            return true;
        } else if((matcher = Commands.Exit.getMatcher(input)) != null) {
            App.setMenu(AppMenuCommands.Exit);
            return true;
        }
        return false;
    }
    public void showMessage(String name) {
        if(name.isEmpty()) {
            return;
        }
        System.out.println("Redirecting to the "+name+" ...");
    }
}
