package io.github.some_example_name.Controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import io.github.some_example_name.Models.*;
import io.github.some_example_name.Views.GameOverView;
import io.github.some_example_name.Views.GameView;
import io.github.some_example_name.Main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.google.gson.Gson;

public class GameController {
    private GameView view;

    private PlayerController playerController;
    private WeaponController weaponController;
    private WorldController worldController;

  //  private final List<Enemy> enemies;
    private float enemySpawnTimer = 0f;
    private final float ENEMY_SPAWN_INTERVAL = 3f;
    private float playerHitCooldown = 0f;

    public GameController(Player player, PreGame preGame) {
        Weapon weapon = createWeaponFromName(preGame.getWeapon());

        playerController = new PlayerController(player);
        weaponController = new WeaponController(player, weapon);
        worldController = new WorldController(playerController);

//        enemies = new ArrayList<>();
//        enemies.add(new TentacleMonster(new Vector2(500, 500))); // نمونه تستی
//        enemies.add(new TentacleMonster(new Vector2(600, 400)));
//        enemies.add(new TentacleMonster(new Vector2(700, 300)));
    }

    public void setView(GameView view) {
        this.view = view;
    }

    public void updateGame(float delta) {
        playerController.update();
        weaponController.update(delta);
        worldController.update();

        Player player = playerController.getPlayer();

        // به‌روزرسانی دشمن‌ها (فقط update! draw فقط در GameView انجام میشه)
        for (Enemy enemy : worldController.getEnemies()) {
            enemy.update(delta, player);
        }

        // برخورد گلوله‌ها با دشمن
        handleBulletEnemyCollisions();

        // برخورد دشمن با پلیر
        handleEnemyPlayerCollisions(delta);


        // برخورد گلوله دشمن با پلیر (EyeBat)
        for (Enemy enemy : worldController.getEnemies()) {
            if (enemy instanceof EyeBat) {
                EyeBat eyeBat = (EyeBat) enemy;
                for (Bullet bullet : eyeBat.getBullets()) {
                    if (bullet.isActive() && bullet.getBounds().overlaps(player.getBounds())) {
                        bullet.deactivate();
                        if (!player.isInvincible())
                            player.takeDamage(1);
                       // player.takeDamage((int) bullet.getDamage());
                    }
                }
            }
        }

        // اسپاون دشمن‌ها
        enemySpawnTimer += delta;
        if (enemySpawnTimer >= ENEMY_SPAWN_INTERVAL) {
            spawnEnemy();
            enemySpawnTimer = 0;
        }

        // آپدیت تایمر بقا
        player.addSurviveTime(delta);
    }

    private void spawnEnemy() {
        Vector2 playerPos = new Vector2(playerController.getPlayer().getPosX(), playerController.getPlayer().getPosY());
        float angle = (float) (Math.random() * Math.PI * 2);
        float radius = 400f;

        float x = playerPos.x + (float) Math.cos(angle) * radius;
        float y = playerPos.y + (float) Math.sin(angle) * radius;
        Vector2 spawnPos = new Vector2(x, y);

        // انتخاب تصادفی نوع دشمن
        int type = (int)(Math.random() * 4);
        Enemy enemy;
        switch (type) {
            case 0: enemy = new TentacleMonster(spawnPos); break;
            case 1: enemy = new EyeBat(spawnPos); break;
            case 2: enemy = new Elder(spawnPos); break;
            case 3: enemy = new Tree(spawnPos); break;
            default: enemy = new TentacleMonster(spawnPos);
        }
        worldController.getEnemies().add(enemy);
    }

    private void handleBulletEnemyCollisions() {
        Iterator<Bullet> bulletIt = weaponController.getBullets().iterator();
        while (bulletIt.hasNext()) {
            Bullet bullet = bulletIt.next();
            Iterator<Enemy> enemyIt = worldController.getEnemies().iterator();
            while (enemyIt.hasNext()) {
                Enemy enemy = enemyIt.next();
                if (bullet.getSprite().getBoundingRectangle().overlaps(enemy.getSprite().getBoundingRectangle())) {
                    enemy.takeDamage(bullet.getDamage());
                    bullet.deactivate();

                    if (enemy.getHealth() <= 0) {
                        playerController.getPlayer().gainXP(enemy.getXpDrop());
                        enemy.die();  // اگر انیمیشن مرگ داری اینو نگه دار
                        enemyIt.remove(); // در غیر این صورت بلافاصله حذف کن
                        playerController.getPlayer().addKill();
                    }
                    break;
                }
            }
        }
    }

