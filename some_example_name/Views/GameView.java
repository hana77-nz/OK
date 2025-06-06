package io.github.some_example_name.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import io.github.some_example_name.Controllers.GameController;
import io.github.some_example_name.Controllers.PlayerController;
import io.github.some_example_name.Controllers.WeaponController;
import io.github.some_example_name.Main;
import io.github.some_example_name.Models.Enemy;
import io.github.some_example_name.Models.GameAssetManager;
import io.github.some_example_name.Models.Player;
import io.github.some_example_name.Models.PreGame;

import com.badlogic.gdx.math.Vector2;
import java.util.List;

public class GameView implements Screen, InputProcessor {
    private Stage stage;
    private GameController controller;
    private BitmapFont font;
    private GlyphLayout layout;
    private ShapeRenderer shapeRenderer;
    private float offsetX;
    private float offsetY;

    public GameView(GameController controller) {
        this.controller = controller;
        controller.setView(this);

        Gdx.input.setInputProcessor(this);
        font = new BitmapFont();
        layout = new GlyphLayout();
        shapeRenderer = new ShapeRenderer();
    }

    public GameView(Player player, PreGame preGame) {
        this.controller = new GameController(player, preGame);
        controller.setView(this);

        Gdx.input.setInputProcessor(this);
        font = new BitmapFont();
        layout = new GlyphLayout();
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        // --- 1. آپدیت بازی ---
        controller.updateGame(delta);



        // 2. گرفتن پلیر و محاسبه offset
        Player player = controller.getPlayerController().getPlayer();
        float centerX = Gdx.graphics.getWidth() / 2f;
        float centerY = Gdx.graphics.getHeight() / 2f;
         offsetX = centerX - player.getPosX();
         offsetY = centerY - player.getPosY();

        // --- 2. رندر همه چیز ---
        Main.getBatch().begin();
        // 3. رسم بک‌گراند
        controller.getWorldController().render(offsetX, offsetY);

        // 2.3. دشمن‌ها (draw فقط اینجا)
//        for (Enemy enemy : controller.getEnemies()) {
//            enemy.render(Main.getBatch(), enemy.getX() + offsetX, enemy.getY() + offsetY);
//        }

        player.render(centerX, centerY);

        // 2.2. اسلحه و گلوله‌ها
        controller.getWeaponController().render(centerX, centerY, offsetX, offsetY);

        // --- 3. HUD ---
        String hudText = "HP: " + player.getPlayerHealth() +
            " | Kills: " + player.getKills() +
            " | Time: " + (int) player.getSurviveTime() +
            " | Ammo: " + controller.getWeaponController().getWeapon().getAmmo();

        layout.setText(font, hudText);
        font.draw(Main.getBatch(), layout, 10, Gdx.graphics.getHeight() - 10);

        // نوار XP
        float barX = 10, barY = Gdx.graphics.getHeight() - 40;
        float barWidth = 200, barHeight = 10;
        int xp = player.getXP();
        int xpMax = player.getXPToNextLevel();
        float progress = (float) xp / xpMax;

        Main.getBatch().end();

        // XP Bar (ShapeRenderer)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(barX, barY, barWidth, barHeight);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(barX, barY, barWidth * progress, barHeight);
        shapeRenderer.end();

        Main.getBatch().begin();
        font.draw(Main.getBatch(), "Level: " + player.getLevel(), barX, barY - 5);
        font.draw(Main.getBatch(), "XP: " + xp + " / " + xpMax, barX + barWidth + 10, barY + 9);
        Main.getBatch().end();

        // --- 4. UI ---
        stage.act(delta);
        stage.draw();
    }

    // --- کنترل شلیک ماوس ---
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        float worldX = screenX - offsetX;
        float worldY = Gdx.graphics.getHeight() - screenY - offsetY;
        controller.getWeaponController().shoot(worldX, worldY);
        return true;
    }


    // --- کنترل کلیدها (حرکت و شلیک و reload و auto-aim) ---
    @Override
    public boolean keyDown(int keycode) {
        PlayerController pc = controller.getPlayerController();
        switch (keycode) {
            case Input.Keys.W: pc.setMoveUp(true); break;
            case Input.Keys.S: pc.setMoveDown(true); break;
            case Input.Keys.A: pc.setMoveLeft(true); break;
            case Input.Keys.D: pc.setMoveRight(true); break;
            case Input.Keys.R:
                controller.getWeaponController().reload();
                break;
            case Input.Keys.SPACE:
                autoAimAndShoot();
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        PlayerController pc = controller.getPlayerController();
        switch (keycode) {
            case Input.Keys.W: pc.setMoveUp(false); break;
            case Input.Keys.S: pc.setMoveDown(false); break;
            case Input.Keys.A: pc.setMoveLeft(false); break;
            case Input.Keys.D: pc.setMoveRight(false); break;
        }
        return true;
    }

    // --- شلیک اتوماتیک به نزدیک‌ترین دشمن (auto-aim) ---
    private void autoAimAndShoot() {
        Player player = controller.getPlayerController().getPlayer();
        List<Enemy> enemies = controller.getEnemies();

        if (enemies.isEmpty()) return;

        Enemy nearest = null;
        float minDist = Float.MAX_VALUE;
        Vector2 playerCenter = new Vector2(
            player.getPosX() + 16, // مرکز اسپرایت (عرض 32)
            player.getPosY() + 16
        );
        for (Enemy e : enemies) {
            Vector2 enemyCenter = new Vector2(
                e.getSprite().getX() + e.getSprite().getWidth() / 2f,
                e.getSprite().getY() + e.getSprite().getHeight() / 2f
            );
            float dist = playerCenter.dst(enemyCenter);
            if (dist < minDist) {
                minDist = dist;
                nearest = e;
            }
        }
        if (nearest != null) {
            Vector2 target = new Vector2(
                nearest.getSprite().getX() + nearest.getSprite().getWidth() / 2f,
                nearest.getSprite().getY() + nearest.getSprite().getHeight() / 2f
            );
            controller.getWeaponController().shoot(target.x, Gdx.graphics.getHeight() - target.y);
        }
    }

    // --- اینپوت‌ها ---
    @Override public boolean keyTyped(char character) { return false; }
    @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchCancelled(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }
    @Override public boolean mouseMoved(int screenX, int screenY) { return false; }
    @Override public boolean scrolled(float amountX, float amountY) { return false; }

    @Override public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
        shapeRenderer.dispose();
    }
}
