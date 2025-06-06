package io.github.some_example_name.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Controllers.MainMenuController;
import io.github.some_example_name.Main;
import io.github.some_example_name.Models.Player;

public class MainMenuView implements Screen {
    private final Stage stage;
    private final Skin skin;
    private final MainMenuController controller;
    private final Player currentPlayer;

    private Label messageLabel;

    public  MainMenuView(MainMenuController controller, Skin skin, Player currentPlayer) {
        this.controller = controller;
        this.skin = skin;
        this.currentPlayer = currentPlayer;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        buildUI();
    }

    private void buildUI() {
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        // Title
        Label titleLabel = new Label("20 Minutes Till Dawn", skin, "title");
        table.add(titleLabel).colspan(2).padBottom(40);
        table.row();

        // آواتار کاربر
        if (currentPlayer != null && currentPlayer.getAvatarPath() != null) {
            Image avatar = new Image(new com.badlogic.gdx.graphics.Texture(Gdx.files.internal(currentPlayer.getAvatarPath())));
            avatar.setSize(64, 64);
            table.add(avatar).colspan(2).padBottom(5);
            table.row();
        }

        // Username
        String displayName = currentPlayer != null ? currentPlayer.getUsername() : "Guest";
        Label playerLabel = new Label("Player: " + displayName, skin);
        table.add(playerLabel).colspan(2).padBottom(10);
        table.row();

        // Score نمایش امتیاز
        if (currentPlayer != null) {
            Label scoreLabel = new Label("Score: " + currentPlayer.getScore(), skin);
            table.add(scoreLabel).colspan(2).padBottom(10);
            table.row();
        }

        // دکمه Continue
        TextButton continueBtn = new TextButton("Continue", skin);
        continueBtn.addListener(e -> {
            controller.handleLoadGame(currentPlayer);
            return true;
        });
        table.add(continueBtn).width(200).padBottom(10);
        table.row();

        // دکمه New Game
        TextButton newGameBtn = new TextButton("New Game", skin);
        newGameBtn.addListener(e -> {
            controller.handleNewGame(currentPlayer);
            return true;
        });
        table.add(newGameBtn).width(200).padBottom(10);
        table.row();

        // دکمه Load Game
        TextButton loadGameBtn = new TextButton("Load Game", skin);
        loadGameBtn.addListener(e -> {
            controller.handleLoadGame(currentPlayer);
            return true;
        });
        table.add(loadGameBtn).width(200).padBottom(10);
        table.row();

        // دکمه Settings
        TextButton settingsBtn = new TextButton("Settings", skin);
        settingsBtn.addListener(e -> {
            controller.handleSettings();
            return true;
        });
        table.add(settingsBtn).width(200).padBottom(10);
        table.row();

        // دکمه Profile
        TextButton profileButton = new TextButton("Profile", skin);
        profileButton.addListener(e -> {
            if (currentPlayer != null && !currentPlayer.getUsername().startsWith("Guest")) {
                controller.handleProfile(currentPlayer);
            } else {
                messageLabel.setText("[ERROR] Profile is only available for registered players.");
                messageLabel.setColor(com.badlogic.gdx.graphics.Color.RED);
            }
            return false;
        });
        table.add(profileButton).width(200).padBottom(10);
        table.row();

        // دکمه Scoreboard (جدید)
        TextButton scoreboardBtn = new TextButton("Scoreboard", skin);
        scoreboardBtn.addListener(e -> {
            controller.handleScoreboard(currentPlayer);
            return true;
        });
        table.add(scoreboardBtn).width(200).padBottom(10);
        table.row();

        // دکمه Talent/Hints (جدید)
        TextButton talentBtn = new TextButton("Talent/Hints", skin);
        talentBtn.addListener(e -> {
            controller.handleTalent(currentPlayer);
            return true;
        });
        table.add(talentBtn).width(200).padBottom(10);
        table.row();

        // دکمه Credits
        TextButton creditsBtn = new TextButton("Credits", skin);
        creditsBtn.addListener(e -> {
            controller.handleCredits();
            return true;
        });
        table.add(creditsBtn).width(200).padBottom(10);
        table.row();

        // دکمه Exit
        TextButton exitBtn = new TextButton("Exit", skin);
        exitBtn.addListener(e -> {
            controller.handleExit();
            return true;
        });
        table.add(exitBtn).width(200).padBottom(10);
        table.row();

        // پیام‌ها
        messageLabel = new Label("", skin);
        messageLabel.setAlignment(Align.center);
        table.add(messageLabel).colspan(2).padTop(15).width(300);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }
}
