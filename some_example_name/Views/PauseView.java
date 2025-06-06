package io.github.some_example_name.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.some_example_name.Controllers.MainMenuController;
import io.github.some_example_name.Main;
import io.github.some_example_name.Models.Player;
import io.github.some_example_name.Models.Ability;
import java.util.List;

public class PauseView implements Screen {
    private final Screen previousScreen;
    private final BitmapFont font;
    private final GlyphLayout layout;
    private final Player currentPlayer;
    private final Skin skin;

    public PauseView(Screen previousScreen, Player currentPlayer, Skin skin) {
        this.previousScreen = previousScreen;
        this.font = new BitmapFont();
        this.layout = new GlyphLayout();
        this.currentPlayer = currentPlayer;
        this.skin = skin;
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1);

        Main.getBatch().begin();

        // Title
        layout.setText(font, "GAME PAUSED\n\nPress R to Resume\nPress G to Give Up");
        font.draw(Main.getBatch(), layout, 100, Gdx.graphics.getHeight() - 100);

        // Cheat codes
        layout.setText(font, "Cheat Codes:\nGODMODE: Invincibility\nMONEY: Infinite coins\nLEVELUP: Instant level up");
        font.draw(Main.getBatch(), layout, 100, Gdx.graphics.getHeight() - 250);

        // پویا: نمایش abilityهای به دست آمده
        List<Ability> unlocked = currentPlayer.getUnlockedAbilities(); // فرض بر این که همچین متدی داری
        StringBuilder abilitiesText = new StringBuilder("Abilities Unlocked:\n");
        if (unlocked != null && !unlocked.isEmpty()) {
            for (Ability ab : unlocked) {
                abilitiesText.append(ab.getName())
                    .append(": ")
                    .append(ab.getDescription())
                    .append("\n");
            }
        } else {
            abilitiesText.append("None\n");
        }
        layout.setText(font, abilitiesText.toString());
        font.draw(Main.getBatch(), layout, 100, Gdx.graphics.getHeight() - 400);

        // پویا: نمایش ability فعلی انتخاب شده
        Ability current = currentPlayer.getCurrentAbility(); // فرض بر این که همچین متدی داری
        String currentAb = "Current Ability:\n";
        if (current != null)
            currentAb += current.getName() + " (" + current.getDescription() + ")";
        else
            currentAb += "None";
        layout.setText(font, currentAb);
        font.draw(Main.getBatch(), layout, 100, Gdx.graphics.getHeight() - 500);

        Main.getBatch().end();

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.R)) {
            Main.instance.setScreen(previousScreen); // Resume game
        }
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.G)) {
            MainMenuController controller = new MainMenuController(currentPlayer);
            Main.instance.setScreen(new io.github.some_example_name.Views.MainMenuView(controller, skin, currentPlayer));
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
