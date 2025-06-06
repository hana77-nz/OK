package io.github.some_example_name.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameAssetManager {
    private static final AssetManager assetManager = new AssetManager();
    private static Skin skin;

    // موزیک‌ها
    private static Music defaultMusic;
    private static Music actionMusic;
    private static Music calmMusic;
    private static Music currentMusic;

    private static Animation<TextureRegion> playerIdleAnimation;

    // Singleton
    public static void loadAssets() {
        // Load textures
        assetManager.load("Images/Sprite/Character/Idle_1.png", Texture.class);
        assetManager.load("Images/Sprite/Character/Idle_2.png", Texture.class);
        assetManager.load("Images/Sprite/Character/Idle_3.png", Texture.class);
        assetManager.load("Images/Sprite/Character/Idle_4.png", Texture.class);

        // Load music
//        assetManager.load("audio/background.mp3", Music.class);   // Default
//        assetManager.load("audio/action.mp3", Music.class);       // Action
//        assetManager.load("audio/calm.mp3", Music.class);         // Calm

        // Load other assets here if needed
        assetManager.finishLoading();

        // Prepare musics
//        defaultMusic = assetManager.get("audio/background.mp3", Music.class);
//        actionMusic = assetManager.get("audio/action.mp3", Music.class);
//        calmMusic = assetManager.get("audio/calm.mp3", Music.class);

        //setMusic("Default"); // پخش موزیک پیش‌فرض و ست کردن ولوم

        // Prepare animation
        createIdleAnimation();
    }

    private static void createIdleAnimation() {
        TextureRegion[] frames = new TextureRegion[4];
        frames[0] = new TextureRegion(assetManager.get("Images/Sprite/Character/Idle_1.png", Texture.class));
        frames[1] = new TextureRegion(assetManager.get("Images/Sprite/Character/Idle_2.png", Texture.class));
        frames[2] = new TextureRegion(assetManager.get("Images/Sprite/Character/Idle_3.png", Texture.class));
        frames[3] = new TextureRegion(assetManager.get("Images/Sprite/Character/Idle_4.png", Texture.class));

        playerIdleAnimation = new Animation<>(0.2f, frames); // 0.2 ثانیه برای هر فریم
        playerIdleAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public static Skin getSkin() {
        if (skin == null) {
            skin = new Skin(Gdx.files.internal("skin/biological-attack-ui.json"));
        }
        return skin;
    }

    // موزیک فعلی
    public static Music getCurrentMusic() {
        return currentMusic;
    }

    // تغییر موزیک در حال پخش
    public static void setMusic(String name) {
        // توقف موزیک فعلی
        if (currentMusic != null) currentMusic.stop();

        switch (name) {
            case "Default":
                currentMusic = defaultMusic;
                break;
            case "Action Theme":
                currentMusic = actionMusic;
                break;
            case "Calm Theme":
                currentMusic = calmMusic;
                break;
            default:
                currentMusic = defaultMusic;
        }

        //currentMusic.setLooping(true);
        // اگر ولوم رو از SettingsManager می‌گیری:
        float volume = io.github.some_example_name.Models.SettingsManager.getMusicVolume();
       // currentMusic.setVolume(volume);
      //  currentMusic.play();
    }

    // تغییر ولوم موزیک فعلی
    public static void setMusicVolume(float vol) {
        if (currentMusic != null) currentMusic.setVolume(vol);
    }

    public static Animation<TextureRegion> getPlayerIdleAnimation() {
        return playerIdleAnimation;
    }

    public static Texture getCharacterTexture() {
        return assetManager.get("Images/Sprite/Character/Idle_1.png", Texture.class);
    }

    public static void dispose() {
        if (defaultMusic != null) defaultMusic.dispose();
        if (actionMusic != null) actionMusic.dispose();
        if (calmMusic != null) calmMusic.dispose();
        assetManager.dispose();
        if (skin != null) skin.dispose();
    }
}
