package io.github.some_example_name.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Main;
import io.github.some_example_name.Models.GameAssetManager;
import io.github.some_example_name.Controllers.MainMenuController;
import io.github.some_example_name.Models.Player;

public class TalentMenuView implements Screen {
    private final Stage stage;
    private final Skin skin;
    private final Player currentPlayer;

    public TalentMenuView(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        this.skin = GameAssetManager.getSkin();
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        buildUI();
    }

    private void buildUI() {
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        table.add(new Label("Talent (Hint) Menu", skin, "title")).colspan(2).padBottom(20).row();

        // ۱. راهنما درباره ۳ هیرو
        table.add(new Label("Hero Tips:", skin, "subtitle")).colspan(2).padBottom(10).row();
        table.add(new Label("SHANA: Faster reload, unique starter weapon.", skin)).colspan(2).left().row();
        table.add(new Label("DIAMOND: Double health, slow speed.", skin)).colspan(2).left().row();
        table.add(new Label("LILITH: Can summon bats, heals per kill.", skin)).colspan(2).left().row();

        // ۲. کلیدهای بازی
        table.add(new Label("Game Keys:", skin, "subtitle")).colspan(2).padTop(15).padBottom(5).row();
        table.add(new Label("Move: W/A/S/D  |  Shoot: Mouse  |  Reload: R  |  Ability: Space", skin)).colspan(2).left().row();

        // ۳. کلیدهایی که تغییر داده شده (فرض کن همیشه WASD، اگر داری نشون بده)
        // میشه یک Label خاص یا اگر Key Mapping داری Table دینامیک
        table.add(new Label("Current Controls: (Default or Changed)", skin)).colspan(2).left().row();

        // ۴. کد تقلب
        table.add(new Label("Cheat Codes:", skin, "subtitle")).colspan(2).padTop(15).row();
        table.add(new Label("GODMODE: Infinite health\nMONEY: +10000 coins\nLEVELUP: Instant level up", skin)).colspan(2).left().row();

        // ۵. کارایی ability ها
        table.add(new Label("Abilities:", skin, "subtitle")).colspan(2).padTop(15).row();
        table.add(new Label("Dash: Quick invincible movement\nBomb: Area damage\nHeal: Restore health instantly", skin)).colspan(2).left().row();

        // دکمه بازگشت
        TextButton backBtn = new TextButton("Back", skin);
        backBtn.addListener(e -> {
            Main.getMain().setScreen(new MainMenuView(new MainMenuController(currentPlayer), skin, currentPlayer));
            return true;
        });
        table.add(backBtn).padTop(30);
    }


    @Override public void show() {}
    @Override public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }
    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true);}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { stage.dispose(); }
}
