package io.github.some_example_name.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Controllers.CharacterSelectionController;
import io.github.some_example_name.Models.GameCharacter;

public class CharacterSelectionView implements Screen {
    private final Stage stage;
    private final Skin skin;
    private final CharacterSelectionController controller;

    private final SelectBox<String> characterSelectBox;
    private final Label hpLabel;
    private final Label speedLabel;
    private final TextButton confirmButton;
    private final TextButton backButton;

    public CharacterSelectionView(CharacterSelectionController controller, Skin skin) {
        this.controller = controller;
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        Label title = new Label("Select Your Character", skin, "title");
        characterSelectBox = new SelectBox<>(skin);
        characterSelectBox.setItems("SHANA", "DIAMOND", "SCARLET", "LILITH", "DASHER");

        hpLabel = new Label("HP: --", skin);
        speedLabel = new Label("Speed: --", skin);

        confirmButton = new TextButton("Confirm", skin);
        backButton = new TextButton("Back", skin);

        table.add(title).colspan(2).padBottom(30).row();
        table.add(new Label("Character:", skin)).padRight(10);
        table.add(characterSelectBox).row();
        table.add(hpLabel).padTop(10);
        table.add(speedLabel).padTop(10).row();
        table.add(confirmButton).padTop(20);
        table.add(backButton).padTop(20);

        // 1. ابتدا ویو رو به کنترلر معرفی کن
        controller.setView(this);

        // 2. مقداردهی اولیه اطلاعات کاراکتر (بعد از attach شدن view)
        controller.updateCharacterDetails(characterSelectBox.getSelected());

        // 3. Listener تغییر کاراکتر
        characterSelectBox.addListener(e -> {
            controller.updateCharacterDetails(characterSelectBox.getSelected());
            return false;
        });
    }

    public void setCharacterDetails(GameCharacter character) {
        hpLabel.setText("HP: " + character.getHp());
        speedLabel.setText("Speed: " + character.getSpeed());
    }

    public String getSelectedCharacter() {
        return characterSelectBox.getSelected();
    }

    public TextButton getConfirmButton() {
        return confirmButton;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    @Override public void show() {}
    @Override public void render(float delta) { stage.act(delta); stage.draw(); }
    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { stage.dispose(); }
}
