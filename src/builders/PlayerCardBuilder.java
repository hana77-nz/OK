package builders;

import decorators.playersDecorators.PlayerInterface;
import enums.Strategy;
import models.App;
import models.PlayerCard;

public class PlayerCardBuilder implements Builder {
    private String name;
    private String nationality;
    private int shootingAbility;
    private int pace;
    private int dribblingAbility;
    private int defendingAbility;
    private int passingAbility;
    private int physicalAbility;

    public PlayerCardBuilder name(String name) {
        this.name = name;
        return this;
    }

    public PlayerCardBuilder nationality(String nationality) {
        this.nationality = nationality;
         return this;
    }

    public PlayerCardBuilder shootingAbility(int shootingAbility) {
        this.shootingAbility = shootingAbility;
        return this;
    }
    public PlayerCardBuilder pace(int pace) {
        this.pace = pace;
        return this;
    }

    public PlayerCardBuilder dribblingAbility(int dribblingAbility) {
        this.dribblingAbility = dribblingAbility;
        return this;
    }

    public PlayerCardBuilder defendingAbility(int defendingAbility) {
        this.defendingAbility = defendingAbility;
        return this;
    }

    public PlayerCardBuilder passingAbility(int passingAbility) {
        this.passingAbility = passingAbility;
        return this;
    }

    public PlayerCardBuilder physicalAbility(int physicalAbility) {
        this.physicalAbility = physicalAbility;
        return this;
    }

    public void build() {
            PlayerInterface playerCard = new PlayerCard(this.name, this.nationality, this.shootingAbility, this.pace, this.dribblingAbility,
                    this.physicalAbility, this.passingAbility, this.defendingAbility, Strategy.Balanced.strategy);
            App.playerCards.add(playerCard);
        }
}
