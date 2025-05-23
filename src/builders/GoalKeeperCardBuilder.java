package builders;

import enums.Strategy;
import decorators.goalKeeperDecorators.GoalKeeperInterface;
import models.App;
import models.GoalKeeperCard;

public class GoalKeeperCardBuilder implements Builder {
    private String name;
    private String nationality;
    private int speed;
    private int divingAbility;
    private int handingAbility;
    private int reflex;
    private int position;
    private int kickingAbility;

    public GoalKeeperCardBuilder name(String name) {
        this.name = name;
        return this;
    }

    public GoalKeeperCardBuilder nationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public GoalKeeperCardBuilder speed(int speed) {
        this.speed = speed;
        return this;
    }

    public GoalKeeperCardBuilder divingAbility(int divingAbility) {
        this.divingAbility = divingAbility;
        return this;
    }

    public GoalKeeperCardBuilder handingAbility(int handingAbility) {
        this.handingAbility = handingAbility;
        return this;
    }

    public GoalKeeperCardBuilder reflex(int reflex) {
        this.reflex = reflex;
        return this;
    }

    public GoalKeeperCardBuilder position(int position) {
        this.position = position;
        return this;
    }

    public GoalKeeperCardBuilder kickingAbility(int kickingAbility) {
        this.kickingAbility = kickingAbility;
        return this;
    }

    public void build() {
        GoalKeeperInterface goalKeeperCard = new GoalKeeperCard(this.name, this.nationality, this.divingAbility, this.handingAbility,
                this.reflex, this.position, this.kickingAbility, this.speed, Strategy.Balanced.strategy);
        App.goalKeeperCards.add(goalKeeperCard);
    }
}