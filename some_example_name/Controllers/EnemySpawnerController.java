package io.github.some_example_name.Controllers;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import io.github.some_example_name.Models.*;
import io.github.some_example_name.Models.Enemy.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EnemySpawnerController {
    private final List<Enemy> enemies;
    private final Player player;

    private float totalElapsedTime = 0;

    private float tentacleTimer = 0;
    private float eyebatTimer = 0;

    private boolean elderSpawned = false;
    private boolean treeSpawned = false;

    public EnemySpawnerController(Player player) {
        this.player = player;
        this.enemies = new ArrayList<>();
    }

    public void update(float delta) {
        totalElapsedTime += delta;

        tentacleTimer += delta;
        eyebatTimer += delta;

        // TentacleMonster: هر 3 ثانیه بعد از 30 ثانیه
        if (totalElapsedTime >= 30 && tentacleTimer >= 3f) {
            spawnTentacle();
            tentacleTimer = 0;
        }

        // EyeBat: طبق فرمول (حداقل 1.5 ثانیه)
        float eyebatInterval = Math.max(1.5f, (4 * 30 - totalElapsedTime + 30) / 60f);
        if (totalElapsedTime >= 30 && eyebatTimer >= eyebatInterval) {
            spawnEyebat();
            eyebatTimer = 0;
        }

        // Elder: فقط یک بار بعد از نصف زمان (مثلاً 10 دقیقه = 600s)
        if (!elderSpawned && totalElapsedTime >= 300) {
            spawnElder();
            elderSpawned = true;
        }

        // Tree: فقط یک بار در اول بازی
        if (!treeSpawned) {
            spawnTree();
            treeSpawned = true;
        }

        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();
            if (enemy.isDead()) {
                iterator.remove();
                player.addKills(1);
                player.gainXP(enemy.getXpDrop());
            } else {
                enemy.update(delta, player);
            }
        }
    }

    private void spawnTentacle() {
        enemies.add(new TentacleMonster(getRandomSpawnPosition()));
    }

    private void spawnEyebat() {
        enemies.add(new EyeBat(getRandomSpawnPosition()));
    }

    private void spawnElder() {
        enemies.add(new Elder(getRandomSpawnPosition()));
    }

    private void spawnTree() {
        enemies.add(new Tree(getRandomSpawnPosition()));
    }

    private Vector2 getRandomSpawnPosition() {
        float screenW = 1920;
        float screenH = 1080;
        float x = MathUtils.random(0, screenW);
        float y = MathUtils.random(0, screenH);
        return new Vector2(x, y);
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
}
