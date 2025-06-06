package io.github.some_example_name.Controllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.some_example_name.Main;
import io.github.some_example_name.Models.GameAssetManager;
import io.github.some_example_name.Models.Player;
import io.github.some_example_name.Models.PreGame;
import io.github.some_example_name.Views.GameView;
import io.github.some_example_name.Views.MainMenuView;
import io.github.some_example_name.Views.PreGameMenuView;

public class PreGameMenuController {
    private PreGameMenuView view;
    private final Player currentPlayer;

    public PreGameMenuController(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setView(PreGameMenuView view) {
        this.view = view;

        // نمایش اطلاعات پلیر
        if (view != null && currentPlayer != null) {
            view.updatePlayerInfo(currentPlayer);
        }

        // دکمه Start بازی
        if (view != null) {
            view.getPlayButton().addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    String selectedHero = view.getSelectedHero();
                    String selectedWeapon = view.getSelectedWeapon();
                    float selectedDuration = view.getSelectedDuration();

                    // ساخت مدل PreGame
                    PreGame preGame = new PreGame(currentPlayer.getUsername(), selectedHero, selectedWeapon, selectedDuration);

                    // رفتن به صفحه بازی
                    Main.getMain().setScreen(new GameView(currentPlayer, preGame));
                }
            });

            // دکمه بازگشت به منوی اصلی
            view.getBackButton().addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Main.getMain().setScreen(
                        new MainMenuView(
                            new MainMenuController(currentPlayer),
                            GameAssetManager.getSkin(),
                            currentPlayer
                        )
                    );
                }
            });
        }
    }

    // اگر بعدها لازم شد هندل‌های دیگه اضافه کنی اینجا بنویس
    public void handlePreGameMenuButtons() {
        // Listenerها داخل setView اضافه شدند
    }
}
