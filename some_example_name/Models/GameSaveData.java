package io.github.some_example_name.Models;

import com.badlogic.gdx.math.Vector2;
import java.util.List;

public class GameSaveData {
    public PlayerData player;
    public List<EnemyData> enemies;
    public WeaponData weapon;
    public float surviveTime;

    public static class PlayerData {
        public String name;
        public int health;
        public int kills;
        public float x, y;
    }

    public static class EnemyData {
        public String type;
        public float x, y;
        public int health;
    }

    public static class WeaponData {
        public String name;
        public int ammo;
    }
}
