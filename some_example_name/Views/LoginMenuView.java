package io.github.some_example_name.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Controllers.LoginMenuController;

public class LoginMenuView implements Screen {
    private final LoginMenuController controller;
    private final Stage stage;
    private final Skin skin;

    private TextField usernameField;
    private TextField passwordField;
    private Label messageLabel;

    public LoginMenuView(LoginMenuController controller, Skin skin) {
        this.controller = controller;
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        controller.setView(this);
        initUI();
    }

    private void initUI() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label title = new Label("Login", skin);
        usernameField = new TextField("", skin);
        usernameField.setMessageText("Username");

        passwordField = new TextField("", skin);
        passwordField.setMessageText("Password");
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');

        TextButton loginButton = new TextButton("Login", skin);
        TextButton backButton = new TextButton("Back", skin);
        TextButton forgotButton = new TextButton("Forgot Password", skin);

        messageLabel = new Label("", skin);

        loginButton.addListener(event -> {
            controller.handleLogin(usernameField.getText(), passwordField.getText());
            return false;
        });

        forgotButton.addListener(event -> {
            controller.handleForgotPassword(usernameField.getText());
            return false;
        });

        backButton.addListener(event -> {
            controller.handleBack();
            return false;
        });

        table.add(title).colspan(2).padBottom(20).row();
        table.add(new Label("Username:", skin)).padRight(10);
        table.add(usernameField).width(200).row();
        table.add(new Label("Password:", skin)).padRight(10);
        table.add(passwordField).width(200).row();
        table.add(loginButton).colspan(2).padTop(15).row();
        table.add(forgotButton).colspan(2).padTop(5).row();
        table.add(backButton).colspan(2).padTop(5).row();
        table.add(messageLabel).colspan(2).padTop(10);
    }

    public void showError(String msg) {
        messageLabel.setText("[ERROR] " + msg);
    }

    public void showSuccess(String msg) {
        messageLabel.setText("[OK] " + msg);
    }

    // --- Screen interface ---
    @Override public void show() {}
    @Override public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }
    @Override public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        stage.dispose();
    }
}
