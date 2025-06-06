// WorldController.java
package io.github.some_example_name.Controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import io.github.some_example_name.Main;
import io.github.some_example_name.Models.Enemy;
import io.github.some_example_name.Models.Player;
import io.github.some_example_name.Models.TentacleMonster;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class WorldController {
    private PlayerController playerController;
    private Texture backgroundTexture;
    private float backgroundX = 0, backgroundY = 0;

    private List<Enemy> enemies;
    private float enemySpawnTimer = 0;
    private final float ENEMY_SPAWN_INTERVAL = 2f;
    private final Random random = new Random();

    public WorldController(PlayerController playerController) {
        this.backgroundTexture = new Texture("Images/Backgrounds/GameBG.png");
        this.playerController = playerController;
        this.enemies = new ArrayList<>();
        enemies.add(new TentacleMonster(new Vector2(500, 500))); // نمونه تستی
        enemies.add(new TentacleMonster(new Vector2(600, 400)));
        enemies.add(new TentacleMonster(new Vector2(700, 300)));
    }

    // فقط منطق بازی (بدون draw)
    public void update() {
        Player player = playerController.getPlayer();

        // آپدیت موقعیت بک‌گراند نسبت به پلیر (ولی draw اینجا نداریم)
        backgroundX = player.getPosX() - Main.getWidth() / 2f;
        backgroundY = player.getPosY() - Main.getHeight() / 2f;

        // زمان‌سنج اسپاون دشمن
        enemySpawnTimer += Gdx.graphics.getDeltaTime();
        if (enemySpawnTimer >= ENEMY_SPAWN_INTERVAL) {
            spawnEnemyNearPlayer(player);
            enemySpawnTimer = 0;
        }

        // آپدیت دشمن‌ها
        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();
            enemy.update(Gdx.graphics.getDeltaTime(), player);
            if (!enemy.isAlive()) {
                iterator.remove();
            }
        }
    }

    // فقط رندر (draw)
    public void render(float offsetX, float offsetY) {
        Main.getBatch().draw(backgroundTexture, backgroundX + offsetX, backgroundY + offsetY);
        for (Enemy enemy : enemies) {
            enemy.draw(Main.getBatch(), offsetX, offsetY);
        }
    }


    private void spawnEnemyNearPlayer(Player player) {
        float px = player.getPosX();
        float py = player.getPosY();

        float offsetX = (random.nextBoolean() ? 1 : -1) * (300 + random.nextInt(200));
        float offsetY = (random.nextBoolean() ? 1 : -1) * (300 + random.nextInt(200));
        Vector2 spawnPos = new Vector2(px + offsetX, py + offsetY);

        enemies.add(new TentacleMonster(spawnPos));
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
}
