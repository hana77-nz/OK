package io.github.some_example_name.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Controllers.ProfileController;
import io.github.some_example_name.Main;
import io.github.some_example_name.Models.Player;
import io.github.some_example_name.Controllers.MainMenuController;

public class ProfileView implements Screen {
    private final Stage stage;
    private final ProfileController controller;

    // آواتارهای پیش‌فرض
    private final String[] avatars = {
        "avatars/avatar1.png", "avatars/avatar2.png", "avatars/avatar3.png", "avatars/avatar4.png"
    };

    public ProfileView(Player player, Skin skin) {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        controller = new ProfileController();

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        Label titleLabel = new Label("Player Profile", skin, "title");
        table.add(titleLabel).colspan(2).padBottom(20);
        table.row();

        // نمایش آواتار فعلی
        Texture avatarTexture = new Texture(Gdx.files.internal(player.getAvatarPath()));
        Image avatarImg = new Image(avatarTexture);
        table.add(avatarImg).colspan(2).size(64, 64).padBottom(10).row();

        // انتخاب آواتار جدید از لیست
        Label avatarLabel = new Label("Change Avatar:", skin);
        final SelectBox<String> avatarBox = new SelectBox<>(skin);
        avatarBox.setItems(avatars);
        avatarBox.setSelected(player.getAvatarPath());
        table.add(avatarLabel).left();
        table.add(avatarBox).left().width(200).row();

        TextButton avatarBtn = new TextButton("Set Avatar", skin);
        table.add(avatarBtn).colspan(2).padBottom(15).row();

        // نمایش username و ویرایش
        table.add(new Label("Username:", skin)).left().padRight(10);
        final TextField usernameField = new TextField(player.getUsername(), skin);
        table.add(usernameField).width(180).row();

        TextButton changeUsernameBtn = new TextButton("Change Username", skin);
        table.add(changeUsernameBtn).colspan(2).padBottom(10).row();

        // نمایش kills و score و survive time فقط اطلاعات
        table.add(new Label("Kills:", skin)).left().padRight(10);
        table.add(new Label(String.valueOf(player.getKills()), skin)).left().row();
        table.add(new Label("Score:", skin)).left().padRight(10);
        table.add(new Label(String.valueOf(player.getScore()), skin)).left().row();
        table.add(new Label("Survive Time:", skin)).left().padRight(10);
        table.add(new Label(String.format("%.2f seconds", player.getSurviveTime()), skin)).left().row();

        // تغییر پسورد
        table.add(new Label("New Password:", skin)).left().padRight(10);
        final TextField passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        table.add(passwordField).width(180).row();

        TextButton changePasswordBtn = new TextButton("Change Password", skin);
        table.add(changePasswordBtn).colspan(2).padBottom(10).row();

        // حذف حساب
        TextButton deleteBtn = new TextButton("Delete Account", skin);
        table.add(deleteBtn).colspan(2).padBottom(20).row();

        // پیام خطا/موفقیت
        final Label msgLabel = new Label("", skin);
        table.add(msgLabel).colspan(2).padBottom(15).row();

        // برگشت
        TextButton backButton = new TextButton("Back", skin);
        table.add(backButton).colspan(2).padTop(15);

        // Listenerها

        // تغییر آواتار
        avatarBtn.addListener(e -> {
            String newAvatar = avatarBox.getSelected();
            controller.changeAvatar(player, newAvatar);
            avatarImg.setDrawable(new Image(new Texture(Gdx.files.internal(newAvatar))).getDrawable());
            msgLabel.setText("Avatar changed!");
            return false;
        });

        // تغییر نام کاربری
        changeUsernameBtn.addListener(e -> {
            String newName = usernameField.getText().trim();
            if (newName.isEmpty()) {
                msgLabel.setText("[Error] Username cannot be empty.");
                return false;
            }
            if (controller.isUsernameDuplicate(newName, player)) {
                msgLabel.setText("[Error] Username already exists.");
                return false;
            }
            controller.changeUsername(player, newName);
            msgLabel.setText("Username changed!");
            return false;
        });

        // تغییر پسورد
        changePasswordBtn.addListener(e -> {
            String pass = passwordField.getText();
            if (!controller.isPasswordStrong(pass)) {
                msgLabel.setText("[Error] Password too weak!");
                return false;
            }
            controller.changePassword(player, pass);
            msgLabel.setText("Password changed!");
            passwordField.setText("");
            return false;
        });

        // حذف حساب کاربری
        deleteBtn.addListener(e -> {
            controller.deleteAccount(player);
            msgLabel.setText("Account deleted! Returning to main menu...");
            // تایمر ساده برای برگشت
            stage.addAction(com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence(
                com.badlogic.gdx.scenes.scene2d.actions.Actions.delay(1f),
                com.badlogic.gdx.scenes.scene2d.actions.Actions.run(() -> {
                    Main.getMain().setScreen(new MainMenuView(new MainMenuController(player), skin, null));
                })
            ));
            return false;
        });

        // برگشت
        backButton.addListener(e -> {
            Main.getMain().setScreen(new MainMenuView(new MainMenuController(player), skin, player));
            return false;
        });
    }

    @Override public void show() {}
    @Override public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
