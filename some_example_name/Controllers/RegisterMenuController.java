package io.github.some_example_name.Controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import io.github.some_example_name.Models.Player;
import io.github.some_example_name.Views.RegisterMenuView;
import io.github.some_example_name.Views.LoginMenuView;
import io.github.some_example_name.Views.SecurityQuestionView;
import io.github.some_example_name.Main;
import io.github.some_example_name.Models.GameAssetManager;
import io.github.some_example_name.Views.MainMenuView;

import java.util.*;

public class RegisterMenuController {
    private RegisterMenuView registerView;
    private LoginMenuView loginView;
    private final FileHandle userFile;
    private final Json json;

    private final String[] avatarPaths = {
        "assets/Images/avatar/avatar1.png",
        "assets/Images/avatar/avatar2.png",
        "assets/Images/avatar/avatar3.png",
        "assets/Images/avatar/avatar4.png"
    };

    public RegisterMenuController() {
        this.userFile = Gdx.files.local("users.json");   //???/
        this.json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
    }

    public void setView(RegisterMenuView view) {
        this.registerView = view;
    }

    public void setLoginView(LoginMenuView view) {
        this.loginView = view;
    }

    public void handleRegister(String username, String password, String selectedQuestion, String answer) {
        if (username.isEmpty() || password.isEmpty() || selectedQuestion.isEmpty() || answer.isEmpty()) {
            registerView.showError("All fields are required.");
            return;
        }

        if (!isPasswordStrong(password)) {
            registerView.showError("Password must be at least 8 characters, include a number, an uppercase letter, and a special character.");
            return;
        }

        List<Player> players = loadPlayers();

        if (isUsernameDuplicate(players, username)) {
            registerView.showError("This username already exists.");
            return;
        }

        String avatarPath = getRandomAvatarPath();
        Player newPlayer = new Player(username, password);
        newPlayer.setAvatarPath(avatarPath);
        newPlayer.setSecurityQuestion(selectedQuestion);
        newPlayer.setSecurityAnswer(answer);

        players.add(newPlayer);
        savePlayers(players);

        registerView.showSuccess("Registration successful!");
        Main.getMain().setScreen(new MainMenuView(new MainMenuController(newPlayer), GameAssetManager.getSkin(), newPlayer));
    }

    public void handleGuestLogin() {
        String guestName = "Guest_" + UUID.randomUUID().toString().substring(0, 6);
        Player guestPlayer = new Player(guestName, "");
        guestPlayer.setAvatarPath("Images/avatar/avatar5.png");

        Main.getMain().setScreen(new MainMenuView(new MainMenuController(guestPlayer), GameAssetManager.getSkin(), guestPlayer));
    }

    public void resetPassword(Player player, String answer, String newPassword) {
        if (!player.getSecurityAnswer().equalsIgnoreCase(answer)) {
            registerView.showError("Incorrect security answer.");
            return;
        }

        if (!isPasswordStrong(newPassword)) {
            registerView.showError("New password is too weak.");
            return;
        }

        List<Player> players = loadPlayers();
        for (Player p : players) {
            if (p.getUsername().equalsIgnoreCase(player.getUsername())) {
                p.setPassword(newPassword);
                break;
            }
        }

        savePlayers(players);
        Main.getMain().setScreen(new MainMenuView(new MainMenuController(player), GameAssetManager.getSkin(), player));
    }

    public void handleBack() {
        Main.getMain().setScreen(new MainMenuView(new MainMenuController(null), GameAssetManager.getSkin(), null));
    }

    /** Password must be at least 8 chars, have uppercase, number, special char */
    private boolean isPasswordStrong(String password) {
        return password.length() >= 8 &&
            password.matches(".*[A-Z].*") &&
            password.matches(".*[0-9].*") &&
            password.matches(".*[!@#$%^&*()_+\\-={}\\[\\]:;\"'<>,.?/\\\\|].*");
    }

    /** Checks for username duplication (case-insensitive) */
    private boolean isUsernameDuplicate(List<Player> players, String username) {
        for (Player player : players) {
            if (player.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    private List<Player> loadPlayers() {
        if (!userFile.exists()) return new ArrayList<>();
        try {
            return json.fromJson(ArrayList.class, Player.class, userFile);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void savePlayers(List<Player> players) {
        json.toJson(players, userFile);
    }

    /** Picks a random avatar path from list */
    private String getRandomAvatarPath() {
        int index = (int) (Math.random() * avatarPaths.length);
        return avatarPaths[index];
    }
}
