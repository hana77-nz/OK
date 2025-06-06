package io.github.some_example_name.Models;

public class PreGame {
    private final String username;
    private final String hero;
    private final String weapon;
    private final float duration;

    public PreGame(String username, String hero, String weapon, float duration) {
        this.username = username;
        this.hero = hero;
        this.weapon = weapon;
        this.duration = duration;
    }

    public String getUsername() { return username; }
    public String getHero() { return hero; }
    public String getWeapon() { return weapon; }
    public float getDuration() { return duration; }
}
