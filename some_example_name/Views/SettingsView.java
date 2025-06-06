package io.github.some_example_name.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Main;
import io.github.some_example_name.Controllers.MainMenuController;
import io.github.some_example_name.Models.GameAssetManager;
import io.github.some_example_name.Models.SettingsManager;

public class SettingsView implements Screen {
    private final Stage stage;
    private final Skin skin;

    public SettingsView(Skin skin) {
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        SettingsManager.loadSettings();

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        Label titleLabel = new Label("Settings", skin, "title");
        table.add(titleLabel).colspan(2).padBottom(30);
        table.row();

        // Volume
        final Label volumeLabel = new Label("Volume: " + (int)(SettingsManager.getMusicVolume() * 100) + "%", skin);
        final Slider volumeSlider = new Slider(0f, 1f, 0.01f, false, skin);
        volumeSlider.setValue(SettingsManager.getMusicVolume());
        volumeSlider.addListener(e -> {
            float vol = volumeSlider.getValue();
            SettingsManager.setMusicVolume(vol);
            GameAssetManager.setMusicVolume(vol);
            volumeLabel.setText("Volume: " + (int)(vol * 100) + "%");
            return false;
        });
        table.add(volumeLabel).colspan(2).padBottom(10).row();
        table.add(volumeSlider).width(250).colspan(2).padBottom(20).row();

        // Music Select
        Label musicLabel = new Label("Background Music:", skin);
        final SelectBox<String> musicSelectBox = new SelectBox<>(skin);
        musicSelectBox.setItems("Default", "Action Theme", "Calm Theme");
        musicSelectBox.setSelected(SettingsManager.getMusicTrack());
        musicSelectBox.addListener(e -> {
            String selected = musicSelectBox.getSelected();
            SettingsManager.setMusicTrack(selected);
            GameAssetManager.setMusic(selected);
            return false;
        });
        table.add(musicLabel).left().padBottom(10);
        table.add(musicSelectBox).width(200).row();

        // SFX Checkbox
        final CheckBox sfxCheckbox = new CheckBox(" Enable Sound Effects (SFX)", skin);
        sfxCheckbox.setChecked(SettingsManager.isSfxEnabled());
        sfxCheckbox.addListener(e -> {
            boolean enabled = sfxCheckbox.isChecked();
            SettingsManager.setSfxEnabled(enabled);
            // هر جا SFX پخش می‌کنی باید از این مقدار چک کنی
            return false;
        });
        table.add(sfxCheckbox).colspan(2).padBottom(10).row();

        // Controls
        Label controlLabel = new Label("Controls:", skin);
        final SelectBox<String> controlSelectBox = new SelectBox<>(skin);
        controlSelectBox.setItems("WASD", "Arrow Keys");
        controlSelectBox.setSelected(SettingsManager.getControlType());
        controlSelectBox.addListener(e -> {
            SettingsManager.setControlType(controlSelectBox.getSelected());
            // در کنترلر باید از مقدار SettingsManager استفاده کنی
            return false;
        });
        table.add(controlLabel).left().padBottom(10);
        table.add(controlSelectBox).width(200).row();

        // Auto Reload
        final CheckBox autoReloadCheckbox = new CheckBox(" Enable Auto Reload", skin);
        autoReloadCheckbox.setChecked(SettingsManager.isAutoReload());
        autoReloadCheckbox.addListener(e -> {
            SettingsManager.setAutoReload(autoReloadCheckbox.isChecked());
            // در WeaponController از مقدار SettingsManager چک کن
            return false;
        });
        table.add(autoReloadCheckbox).colspan(2).padBottom(10).row();

        // Grayscale
        final CheckBox grayscaleCheckbox = new CheckBox(" Enable Grayscale Mode", skin);
        grayscaleCheckbox.setChecked(SettingsManager.isGrayscale());
        grayscaleCheckbox.addListener(e -> {
            SettingsManager.setGrayscale(grayscaleCheckbox.isChecked());
            // موقع رندر Main یا Stage یا GameView چک کن اگه فعال بود Shader بزنی
            return false;
        });
        table.add(grayscaleCheckbox).colspan(2).padBottom(20).row();

        // Back Button
        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(e -> {
            Main.getMain().setScreen(new MainMenuView(new MainMenuController(SettingsManager.isSfxEnabled() ? null : null), skin, null));
            return false;
        });
        table.add(backButton).colspan(2).padTop(10);
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
    }
}
