package io.github.some_example_name.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class SettingsManager {
    private static final Preferences prefs = Gdx.app.getPreferences("game_settings");

    // مقدار پیش‌فرض
    private static float musicVolume = 1f;
    private static String musicTrack = "Default";
    private static boolean sfxEnabled = true;
    private static String controlType = "WASD";
    private static boolean autoReload = true;
    private static boolean grayscale = false;

    public static void loadSettings() {
        musicVolume = prefs.getFloat("musicVolume", 1f);
        musicTrack = prefs.getString("musicTrack", "Default");
        sfxEnabled = prefs.getBoolean("sfxEnabled", true);
        controlType = prefs.getString("controlType", "WASD");
        autoReload = prefs.getBoolean("autoReload", true);
        grayscale = prefs.getBoolean("grayscale", false);
    }

    public static void saveSettings() {
        prefs.putFloat("musicVolume", musicVolume);
        prefs.putString("musicTrack", musicTrack);
        prefs.putBoolean("sfxEnabled", sfxEnabled);
        prefs.putString("controlType", controlType);
        prefs.putBoolean("autoReload", autoReload);
        prefs.putBoolean("grayscale", grayscale);
        prefs.flush();
    }

    public static float getMusicVolume() { return musicVolume; }
    public static void setMusicVolume(float v) { musicVolume = v; saveSettings(); }

    public static String getMusicTrack() { return musicTrack; }
    public static void setMusicTrack(String s) { musicTrack = s; saveSettings(); }

    public static boolean isSfxEnabled() { return sfxEnabled; }
    public static void setSfxEnabled(boolean b) { sfxEnabled = b; saveSettings(); }

    public static String getControlType() { return controlType; }
    public static void setControlType(String s) { controlType = s; saveSettings(); }

    public static boolean isAutoReload() { return autoReload; }
    public static void setAutoReload(boolean b) { autoReload = b; saveSettings(); }

    public static boolean isGrayscale() { return grayscale; }
    public static void setGrayscale(boolean b) { grayscale = b; saveSettings(); }
}
