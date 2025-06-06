package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.github.some_example_name.Controllers.RegisterMenuController;
import io.github.some_example_name.Models.GameAssetManager;
import io.github.some_example_name.Views.MainMenuView;
import io.github.some_example_name.Views.RegisterMenuView;

public class Main extends Game {
    public static Main instance; // singleton reference
    private static Main main;
    private static SpriteBatch batch;

    @Override
    public void create() {
        instance = this;
        main = this;
        batch = new SpriteBatch();

        // *** اول همه‌ی assetها رو لود کن ***
        GameAssetManager.loadAssets();

        // حالا با خیال راحت از اسکین و بقیه assetها استفاده کن
        Skin skin = GameAssetManager.getSkin();

        // نمایش صفحه ثبت‌نام به عنوان شروع
        RegisterMenuController registerController = new RegisterMenuController();
        RegisterMenuView registerView = new RegisterMenuView(this, registerController, skin);
        registerController.setView(registerView);

        setScreen(registerView);
    }


    @Override
    public void render() {
        super.render(); // تمام رندرها را به اسکرین جاری منتقل می‌کند
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public static Main getMain() {
        return main;
    }

    public static void setMain(Main main) {
        Main.main = main;
    }

    public static SpriteBatch getBatch() {
        return batch;
    }

    public static void setBatch(SpriteBatch batch) {
        Main.batch = batch;
    }

    public static int getWidth() {
        return Gdx.graphics.getWidth();
    }

    public static int getHeight() {
        return Gdx.graphics.getHeight();
    }
}
