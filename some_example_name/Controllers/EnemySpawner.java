//package io.github.some_example_name.Controllers;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.math.Vector2;
//import io.github.some_example_name.Models.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//public class EnemySpawner {
//    private final Player player;
//    private final List<Enemy> enemies;
//    private float spawnTimer = 0;
//    private float spawnInterval = 2f; // هر ۲ ثانیه یک دشمن
//    private final Random random = new Random();
//
//    public EnemySpawner(Player player) {
//        this.player = player;
//        this.enemies = new ArrayList<>();
//    }
//
//    public void update(float delta) {
//        spawnTimer -= delta;
//        if (spawnTimer <= 0) {
//            spawnEnemy();
//            spawnTimer = spawnInterval;
//        }
//
//        for (Enemy enemy : enemies) {
//            enemy.update(delta, player);
//        }
//    }
//
//    private void spawnEnemy() {
//        Vector2 spawnPos = getRandomSpawnPosition();
//        Enemy enemy;
//        int rand = random.nextInt(4);
//
//        switch (rand) {
//            case 0: enemy = new TentacleMonster(spawnPos); break;
//            case 1: enemy = new EyeBat(spawnPos); break;
//            case 2: enemy = new Tree(spawnPos); break;
//            case 3: enemy = new Elder(spawnPos); break;
//            default: enemy = new TentacleMonster(spawnPos); break;
//        }
//
//        enemies.add(enemy);
//    }
//
//    private Vector2 getRandomSpawnPosition() {
//        float x = random.nextBoolean() ? -50 : Gdx.graphics.getWidth() + 50;
//        float y = random.nextInt(Gdx.graphics.getHeight());
//        return new Vector2(x, y);
//    }
//
//    public List<Enemy> getEnemies() {
//        return enemies;
//    }
//}
