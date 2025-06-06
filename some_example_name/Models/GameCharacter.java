package io.github.some_example_name.Models;

public class GameCharacter {
    private String name;
    private int hp;
    private int speed;

    public GameCharacter(String name, int hp, int speed) {
        this.name = name;
        this.hp = hp;
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getSpeed() {
        return speed;
    }
}
