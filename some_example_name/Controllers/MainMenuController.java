package io.github.some_example_name.Controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.badlogic.gdx.math.Vector2;
import io.github.some_example_name.Main;
import io.github.some_example_name.Models.*;
import io.github.some_example_name.Views.*;

import java.util.ArrayList;
import java.util.List;

public class MainMenuController {
    private final Player currentPlayer;

    public MainMenuController(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    // هندلر شروع بازی جدید
    public void handleNewGame(Player currentPlayer) {
        CharacterSelectionController cController = new CharacterSelectionController(currentPlayer);
        CharacterSelectionView cView = new CharacterSelectionView(cController, GameAssetManager.getSkin());
        cController.setView(cView); // 👈 این خط الزامی است!
        Main.getMain().setScreen(cView);
    }


    // هندلر لود بازی ذخیره شده
    public void handleLoadGame(Player currentPlayer) {
        FileHandle file = Gdx.files.local("save.json");
        if (!file.exists()) {
            System.out.println("No saved game found.");
            // اینجا می‌تونی پیام به UI بفرستی
            return;
        }

        Gson gson = new Gson();
        String json = file.readString();
        GameSaveData saveData = gson.fromJson(json, GameSaveData.class);

        Player player = new Player();
        player.setUsername(saveData.player.name);
        player.setPlayerHealth(saveData.player.health);
        player.setKills(saveData.player.kills);
        player.setPos(saveData.player.x, saveData.player.y);
        player.addSurviveTime(saveData.surviveTime);

        PreGame preGame = new PreGame(
            saveData.player.name,
            "Dash",
            saveData.weapon.name,
            20 // می‌تونی مقدارش رو تغییر بدی
        );

        GameController gameController = new GameController(player, preGame);
        gameController.getWeaponController().getWeapon().setAmmo(saveData.weapon.ammo);

        for (GameSaveData.EnemyData e : saveData.enemies) {
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
            gameController.getEnemies().add(enemy);
        }

        Main.getMain().setScreen(new GameView(gameController));
    }

    // هندلر نمایش تنظیمات
    public void handleSettings() {
        Main.getMain().setScreen(new SettingsView(GameAssetManager.getSkin()));
    }

    // هندلر نمایش سازندگان
    public void handleCredits() {
        Main.getMain().setScreen(
            new CreditsView(
                GameAssetManager.getSkin(),
                currentPlayer // این متغیر باید Player فعلی باشه (مثلاً همون currentPlayer)
            )
        );
    }

    // هندلر نمایش پروفایل
    public void handleProfile(Player currentPlayer) {
        Main.getMain().setScreen(new ProfileView(currentPlayer, GameAssetManager.getSkin()));
    }

    // هندلر Scoreboard (جدید)
    public void handleScoreboard(Player currentPlayer) {
        List<Player> allPlayers = loadAllPlayers();
        Main.getMain().setScreen(new ScoreboardView(allPlayers, currentPlayer));
    }

    // لود لیست همه کاربران از فایل users.json
    private List<Player> loadAllPlayers() {
        FileHandle userFile = Gdx.files.local("users.json");
        Gson gson = new Gson();
        if (!userFile.exists()) return new ArrayList<>();
        try {
            // اگر با JsonUtils لیست رو ذخیره کردی باید همینجا درست بخونی
            String json = userFile.readString();
            Player[] playersArray = gson.fromJson(json, Player[].class);
            List<Player> list = new ArrayList<>();
            if (playersArray != null) {
                for (Player p : playersArray) list.add(p);
            }
            return list;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    // هندلر Talent/Hints (جدید)
    public void handleTalent(Player currentPlayer) {
        Main.getMain().setScreen(new TalentMenuView(currentPlayer));
    }

    // هندلر خروج کامل
    public void handleExit() {
        System.out.println("Exiting the game...");
        Main.getMain().dispose();
        System.exit(0);
    }
}
