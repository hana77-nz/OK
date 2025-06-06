package io.github.some_example_name.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Main;
import io.github.some_example_name.Models.GameAssetManager;
import io.github.some_example_name.Models.Player;
import io.github.some_example_name.Views.MainMenuView;
import io.github.some_example_name.Controllers.MainMenuController;

public class CreditsView implements Screen {
    private final Stage stage;
    private final Skin skin;
    private final Player currentPlayer;

    public CreditsView(Skin skin, Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        this.skin = GameAssetManager.getSkin();
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        Label title = new Label("Credits", skin, "title");
        Label credits = new Label(
            "Developed by:\n- Your Name\n\nAssets from:\n- Kenney.nl\n- OpenGameArt.org\n\nThank you for playing!",
            skin
        );
        credits.setWrap(true);
        credits.setAlignment(Align.center);

        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(event -> {
            Main.getMain().setScreen(new MainMenuView(new MainMenuController(currentPlayer), skin, null));
            return true;
        });

        table.add(title).padBottom(30);
        table.row();
        table.add(credits).width(400).padBottom(30);
        table.row();
        table.add(backButton).width(200);
    }

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
