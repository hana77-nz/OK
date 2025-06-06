package io.github.some_example_name.Controllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.some_example_name.Main;
import io.github.some_example_name.Models.GameAssetManager;
import io.github.some_example_name.Models.GameCharacter;
import io.github.some_example_name.Models.Player;
import io.github.some_example_name.Views.CharacterSelectionView;
import io.github.some_example_name.Views.MainMenuView;
import io.github.some_example_name.Views.PreGameMenuView;

public class CharacterSelectionController {
    private CharacterSelectionView view;
    private final Player currentPlayer;

    public CharacterSelectionController(Player player) {
        this.currentPlayer = player;
    }

    public void setView(CharacterSelectionView view) {
        this.view = view;

        view.getConfirmButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String selected = view.getSelectedCharacter();
                System.out.println("Selected character: " + selected);

                // ذخیره‌ی کاراکتر انتخاب‌شده در Player
                currentPlayer.setSelectedCharacter(selected);

                // ==== ست کردن health و speed پلیر ====
                switch (selected.toLowerCase()) {
                    case "shana":
                        currentPlayer.setPlayerHealth(4);
                        currentPlayer.setSpeed(4);
                        break;
                    case "diamond":
                        currentPlayer.setPlayerHealth(7);
                        currentPlayer.setSpeed(1);
                        break;
                    case "scarlet":
                        currentPlayer.setPlayerHealth(1);
                        currentPlayer.setSpeed(7);
                        break;
                    case "lilith":
                        currentPlayer.setPlayerHealth(4);
                        currentPlayer.setSpeed(4);
                        break;
                    case "dasher":
                        currentPlayer.setPlayerHealth(2);
                        currentPlayer.setSpeed(6);
                        break;
                    default:
                        currentPlayer.setPlayerHealth(3);
                        currentPlayer.setSpeed(3);
                }

                // رفتن به صفحه بعد
                Main.getMain().setScreen(
                    new PreGameMenuView(new PreGameMenuController(currentPlayer),
                        GameAssetManager.getSkin(), currentPlayer));
            }
        });

        view.getBackButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(
                    new MainMenuView(new MainMenuController(currentPlayer),
                        GameAssetManager.getSkin(), currentPlayer));
            }
        });
    }

    // اضافه کردن چک نال برای جلوگیری از کرش:
    public void updateCharacterDetails(String characterName) {
        GameCharacter ch;
        switch (characterName.toLowerCase()) {
            case "shana":    ch = new GameCharacter("SHANA", 4, 4); break;
            case "diamond":  ch = new GameCharacter("DIAMOND", 7, 1); break;
            case "scarlet":  ch = new GameCharacter("SCARLET", 1, 7); break;
            case "lilith":   ch = new GameCharacter("LILITH", 4, 4); break;
            case "dasher":   ch = new GameCharacter("DASHER", 2, 6); break;
            default:         ch = new GameCharacter("UNKNOWN", 0, 0);
        }
        if (view != null) {
            view.setCharacterDetails(ch);
        } else {
            System.out.println("CharacterSelectionView is not initialized (null). [DEBUG]");
        }
    }

    public Player getPlayer() {
        return currentPlayer;
    }
}
