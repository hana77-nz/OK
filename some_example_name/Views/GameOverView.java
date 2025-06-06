package io.github.some_example_name.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.some_example_name.Controllers.MainMenuController;
import io.github.some_example_name.Main;
import io.github.some_example_name.Models.GameAssetManager;
import io.github.some_example_name.Models.Player;

public class GameOverView implements Screen {
    private BitmapFont font;
    private GlyphLayout layout;
    private String message;
    private final Player player;

    public GameOverView(Player player, boolean isVictory) {
        this.player = player;
        font = new BitmapFont();
        layout = new GlyphLayout();

        int kills = player.getKills();
        int time = (int) player.getSurviveTime();
        int score = kills * time;

        message = (isVictory ? "VICTORY!\n" : "GAME OVER\n") +
            "Character: " + player.getUsername() + "\n" +
            "Kills: " + kills + "\n" +
            "Time: " + time + " seconds\n" +
            "Score: " + score + "\n\n" +
            "Press ESC to return to main menu.";
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        layout.setText(font, message);
        font.draw(Main.getBatch(), layout, 100, Gdx.graphics.getHeight() - 100);
        Main.getBatch().end();

        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            Main.getMain().setScreen(
                new MainMenuView(
                    new MainMenuController(player),
                    GameAssetManager.getSkin(),
                    player
                )
            );
        }
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        font.dispose();
    }
}