    private void handleEnemyPlayerCollisions(float delta) {
        Player player = playerController.getPlayer();

        if (playerHitCooldown > 0f)
            playerHitCooldown -= delta;

        for (Enemy enemy : getEnemies()) {
            if (!enemy.isAlive()) continue;
            // اگر انمی روی پلیر بود
            if (enemy.getSprite().getBoundingRectangle().overlaps(player.getPlayerSprite().getBoundingRectangle())) {
                if (playerHitCooldown <= 0f) {
                    player.takeDamage(1); // فقط یک واحد کم بشه
                    playerHitCooldown = 0.5f; // نیم ثانیه مصونیت بعد هر ضربه
                }
            }
        }
    }


    private Weapon createWeaponFromName(String weaponName) {
        switch (weaponName.toLowerCase()) {
            case "revolver":
                return new Weapon("Revolver", 20, 6, 0.5f, 1.2f);
            case "shotgun":
                return new Weapon("Shotgun", 40, 2, 1.0f, 2.0f);
            case "dual smgs":
                return new Weapon("Dual SMGs", 8, 30, 0.1f, 2.5f);
            default:
                return new Weapon("Pistol", 10, 12, 0.3f, 1.5f);
        }
    }

    public PlayerController getPlayerController() {
        return playerController;
    }

    public WeaponController getWeaponController() {
        return weaponController;
    }

    public WorldController getWorldController() {
        return worldController;
    }

    public List<Enemy> getEnemies() {
        return worldController.getEnemies();
    }


    public void saveGame() {
        GameSaveData save = new GameSaveData();

        // ذخیره بازیکن
        GameSaveData.PlayerData p = new GameSaveData.PlayerData();
        Player player = playerController.getPlayer();
        p.name = player.getUsername();
        p.health = player.getPlayerHealth();
        p.kills = player.getKills();
        p.x = player.getPosX();
        p.y = player.getPosY();
        save.player = p;

        // ذخیره اسلحه
        Weapon weapon = weaponController.getWeapon();
        GameSaveData.WeaponData w = new GameSaveData.WeaponData();
        w.name = weapon.getName();
        w.ammo = weapon.getAmmo();
        save.weapon = w;

        // ذخیره دشمن‌ها
        List<GameSaveData.EnemyData> enemyList = new ArrayList<>();
        for (Enemy e : worldController.getEnemies()) {
            GameSaveData.EnemyData ed = new GameSaveData.EnemyData();
            ed.type = e.getType();
            ed.x = e.getPosition().x;
            ed.y = e.getPosition().y;
            ed.health = e.getHealth();
            enemyList.add(ed);
        }
        save.enemies = enemyList;

        save.surviveTime = player.getSurviveTime();

        // نوشتن در فایل
        FileHandle file = Gdx.files.local("save.json");
        file.writeString(new Gson().toJson(save), false);
    }

    public static GameController loadGame() {
        FileHandle file = Gdx.files.local("save.json");

        if (!file.exists()) return null;

        Gson gson = new Gson();
        GameSaveData save = gson.fromJson(file.readString(), GameSaveData.class);

        // ساخت player
        GameSaveData.PlayerData p = save.player;
        Player player = new Player();
        player.setUsername(p.name);
        player.setPlayerHealth(p.health);
        player.setKills(p.kills);
        player.setPos(p.x, p.y);
        player.addSurviveTime(save.surviveTime);

        // ساخت weapon
        GameSaveData.WeaponData w = save.weapon;
        Weapon weapon = new Weapon(w.name, 10, w.ammo, 0.3f, 1.5f); // بقیه پارامترها تنظیم کن

        // ساخت controller
        GameController controller = new GameController(player, new PreGame(p.name, "", w.name, 20));
        controller.weaponController = new WeaponController(player, weapon);

        // بارگذاری دشمن‌ها
        for (GameSaveData.EnemyData e : save.enemies) {
            Enemy enemy;
            Vector2 pos = new Vector2(e.x, e.y);
            switch (e.type) {
                case "TentacleMonster": enemy = new TentacleMonster(pos); break;
                case "Elder": enemy = new Elder(pos); break;
                case "Tree": enemy = new Tree(pos); break;
                case "EyeBat": enemy = new EyeBat(pos); break;
                default: enemy = new TentacleMonster(pos);
            }
            enemy.setHealth(e.health);
            controller.getEnemies().add(enemy);
        }

        return controller;
    }
}
