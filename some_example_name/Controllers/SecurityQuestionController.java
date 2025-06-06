package io.github.some_example_name.Controllers;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import io.github.some_example_name.Main;
import io.github.some_example_name.Models.Player;
import io.github.some_example_name.Views.SecurityQuestionView;
import io.github.some_example_name.Views.LoginMenuView;
import io.github.some_example_name.Models.GameAssetManager;

import java.util.List;

public class SecurityQuestionController implements Screen {
    private final SecurityQuestionView view;
    private final List<Player> allPlayers;
    private final Json json;

    public SecurityQuestionController(Main game, List<Player> allPlayers, Skin skin) {
        this.allPlayers = allPlayers;
        this.view = new SecurityQuestionView(skin);
        this.json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        setupListeners(game);
    }

    private void setupListeners(Main game) {
        view.getSubmitButton().addListener(event -> {
            String username = view.getUsernameField().getText().trim();
            String answer = view.getAnswerField().getText().trim();
            String newPass = view.getNewPasswordField().getText().trim();

            Player player = findPlayerByUsername(username);
            if (player == null) {
                view.showError("User not found.");
                return false;
            }

            if (!player.getSecurityAnswer().equalsIgnoreCase(answer)) {
                view.showError("Incorrect security answer.");
                return false;
            }

            if (!isPasswordStrong(newPass)) {
                view.showError("Password must be stronger (8+ chars, capital, number, symbol)");
                return false;
            }

            player.setPassword(newPass);
            savePlayers();
            view.showSuccess("Password changed successfully!");
            return false;
        });

        view.getBackButton().addListener(event -> {
            game.setScreen(new LoginMenuView(new LoginMenuController(game, new RegisterMenuController()), GameAssetManager.getSkin()));
            return false;
        });
    }

    private Player findPlayerByUsername(String username) {
        for (Player p : allPlayers) {
            if (p.getUsername().equalsIgnoreCase(username)) return p;
        }
        return null;
    }

    private boolean isPasswordStrong(String password) {
        return password.length() >= 8 &&
            password.matches(".*[A-Z].*") &&
            password.matches(".*[0-9].*") &&
            password.matches(".*[@#$%&*()_+=!].*");
    }

    private void savePlayers() {
        com.badlogic.gdx.files.FileHandle file = com.badlogic.gdx.Gdx.files.local("users.json");
        json.toJson(allPlayers, file);
    }

    @Override public void show() {}
    @Override public void render(float delta) { view.render(delta); }
    @Override public void resize(int width, int height) { view.resize(width, height); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { view.dispose(); }
}
