package io.github.some_example_name.Controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import io.github.some_example_name.Models.Player;

import java.util.ArrayList;
import java.util.List;

public class ProfileController {
    private final FileHandle userFile;
    private final Json json;

    public ProfileController() {
        userFile = Gdx.files.local("users.json");
        json = new Json();
    }

    // دریافت لیست تمام کاربران
    public List<Player> loadPlayers() {
        if (!userFile.exists()) return new ArrayList<>();
        try {
            return json.fromJson(ArrayList.class, Player.class, userFile);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    // ذخیره کاربران
    public void savePlayers(List<Player> players) {
        json.toJson(players, userFile);
    }

    // چک تکراری بودن نام کاربری (به جز خودش)
    public boolean isUsernameDuplicate(String newUsername, Player self) {
        List<Player> players = loadPlayers();
        for (Player p : players) {
            if (p.getUsername().equalsIgnoreCase(newUsername) && !p.getUsername().equalsIgnoreCase(self.getUsername()))
                return true;
        }
        return false;
    }

    // آپدیت نام کاربری
    public boolean changeUsername(Player player, String newUsername) {
        if (isUsernameDuplicate(newUsername, player)) return false;
        List<Player> players = loadPlayers();
        for (Player p : players) {
            if (p.getUsername().equalsIgnoreCase(player.getUsername())) {
                p.setUsername(newUsername);
                player.setUsername(newUsername); // آپدیت داخل برنامه
                break;
            }
        }
        savePlayers(players);
        return true;
    }

    // آپدیت پسورد
    public boolean changePassword(Player player, String newPassword) {
        if (!isPasswordStrong(newPassword)) return false;
        List<Player> players = loadPlayers();
        for (Player p : players) {
            if (p.getUsername().equalsIgnoreCase(player.getUsername())) {
                p.setPassword(newPassword);
                player.setPassword(newPassword);
                break;
            }
        }
        savePlayers(players);
        return true;
    }

    // حذف اکانت
    public void deleteAccount(Player player) {
        List<Player> players = loadPlayers();
        players.removeIf(p -> p.getUsername().equalsIgnoreCase(player.getUsername()));
        savePlayers(players);
    }

    // تغییر آواتار (انتخاب آدرس عکس)
    public void changeAvatar(Player player, String avatarPath) {
        List<Player> players = loadPlayers();
        for (Player p : players) {
            if (p.getUsername().equalsIgnoreCase(player.getUsername())) {
                p.setAvatarPath(avatarPath);
                player.setAvatarPath(avatarPath);
                break;
            }
        }
        savePlayers(players);
    }

    // قوی بودن پسورد (حداقل 8 کاراکتر و عدد و حرف بزرگ و علامت)
    public boolean isPasswordStrong(String password) {
        return password.length() >= 8 &&
            password.matches(".*[A-Z].*") &&
            password.matches(".*[0-9].*") &&
            password.matches(".*[@#$%&*()_+=!].*");
    }
}
