package views;

import models.App;
import models.enums.AppMenuCommands;

import java.util.Scanner;

public class AppView {
    public static Scanner scanner = new Scanner(System.in);
    public static void run(){
        while(!App.getMenu().equals(AppMenuCommands.Exit)){
//            if (!scanner.hasNextLine()) break;
            String input = scanner.nextLine();
            try {
                App.getMenu().run(input);
            } catch (Exception e) {

            }
        }

    }
}
