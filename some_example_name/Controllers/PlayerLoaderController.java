package io.github.some_example_name.Controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import io.github.some_example_name.Models.Player;

import java.util.ArrayList;
import java.util.List;
public class PlayerLoaderController {

    public static List<Player> loadPlayers() {
        FileHandle file = Gdx.files.local("users.json");

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try {
            Json json = new Json();
            return json.fromJson(ArrayList.class, Player.class, file);
        } catch (Exception e) {
            System.err.println("⚠ خطا در خواندن users.json: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void savePlayers(List<Player> players) {
        try {
            FileHandle file = Gdx.files.local("users.json");
            Json json = new Json();
            file.writeString(json.toJson(players), false);
        } catch (Exception e) {
            System.err.println("⚠ خطا در ذخیره users.json: " + e.getMessage());
        }
    }
}
