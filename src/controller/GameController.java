package controller;

import builders.GoalKeeperCardBuilder;
import builders.PlayerCardBuilder;
import decorators.goalKeeperDecorators.*;
import decorators.playersDecorators.*;
import directors.Directors;
import enums.Strategy;
import models.*;

import java.util.regex.Matcher;

public class GameController {
    public void buildPlayer(Matcher matcher) {
        String name = matcher.group("name");
        name = name.trim();
        String nationality = matcher.group("nationality");
        nationality = nationality.trim();
        int shootingAbility = Integer.parseInt(matcher.group("shooting").trim());
        int pace = Integer.parseInt(matcher.group("pace").trim());
        int dribblingAbility = Integer.parseInt(matcher.group("dribbling").trim());
        int physicalAbility = Integer.parseInt(matcher.group("physic").trim());
        int passingAbility = Integer.parseInt(matcher.group("passing").trim());
        int defendingAbility = Integer.parseInt(matcher.group("defending").trim());
        Directors.setPlayerCardBuilder(name, nationality, shootingAbility, pace, dribblingAbility, physicalAbility,
                passingAbility, defendingAbility);
    }

    public void buildGoalKeeper(Matcher matcher) {
        String name = matcher.group("name");
        name = name.trim();
        String nationality = matcher.group("nationality");
        nationality = nationality.trim();
        int divingAbility = Integer.parseInt(matcher.group("diving").trim());
        int handlingAbility = Integer.parseInt(matcher.group("handling").trim());
        int reflex = Integer.parseInt(matcher.group("reflex").trim());
        int position = Integer.parseInt(matcher.group("positioning").trim());
        int kickingAbility = Integer.parseInt(matcher.group("kicking").trim());
        int speed = Integer.parseInt(matcher.group("speed").trim());
        Directors.setGoalKeeperCardBuilder(name, nationality, divingAbility, handlingAbility, reflex, position, kickingAbility, speed);
    }

    public String buyingPlayer(Matcher matcher) {
        String name = matcher.group("name");
        name = name.trim();
        PlayerInterface card = getPlayer(name);
        if (card == null) {
            GoalKeeperInterface secondCard = getGoalKeeper(name);
            if (secondCard.getPrice() > Team.money) {
                return "8 - 2 ";
            } else {
                Team.goalKeeperCards.add(secondCard);
                Team.addMoney(-secondCard.getPrice());
                return "";
            }
        }
        if (card.getPrice() > Team.money) {
            return "8 - 2 ";
        } else {
            Team.playerCards.add(card);
            Team.addMoney(-card.getPrice());
            return "";
        }
    }

    public PlayerInterface getPlayer(String cardName) {
        for (PlayerInterface card : App.playerCards) {
            if (card.getName().equals(cardName)) {
                return card;
            }
        }
        return null;
    }

    public GoalKeeperInterface getGoalKeeper(String cardName) {
        for (GoalKeeperInterface card : App.goalKeeperCards) {
            if (card.getName().equals(cardName)) {
                return card;
            }
        }
        return null;
    }

    public void sellingPlayer(Matcher matcher) {
        String name = matcher.group("name");
        name = name.trim();
        PlayerInterface card = getPlayer(name);
        if (card == null) {
            GoalKeeperInterface secondCard = getGoalKeeper(name);
            Team.goalKeeperCards.add(secondCard);
            Team.addMoney(card.getPrice() / 2);
            return;
        }
        Team.playerCards.remove(card);
        Team.addMoney(card.getPrice() / 2);
    }

    public void selectPlayer(Matcher matcher) {
        String name = matcher.group("name");
        name = name.trim();
        String position = matcher.group("position");
        name = name.trim();
        PlayerInterface card = getPlayer(name);
        if (card == null && position.equals("gk")) {
            Team.gk = getGoalKeeper(name);
            return;
        }
        switch (position) {
            case "st":
            Team.st = card;
            break;

            case "cb":
                Team.cb = card;
                break;

        }
    }

    public void showLineup() {
        if (Team.st != null) {
            System.out.println("striker: " + Team.st.getName() + " " + Team.st.getNationality() + " " + Team.st.getShootingAbility()
                    + " " + Team.st.getPace() + " " + Team.st.getDribblingAbility() + " " + Team.st.getPhysicalAbility()
                    + " " + Team.st.getPassingAbility() + " " + Team.st.getDefendingAbility() + " " + Team.st.getPlayStyleName());
        } else {
            System.out.println("striker: ");
        }
        if (Team.cb != null) {
            System.out.println("center back: " + Team.cb.getName() + " " + Team.cb.getNationality() + " " + Team.cb.getShootingAbility()
                    + " " + Team.cb.getPace() + " " + Team.cb.getDribblingAbility() + " " + Team.cb.getPhysicalAbility()
                    + " " + Team.cb.getPassingAbility() + " " + Team.cb.getDefendingAbility() + " " + Team.cb.getPlayStyleName());
        } else {
            System.out.println("center back: ");
        }
        if (Team.gk != null) {
            System.out.println("goal keeper: " + Team.gk.getName() + " " + Team.gk.getNationality() + " " + Team.gk.getDivingAbility()
                    + " " + Team.gk.getHandlingAbility() + " " + Team.gk.getReflex() + " " + Team.gk.getPosition()
                    + " " + Team.gk.getKickingAbility() + " " + Team.gk.getSpeed());
        } else {
            System.out.println("goal keeper: ");
        }
    }

