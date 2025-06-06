package io.github.some_example_name.Controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import io.github.some_example_name.Main;
import io.github.some_example_name.Models.Bullet;
import io.github.some_example_name.Models.Player;
import io.github.some_example_name.Models.Weapon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WeaponController {
    private final Weapon weapon;
    private final Player player;
    private final List<Bullet> bullets;

    public WeaponController(Player player, Weapon weapon) {
        this.player = player;
        this.weapon = weapon;
        this.bullets = new ArrayList<>();
    }

    public void update(float delta) {
        weapon.update();

        // آپدیت تمام گلوله‌ها
        Iterator<Bullet> it = bullets.iterator();
        while (it.hasNext()) {
            Bullet b = it.next();
            b.update(delta);
            if (!b.isActive()) {
                it.remove();
            }
        }
    }

    public void render(float centerX, float centerY, float offsetX, float offsetY) {
        for (Bullet b : bullets) {
            b.render(Main.getBatch(), b.getPosition().x + offsetX, b.getPosition().y + offsetY);
        }
    }


    public void shoot(float mouseX, float mouseY) {
        if (!weapon.canShoot()) return;

        float playerX = player.getPosX() + 32;
        float playerY = player.getPosY() + 32;
        Vector2 target = new Vector2(mouseX, mouseY);

        String name = weapon.getName().toLowerCase();

        if (name.contains("shotgun")) {
            shootShotgun(playerX, playerY, target);
        } else if (name.contains("smg")) {
            shootSMG(playerX, playerY, target);
        } else {
            shootSingleBullet(playerX, playerY, target);
        }

        weapon.shoot();
    }

    private void shootSingleBullet(float x, float y, Vector2 target) {
        bullets.add(new Bullet(x, y, target.x, target.y, 800, weapon.getDamage(), Bullet.BulletOwner.PLAYER));
    }

    private void shootShotgun(float x, float y, Vector2 target) {
        int pelletCount = 5;
        float spreadAngle = 15f;

        Vector2 direction = target.cpy().sub(x, y).nor();
        float baseAngle = direction.angleDeg();

        for (int i = 0; i < pelletCount; i++) {
            float angleOffset = (i - pelletCount / 2f) * spreadAngle;
            float finalAngle = baseAngle + angleOffset;

            Vector2 rotated = new Vector2(1, 0).setAngleDeg(finalAngle);
            Vector2 bulletTarget = new Vector2(x, y).add(rotated.scl(1000));

            bullets.add(new Bullet(x, y, bulletTarget.x, bulletTarget.y, 800, weapon.getDamage(), Bullet.BulletOwner.PLAYER));
        }
    }

    private void shootSMG(float x, float y, Vector2 target) {
        // دو گلوله با کمی پراکندگی شلیک میشه
        for (int i = 0; i < 2; i++) {
            Vector2 spread = target.cpy().sub(x, y).nor();
            spread.rotateDeg((float) (Math.random() * 10 - 5)); // -5 تا +5 درجه

            Vector2 bulletTarget = new Vector2(x, y).add(spread.scl(1000));
            bullets.add(new Bullet(x, y, bulletTarget.x, bulletTarget.y, 900, weapon.getDamage(), Bullet.BulletOwner.PLAYER));
        }
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public Weapon getWeapon() {
        return weapon;
    }
    public void setAmmo(int ammo) {
        weapon.setAmmo(ammo);
    }

    public void reload() {
        weapon.reload();
    }

}
