package models;

import decorators.goalKeeperDecorators.GoalKeeperInterface;
import strategies.StrategyClass;

public class GoalKeeperCard implements GoalKeeperInterface {
    private String name;
    private String nationality;
    private int speed;
    private String playStyleName;
    private int divingAbility;
    private int handlingAbility;
    private int reflex;
    private int position;
    private int kickingAbility;
    private int price;
    private boolean isPlayer;
    private StrategyClass playStyle;

    public GoalKeeperCard (String name, String nationality, int divingAbility, int handlingAbility, int reflex,
    int position, int kickingAbility, int speed, StrategyClass strategy) {
        this.name = name;
        this.nationality = nationality;
        this.divingAbility = divingAbility;
        this.handlingAbility = handlingAbility;
        this.reflex = reflex;
        this.position = position;
        this.kickingAbility = kickingAbility;
        this.speed = speed;
        this.playStyle = strategy;
        this.price = ((divingAbility + handlingAbility + reflex + position + kickingAbility + speed) / 6) * 10000000;
        this.isPlayer = false;
        this.playStyleName = "balanced";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getPlayStyleName() {
        return playStyleName;
    }

    public void setPlayStyleName(String playStyleName) {
        this.playStyleName = playStyleName;
    }

    public int getDivingAbility() {
        return divingAbility;
    }

    public void setDivingAbility(int divingAbility) {
        this.divingAbility = divingAbility;
    }

    public int getHandlingAbility() {
        return handlingAbility;
    }

    public void setHandlingAbility(int handlingAbility) {
        this.handlingAbility = handlingAbility;
    }

    public int getReflex() {
        return reflex;
    }

    public void setReflex(int reflex) {
        this.reflex = reflex;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getKickingAbility() {
        return kickingAbility;
    }

    public void setKickingAbility(int kickingAbility) {
        this.kickingAbility = kickingAbility;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isPlayer() {
        return isPlayer;
    }

    public void setPlayer(boolean player) {
        isPlayer = player;
    }

    public StrategyClass getPlayStyle() {
        return playStyle;
    }

    public void setPlayStyle(StrategyClass playStyle) {
        this.playStyle = playStyle;
    }

    @Override
    public int calculatePower() {
        return (divingAbility + handlingAbility + reflex + position+ kickingAbility+ speed) / 6;
    }

    public StrategyClass getStrategy() {
        return playStyle;
    }

}