    public void decorate(Matcher matcher) {
        String position = matcher.group("position");
        position = position.trim();
        String decoration = matcher.group("decoration");
        decoration = decoration.trim();
        switch (position) {
            case "cb":
            setDecorationForPlayer(decoration, 1);
            break;

            case "gk":
                setDecorationForGoalkeeper(decoration);
                break;

            case "st":
                setDecorationForPlayer(decoration, 2);
                break;
        }
    }

    public void setDecorationForPlayer(String decoration, int x) {
        switch (decoration) {
            case "bronze":
                Team.addMoney(-50000000);
                if (x == 2)
                    Team.st = new BronzePlayerCardDecorator(Team.st);
                else
                    Team.cb = new BronzePlayerCardDecorator(Team.cb);
                break;
            case "silver":
                Team.addMoney(-70000000);
                if (x == 2)
                    Team.st = new SilverPlayerCardDecorator(Team.st);
                else
                    Team.cb = new SilverPlayerCardDecorator(Team.cb);
                break;
            case "gold":
                Team.addMoney(-100000000);
                if (x == 2)
                    Team.st = new GoldPlayerCardDecorator(Team.st);
                else
                    Team.cb = new GoldPlayerCardDecorator(Team.cb);
                break;
            case "hero":
                Team.addMoney(-150000000);
                if (x == 2)
                    Team.st = new HeroPlayerCardDecorator(Team.st);
                else
                    Team.cb = new HeroPlayerCardDecorator(Team.cb);
                break;
            case "icon":
                Team.addMoney(-200000000);
                if (x == 2)
                    Team.st = new IconPlayerCardDecorator(Team.st);
                else
                    Team.cb = new IconPlayerCardDecorator(Team.cb);
                break;
        }
    }

    public void setDecorationForGoalkeeper(String decoration) {
        switch (decoration) {
            case "bronze":
                Team.addMoney(-50000000);
                Team.gk = new BronzeGoalKeeperCardDecorator(Team.gk);
                break;
            case "silver":
                Team.addMoney(-70000000);
                Team.gk = new SilverGoalKeeperCardDecorator(Team.gk);
                break;
            case "gold":
                Team.addMoney(-100000000);
                Team.gk = new GoldGoalKeeperCardDecorator(Team.gk);
                break;
            case "hero":
                Team.addMoney(-150000000);
                Team.gk = new HeroGoalKeeperCardDecorator(Team.gk);
                break;
            case "icon":
                Team.addMoney(-200000000);
                Team.gk = new IconGoalKeeperCardDecorator(Team.gk);
                break;

        }
    }

    public void setPlayStyle(Matcher matcher) {
        String position = matcher.group("position");
        position = position.trim();
        String style = matcher.group("style");
        style = style.trim();
        Strategy playStyle = getPlayStyle(style);
        switch (position) {
            case "st":
                Team.st.setPlayStyle(playStyle.strategy);
                Team.st.setPlayStyleName(playStyle.name);
                Team.addMoney(-playStyle.money);
                break;

            case "cb":
                Team.cb.setPlayStyle(playStyle.strategy);
                Team.cb.setPlayStyleName(playStyle.name);
                Team.addMoney(-playStyle.money);
                break;
        }
    }

    public Strategy getPlayStyle(String style) {
        switch (style) {
            case "aggressive":
                return Strategy.Aggressive;
            case "defensive":
                return Strategy.defensive;
            case "balanced":
                return Strategy.Balanced;
        }
        return Strategy.Balanced;
    }

    public int calculatePower() {
        int power = 0;
        if (Team.gk != null)
            if (Team.gk.getStrategy() != null)
                power += Team.gk.getStrategy().getPlayStyle().calculatePower(0);
            else {
                power += Team.gk.calculatePower();
            }
        if (Team.cb != null)
            if (Team.cb.getStrategy() != null)
                power += Team.cb.getStrategy().getPlayStyle().calculatePower(1);
            else
                power += Team.cb.calculatePower();
        if (Team.st != null)
            if (Team.st.getStrategy() != null)
                power += Team.st.getStrategy().getPlayStyle().calculatePower(2);
             else
                power += Team.st.calculatePower();
        return power / 3;
    }
}
