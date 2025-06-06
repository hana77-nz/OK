package io.github.some_example_name.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Models.Player;
import io.github.some_example_name.Main;
import io.github.some_example_name.Models.GameAssetManager;
import io.github.some_example_name.Controllers.MainMenuController;

import java.util.*;
import java.util.List;

public class ScoreboardView implements Screen {
    private final Stage stage;
    private final Skin skin;
    private List<Player> players;
    private final Player currentPlayer;
    private String sortBy = "score"; // پیش‌فرض

    public ScoreboardView(List<Player> players, Player currentPlayer) {
        this.skin = GameAssetManager.getSkin();
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        this.players = new ArrayList<>(players);
        this.currentPlayer = currentPlayer;

        buildUI();
    }

    private void buildUI() {
        stage.clear();
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        // Title
        table.add(new Label("Scoreboard", skin, "title")).colspan(5).padBottom(30);
        table.row();

        // Sort Buttons
        TextButton sortScore = new TextButton("Sort by Score", skin);
        TextButton sortUsername = new TextButton("Sort by Username", skin);
        TextButton sortKills = new TextButton("Sort by Kills", skin);
        TextButton sortSurvive = new TextButton("Sort by Time", skin);

        sortScore.addListener(e -> { sortBy = "score"; buildUI(); return true; });
        sortUsername.addListener(e -> { sortBy = "username"; buildUI(); return true; });
        sortKills.addListener(e -> { sortBy = "kills"; buildUI(); return true; });
        sortSurvive.addListener(e -> { sortBy = "survive"; buildUI(); return true; });

        table.add(sortScore).pad(5);
        table.add(sortUsername).pad(5);
        table.add(sortKills).pad(5);
        table.add(sortSurvive).pad(5);
        table.row();

        // Headers
        table.add(new Label("Rank", skin)).pad(10);
        table.add(new Label("Username", skin)).pad(10);
        table.add(new Label("Kills", skin)).pad(10);
        table.add(new Label("Score", skin)).pad(10);
        table.add(new Label("Time", skin)).pad(10);
        table.row();

        // مرتب‌سازی
        players.sort((a, b) -> {
            switch (sortBy) {
                case "username": return a.getUsername().compareToIgnoreCase(b.getUsername());
                case "kills": return Integer.compare(b.getKills(), a.getKills());
                case "survive": return Float.compare(b.getSurviveTime(), a.getSurviveTime());
                default: return Integer.compare(b.getScore(), a.getScore());
            }
        });

        // فقط ۱۰ نفر برتر
        int max = Math.min(10, players.size());
        for (int i = 0; i < max; i++) {
            Player p = players.get(i);

            Label rankLabel = new Label("" + (i + 1), skin);
            Label userLabel = new Label(p.getUsername(), skin);
            Label killsLabel = new Label("" + p.getKills(), skin);
            Label scoreLabel = new Label("" + p.getScore(), skin);
            Label timeLabel = new Label(String.format("%.0f", p.getSurviveTime()), skin);

            // جلوه برای ۳ نفر برتر
            if (i == 0) {
                rankLabel.setColor(Color.GOLD);
                userLabel.setColor(Color.GOLD);
                scoreLabel.setColor(Color.GOLD);
                killsLabel.setColor(Color.GOLD);
                timeLabel.setColor(Color.GOLD);
            } else if (i == 1) {
                Color silver = Color.valueOf("#C0C0C0"); // Silver: R=192, G=192, B=192
                rankLabel.setColor(silver);
                userLabel.setColor(silver);
                scoreLabel.setColor(silver);
                killsLabel.setColor(silver);
                timeLabel.setColor(silver);
            } else if (i == 2) {
                rankLabel.setColor(Color.valueOf("#CD7F32")); // Bronze
                userLabel.setColor(Color.valueOf("#CD7F32"));
                scoreLabel.setColor(Color.valueOf("#CD7F32"));
                killsLabel.setColor(Color.valueOf("#CD7F32"));
                timeLabel.setColor(Color.valueOf("#CD7F32"));
            }

            // جلوه برای کاربر لاگین شده
            if (currentPlayer != null && p.getUsername().equals(currentPlayer.getUsername())) {
                userLabel.setColor(Color.LIME);
                scoreLabel.setColor(Color.LIME);
                killsLabel.setColor(Color.LIME);
                timeLabel.setColor(Color.LIME);
            }

            table.add(rankLabel).pad(3);
            table.add(userLabel).pad(3);
            table.add(killsLabel).pad(3);
            table.add(scoreLabel).pad(3);
            table.add(timeLabel).pad(3);
            table.row();
        }

        TextButton backBtn = new TextButton("Back", skin);
        backBtn.addListener(e -> {
            Main.getMain().setScreen(new MainMenuView(new MainMenuController(currentPlayer), skin, currentPlayer));
            return true;
        });
        table.add(backBtn).colspan(5).padTop(30);
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
