package io.github.some_example_name.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;

public class Weapon {
    private String name;
    private float damage;
    private int magazineSize;
    private int ammo;
    private float reloadTime;
    private float fireRate; // فاصله بین دو شلیک (ثانیه)
    private long lastShotTime;
    private boolean reloading;
    private long reloadStartTime;
   // private int ammo;


    public Weapon(String name, float damage, int magazineSize, float fireRate, float reloadTime) {
        this.name = name;
        this.damage = damage;
        this.magazineSize = magazineSize;
        this.fireRate = fireRate;
        this.reloadTime = reloadTime;

        this.ammo = magazineSize;
        this.lastShotTime = 0;
        this.reloading = false;
    }

    public boolean canShoot() {
        long currentTime = TimeUtils.nanoTime();
        float timeSinceLastShot = (currentTime - lastShotTime) / 1_000_000_000f;

        return !reloading && ammo > 0 && timeSinceLastShot >= fireRate;
    }

    public void shoot() {
        if (canShoot()) {
            ammo--;
            lastShotTime = TimeUtils.nanoTime();

            if (ammo <= 0) {
                startReload();
            }
        }
    }

    public void startReload() {
        reloading = true;
        reloadStartTime = TimeUtils.nanoTime();
    }

    public void update() {
        if (reloading) {
            float timePassed = (TimeUtils.nanoTime() - reloadStartTime) / 1_000_000_000f;
            if (timePassed >= reloadTime) {
                reloading = false;
                ammo = magazineSize;
            }
        }
    }

    public boolean isReloading() {
        return reloading;
    }

    public int getMagazineSize() {
        return magazineSize;
    }

    public float getDamage() {
        return damage;
    }

    public String getName() {
        return name;
    }

    public float getReloadTime() {
        return reloadTime;
    }

    public float getFireRate() {
        return fireRate;
    }
    public int getAmmo() {
        return ammo;
    }
    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }
    public void reload() {
        this.ammo = this.magazineSize;
    }
}
