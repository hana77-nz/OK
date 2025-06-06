package io.github.some_example_name.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EyeBat extends Enemy {
    private float speed = 1.5f;
    private float shootCooldown = 0;
    private final float SHOOT_INTERVAL = 3f;
    private final float BULLET_SPEED = 400f;
    private final float BULLET_DAMAGE = 1f;

    private final List<Bullet> enemyBullets;

    // Animation fields
    private Animation<TextureRegion> animation;
    private TextureRegion deathFrame;
    private float stateTime = 0f;
    private boolean dead = false;
    private static Texture[] textures = null; // فریم‌ها رو استاتیک نگه می‌داریم
    private static Texture deathTexture = null;

    public EyeBat(Vector2 spawnPosition) {
        super("Eyebat", 50, getFirstFrameTexture(), spawnPosition);

        // حالا اینجا Animation رو می‌سازیم
        TextureRegion[] frames = new TextureRegion[4];
        for (int i = 0; i < 4; i++) {
            frames[i] = new TextureRegion(textures[i]);
        }
        animation = new Animation<TextureRegion>(0.15f, frames);
        animation.setPlayMode(Animation.PlayMode.LOOP);

        deathFrame = new TextureRegion(deathTexture);

        this.sprite.setSize(frames[0].getRegionWidth(), frames[0].getRegionHeight());
        this.sprite.setTexture(frames[0].getTexture());

        this.enemyBullets = new ArrayList<>();
        this.xpDrop = 30;
    }

    // متد کمک‌کننده برای بارگذاری تکسچرها فقط یکبار:
    private static Texture getFirstFrameTexture() {
        if (textures == null) {
            textures = new Texture[4];
            for (int i = 0; i < 4; i++) {
                textures[i] = new Texture(Gdx.files.internal("Images/Sprite/Monsters/T_EyeBat_" + i + ".png"));
            }
            deathTexture = new Texture(Gdx.files.internal("Images/Sprite/Monsters/T_EyeBat_EM.png"));
        }
        return textures[0];
    }

    @Override
    public void update(float delta, Player player) {
        if (!dead) {
            moveToPlayer(player, speed);

            shootCooldown -= delta;
            if (shootCooldown <= 0f) {
                shootAtPlayer(player);
                shootCooldown = SHOOT_INTERVAL;
            }
            stateTime += delta;
        }

        // Update bullets
        Iterator<Bullet> iter = enemyBullets.iterator();
        while (iter.hasNext()) {
            Bullet bullet = iter.next();
            bullet.update(delta);
            if (!bullet.isActive()) {
                iter.remove();
            }
        }
    }

    private void shootAtPlayer(Player player) {
        Vector2 playerPos = player.getPositionCenter();
        Vector2 shootPos = this.getPositionCenter();

        Bullet bullet = new Bullet(
            shootPos.x, shootPos.y,
            playerPos.x, playerPos.y,
            BULLET_SPEED, BULLET_DAMAGE,
            Bullet.BulletOwner.ENEMY
        );

        enemyBullets.add(bullet);
    }

    public List<Bullet> getBullets() {
        return enemyBullets;
    }

    // برای کشته شدن
    public void die() {
        dead = true;
    }

    // متد رسم صحیح با انیمیشن
    @Override
    public void draw(Batch batch, float offsetX, float offsetY) {
        if (dead) {
            batch.draw(deathFrame, getX() + offsetX, getY() + offsetY);
        } else {
            TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
            batch.draw(currentFrame, getX() + offsetX, getY() + offsetY);
        }
        for (Bullet bullet : enemyBullets) {
            bullet.draw(batch); // فقط batch بده
        }

    }
}
