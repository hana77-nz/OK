package models;

import decorators.playersDecorators.PlayerInterface;
import strategies.StrategyClass;

public class PlayerCard implements PlayerInterface{
    private String name;
    private String nationality;
    private int price;
    private boolean isPlayer;
    private int shootingAbility;
    private int pace;
    private int dribblingAbility;
    private int physicalAbility;
    private int passingAbility;
    private int defendingAbility;
    private StrategyClass playStyle;
    private String playStyleName;

    public PlayerCard(String name, String nationality, int shootingAbility, int pace,
                      int dribblingAbility, int physicalAbility, int passingAbility, int defendingAbility, StrategyClass playStyle) {
        this.name = name;
        this.nationality = nationality;
        this.price =((shootingAbility + pace + dribblingAbility + physicalAbility + passingAbility + defendingAbility) / 6) * 10000000;
        this.isPlayer = true;
        this.shootingAbility = shootingAbility;
        this.pace = pace;
        this.dribblingAbility = dribblingAbility;
        this.physicalAbility = physicalAbility;
        this.passingAbility = passingAbility;
        this.defendingAbility = defendingAbility;
        this.playStyle = playStyle;
        this.playStyleName = "balanced";
    }

    @Override
    public int calculatePower() {
        return (shootingAbility + pace + dribblingAbility + physicalAbility + passingAbility + defendingAbility) / 6;
    }


    public int getPace() {
        return pace;
    }

    public void setPace(int pace) {
        this.pace = pace;
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

    public int getShootingAbility() {
        return shootingAbility;
    }

    public void setShootingAbility(int shootingAbility) {
        this.shootingAbility = shootingAbility;
    }

    public int getDribblingAbility() {
        return dribblingAbility;
    }

    public void setDribblingAbility(int dribblingAbility) {
        this.dribblingAbility = dribblingAbility;
    }

    public int getPhysicalAbility() {
        return physicalAbility;
    }

    public void setPhysicalAbility(int physicalAbility) {
        this.physicalAbility = physicalAbility;
    }

    public int getPassingAbility() {
        return passingAbility;
    }

    public void setPassingAbility(int passingAbility) {
        this.passingAbility = passingAbility;
    }

    public int getDefendingAbility() {
        return defendingAbility;
    }

    public void setDefendingAbility(int defendingAbility) {
        this.defendingAbility = defendingAbility;
    }

    public StrategyClass getPlayStyle() {
        return playStyle;
    }

    public void setPlayStyle(StrategyClass playStyle) {
        this.playStyle = playStyle;
    }

    public String getPlayStyleName() {
        return playStyleName;
    }

    public void setPlayStyleName(String playStyleName) {
        this.playStyleName = playStyleName;
    }
    public StrategyClass getStrategy() {
        return playStyle;
    }

}