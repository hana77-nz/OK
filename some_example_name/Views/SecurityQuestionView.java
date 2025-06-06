package io.github.some_example_name.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SecurityQuestionView implements Screen {
    private final Skin skin;
    private final Stage stage;

    private final TextField usernameField;
    private final TextField answerField;
    private final TextField newPasswordField;
    private final Label messageLabel;
    private final TextButton submitButton;
    private final TextButton backButton;

    public SecurityQuestionView(Skin skin) {
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.center();

        usernameField = new TextField("", skin);
        answerField = new TextField("", skin);
        newPasswordField = new TextField("", skin);
        newPasswordField.setPasswordCharacter('*');
        newPasswordField.setPasswordMode(true);

        submitButton = new TextButton("Submit", skin);
        backButton = new TextButton("Back", skin);
        messageLabel = new Label("", skin);

        table.add(new Label("Username:", skin)).left();
        table.row();
        table.add(usernameField).width(300);
        table.row().padTop(10);
        table.add(new Label("Security Answer:", skin)).left();
        table.row();
        table.add(answerField).width(300);
        table.row().padTop(10);
        table.add(new Label("New Password:", skin)).left();
        table.row();
        table.add(newPasswordField).width(300);
        table.row().padTop(20);
        table.add(submitButton).width(120).padRight(10);
        table.row();
        table.add(backButton).width(120).padTop(5);
        table.row().padTop(10);
        table.add(messageLabel).colspan(2);

        stage.addActor(table);
    }

    public TextField getUsernameField() { return usernameField; }
    public TextField getAnswerField() { return answerField; }
    public TextField getNewPasswordField() { return newPasswordField; }
    public TextButton getSubmitButton() { return submitButton; }
    public TextButton getBackButton() { return backButton; }

    public void showError(String message) {
        messageLabel.setText("[RED]" + message);
    }

    public void showSuccess(String message) {
        messageLabel.setText("[GREEN]" + message);
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
