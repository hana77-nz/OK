package io.github.some_example_name.Views;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Controllers.LoginMenuController;
import io.github.some_example_name.Controllers.RegisterMenuController;
import io.github.some_example_name.Main;

public class RegisterMenuView implements Screen {
    private final Game game;
    private final Stage stage;
    private final Skin skin;
    private final RegisterMenuController controller;
    private Label messageLabel;
    private Texture backgroundTexture;
    private Image backgroundImage;


    private TextField usernameField;
    private TextField passwordField;
    private SelectBox<String> securityQuestionBox;
    private TextField securityAnswerField;

    public RegisterMenuView(Game game, RegisterMenuController controller, Skin skin) {
        this.game = game;
        this.controller = controller;
        this.skin = skin;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        controller.setView(this);

        Table table = new Table();
        table.setFillParent(true);
        backgroundTexture = new Texture("assets/Images/Backgrounds/Menus.png");
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(backgroundImage); // اول بک‌گراند

        stage.addActor(table);

        Label titleLabel = new Label("Register", skin, "title");
        titleLabel.setColor(Color.WHITE);

        usernameField = new TextField("", skin);
        usernameField.setMessageText("Username");

        passwordField = new TextField("", skin);
        passwordField.setMessageText("Password");
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');

        securityQuestionBox = new SelectBox<>(skin);
        securityQuestionBox.setItems(
            "What is your pet’s name?",
            "What is your mother’s maiden name?",
            "What is your favorite color?"
        );
        securityQuestionBox.setSelectedIndex(0);

        securityAnswerField = new TextField("", skin);
        securityAnswerField.setMessageText("Answer");

        TextButton registerButton = new TextButton("Register", skin);
        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleRegister(
                    usernameField.getText(),
                    passwordField.getText(),
                    securityQuestionBox.getSelected(),
                    securityAnswerField.getText()
                );
            }
        });

        TextButton skipButton = new TextButton("Continue as Guest", skin);
        skipButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleGuestLogin();
            }
        });

        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleBack();
            }
        });

        TextButton loginButton = new TextButton("Already Registered? Login", skin);
        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LoginMenuView(new LoginMenuController(Main.getMain(), new RegisterMenuController()), skin));
            }
        });

        messageLabel = new Label("", skin);
        messageLabel.setColor(Color.RED);

        table.add(titleLabel).colspan(2).padBottom(20);
        table.row();
        table.add(new Label("Username:", skin)).padRight(10);
        table.add(usernameField).width(200);
        table.row();
        table.add(new Label("Password:", skin)).padRight(10);
        table.add(passwordField).width(200);
        table.row();
        table.add(new Label("Security Question:", skin)).padRight(10);
        table.add(securityQuestionBox).width(200);
        table.row();
        table.add(new Label("Answer:", skin)).padRight(10);
        table.add(securityAnswerField).width(200);
        table.row();
        table.add(registerButton).colspan(2).padTop(20);
        table.row();
        table.add(skipButton).colspan(2).padTop(10);
        table.row();
        table.add(backButton).colspan(2).padTop(10);
        table.row();
        table.add(messageLabel).colspan(2).padTop(10);
        table.add(loginButton).colspan(2).padTop(10);

    }

    public void showError(String message) {
        messageLabel.setColor(Color.RED);
        messageLabel.setText(message);
    }

    public void showSuccess(String message) {
        messageLabel.setColor(Color.GREEN);
        messageLabel.setText(message);
    }

    @Override public void show() {}
    @Override public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
        skin.dispose();
        backgroundTexture.dispose();

    }
}
