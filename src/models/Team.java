package models;

import decorators.goalKeeperDecorators.GoalKeeperInterface;
import decorators.playersDecorators.PlayerInterface;

import java.util.ArrayList;

public class Team {
    public static GoalKeeperInterface gk;
    public static PlayerInterface cb;
    public static PlayerInterface st;
    public static ArrayList<PlayerInterface> playerCards = new ArrayList<>();
    public static ArrayList<GoalKeeperInterface> goalKeeperCards = new ArrayList<>();
    public static int money = 1000000000;

    public static void addMoney(int money) {
        Team.money += money;
    }
}
