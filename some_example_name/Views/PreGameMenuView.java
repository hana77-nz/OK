package io.github.some_example_name.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Controllers.PreGameMenuController;
import io.github.some_example_name.Models.Player;

public class PreGameMenuView implements Screen {

    private final Stage stage;
    private final PreGameMenuController controller;
    private final Skin skin;
    private final Player player;

    private final SelectBox<String> heroSelectBox;
    private final SelectBox<String> weaponSelectBox;
    private final SelectBox<String> durationSelectBox;
    private final TextButton playButton;
    private final TextButton backButton;

    private final Label usernameLabel;
    private final Image avatarImage;

    public PreGameMenuView(PreGameMenuController controller, Skin skin, Player player) {
        this.controller = controller;
        this.skin = skin;
        this.player = player;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        Label title = new Label("Pre-Game Setup", skin, "title");

        usernameLabel = new Label("Username: " + player.getUsername(), skin);
        avatarImage = new Image();

        if (player.getAvatarPath() != null && !player.getAvatarPath().isEmpty()) {
            Texture avatarTexture = new Texture(Gdx.files.internal(player.getAvatarPath()));
            avatarImage.setDrawable(new Image(avatarTexture).getDrawable());
            avatarImage.setSize(64, 64);
        }

        heroSelectBox = new SelectBox<>(skin);
        heroSelectBox.setItems("SHANA", "DIAMOND", "SCARLET", "LILITH", "DASHER");

        weaponSelectBox = new SelectBox<>(skin);
        weaponSelectBox.setItems("Revolver", "Shotgun", "Dual SMGs");

        durationSelectBox = new SelectBox<>(skin);
        durationSelectBox.setItems("2.5", "5", "10", "20");

        playButton = new TextButton("Start Game", skin);
        backButton = new TextButton("Back", skin);

        table.add(title).colspan(2).padBottom(20).row();
        table.add(usernameLabel).colspan(2).padBottom(10).row();
        table.add(avatarImage).colspan(2).padBottom(20).row();
        table.add(new Label("Select Hero:", skin)).left().padRight(10);
        table.add(heroSelectBox).left().row();
        table.add(new Label("Select Weapon:", skin)).left().padRight(10);
        table.add(weaponSelectBox).left().row();
        table.add(new Label("Duration (minutes):", skin)).left().padRight(10);
        table.add(durationSelectBox).left().row();
        table.add(playButton).padTop(20).padRight(20);
        table.add(backButton).padTop(20).left();

        // ✅ مهم: فقط بعد از مقداردهی همه چیز، ویو رو به کنترلر بده!
        controller.setView(this);
    }

    public void updatePlayerInfo(Player player) {
        usernameLabel.setText("Username: " + player.getUsername());
    }

    public String getSelectedHero() {
        return heroSelectBox.getSelected();
    }

    public String getSelectedWeapon() {
        return weaponSelectBox.getSelected();
    }

    public float getSelectedDuration() {
            return Float.parseFloat(durationSelectBox.getSelected());
        }


    public TextButton getPlayButton() {
        return playButton;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    @Override public void show() {}
    @Override public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
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
