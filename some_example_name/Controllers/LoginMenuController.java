package io.github.some_example_name.Controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import io.github.some_example_name.Models.Player;
import io.github.some_example_name.Views.LoginMenuView;
import io.github.some_example_name.Main;
import io.github.some_example_name.Models.GameAssetManager;
import io.github.some_example_name.Views.MainMenuView;
import io.github.some_example_name.Views.SecurityQuestionView;

import java.util.List;

public class LoginMenuController {
    private LoginMenuView view;
    private final FileHandle userFile;
    private final Json json;
    private final RegisterMenuController registerController;
    private final Main game;

    public LoginMenuController(Main game, RegisterMenuController registerController) {
        this.game = game;
        this.registerController = registerController;
        this.userFile = Gdx.files.local("users.json"); //؟؟؟؟؟؟؟؟؟
        this.json = new Json();
    }

    public void setView(LoginMenuView view) {
        this.view = view;
    }

    public void handleLogin(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            view.showError("Username and password are required.");
            return;
        }

        List<Player> players = PlayerLoaderController.loadPlayers();
        for (Player player : players) {
            if (player.getUsername().equalsIgnoreCase(username)) {
                if (player.getPassword().equals(password)) {
                    System.out.println("Login successful for " + username);
                    Player loggedInPlayer = player;
                    Main.getMain().setScreen(new MainMenuView(new MainMenuController(loggedInPlayer), GameAssetManager.getSkin(), loggedInPlayer));

                    return;
                } else {
                    view.showError("Incorrect password.");
                    return;
                }
            }
        }

        view.showError("User not found.");
    }

    public void handleForgotPassword(String username) {
        if (username.isEmpty()) {
            view.showError("Enter username to recover password.");
            return;
        }

        // در صورت نیاز می‌تونی از username هم استفاده کنی برای فیلتر کردن
        List<Player> players = PlayerLoaderController.loadPlayers();
        game.setScreen(new SecurityQuestionController(game, players, GameAssetManager.getSkin()));
    }

    public void handleBack() {
        // مثلاً برگشت به صفحه ثبت‌نام یا منوی اصلی
        Main.getMain().setScreen(new MainMenuView(new MainMenuController(null), GameAssetManager.getSkin(), null));
    }
}
