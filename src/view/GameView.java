package view;

import controller.GameController;
import enums.GameCommands;
import models.Team;


import java.util.Scanner;
import java.util.regex.Matcher;

public class GameView {
    public void run(Scanner scanner) {
        String command = "";
        Matcher matcher;
        GameController controller = new GameController();
        while (!command.equals("soot")) {
            command = scanner.nextLine().trim();

            if (( GameCommands.BuildPlayer.getMatcher(command)) != null) {
                matcher = GameCommands.BuildPlayer.getMatcher(command);
                controller.buildPlayer(matcher);
            } else if ((GameCommands.BuildGoalKeeper.getMatcher(command)) != null) {
                matcher = GameCommands.BuildGoalKeeper.getMatcher(command);
                controller.buildGoalKeeper(matcher);
            } else if ((GameCommands.BuyPlayer.getMatcher(command)) != null) {
                matcher = GameCommands.BuyPlayer.getMatcher(command);
                System.out.println(controller.buyingPlayer(matcher));
            } else if ((GameCommands.SellPlayer.getMatcher(command)) != null) {
                matcher = GameCommands.SellPlayer.getMatcher(command);
                controller.sellingPlayer(matcher);
            } else if ((GameCommands.Select.getMatcher(command)) != null) {
                matcher = GameCommands.Select.getMatcher(command);
                controller.selectPlayer(matcher);
            } else if ((GameCommands.Decoration.getMatcher(command)) != null) {
                matcher = GameCommands.Decoration.getMatcher(command);
                controller.decorate(matcher);
            } else if ((GameCommands.PlayStyle.getMatcher(command)) != null) {
                matcher = GameCommands.PlayStyle.getMatcher(command);
                controller.setPlayStyle(matcher);
            } else if (GameCommands.ShowLineup.getMatcher(command) != null) {
                controller.showLineup();
            } else if (GameCommands.ShowMoney.getMatcher(command) != null) {
                System.out.println(Team.money);
            } else if(GameCommands.TeamPower.getMatcher(command) != null) {
                System.out.println(controller.calculatePower());
            }
        }
        int power = controller.calculatePower();
        if(power > 90)
            System.out.println("Visca Barca");
        else if(power == 90)
            System.out.println("draw");
        else
            System.out.println("Hala Madrid");
    }
}
