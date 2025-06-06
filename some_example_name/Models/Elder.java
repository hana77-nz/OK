package io.github.some_example_name.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Elder extends Enemy {
    private float dashCooldown = 0;
    private final float DASH_INTERVAL = 5f;
    private boolean shieldActive = true;
    private float shieldRadius = 150f;
    private float speed = 0.8f;

    private Vector2 dashTarget = null;
    private boolean isDashing = false;
    private float dashSpeed = 8f;

    // انیمیشن و فریم‌ها
    private TextureRegion normalFrame;
    private TextureRegion deadFrame;
    private boolean dead = false;

    public Elder(Vector2 spawnPosition) {
        // فقط همون Texture اولیه رو اینجا میدیم به super (الزاماً اولین کار constructor)
        super("Elder", 400, getNormalTexture(), spawnPosition);

        // Frame معمولی و مرگ رو اینجا ست می‌کنیم (بعد super)
        normalFrame = new TextureRegion(getNormalTexture());
        deadFrame = new TextureRegion(getDeadTexture());

        this.sprite.setSize(normalFrame.getRegionWidth(), normalFrame.getRegionHeight());
        this.sprite.setTexture(normalFrame.getTexture());

        this.xpDrop = 35;
    }

    // استاتیک لودینگ تکسچرها (فقط یک بار لود بشه)
    private static Texture normalTexture = null, deadTexture = null;
    private static Texture getNormalTexture() {
        if (normalTexture == null)
            normalTexture = new Texture(Gdx.files.internal("Images/Sprite/Monsters/ElderBrain.png"));
        return normalTexture;
    }
    private static Texture getDeadTexture() {
        if (deadTexture == null)
            deadTexture = new Texture(Gdx.files.internal("Images/Sprite/Monsters/ElderBrain_Em.png"));
        return deadTexture;
    }

    @Override
    public void update(float delta, Player player) {
        if (!dead) {
            dashCooldown -= delta;

            if (shieldActive) {
                if (dashCooldown <= 0) {
                    startDash(player);
                    dashCooldown = DASH_INTERVAL;
                    reduceShield();
                }
                if (isDashing && dashTarget != null) {
                    dashTowardsTarget(delta);
                }
            } else {
                moveToPlayer(player, speed);
            }
        }
    }

    private void startDash(Player player) {
        dashTarget = new Vector2(player.getPositionCenter());
        isDashing = true;
        System.out.println("Elder dashes toward the player.");
    }

    private void dashTowardsTarget(float delta) {
        Vector2 currentPos = getPositionCenter();
        Vector2 direction = dashTarget.cpy().sub(currentPos).nor();
        float moveX = direction.x * dashSpeed;
        float moveY = direction.y * dashSpeed;
        sprite.setPosition(sprite.getX() + moveX * delta, sprite.getY() + moveY * delta);

        if (currentPos.dst(dashTarget) < 10f) {
            isDashing = false;
            dashTarget = null;
        }
    }

    private void reduceShield() {
        shieldRadius -= 10f;
        if (shieldRadius <= 0) {
            shieldActive = false;
            System.out.println("Elder shield is down.");
        }
    }

    public boolean isShieldActive() {
        return shieldActive;
    }

    public float getShieldRadius() {
        return shieldRadius;
    }

    public void die() {
        dead = true;
    }

    @Override
    public void draw(Batch batch, float offsetX, float offsetY) {
        float drawX = getX() + offsetX;
        float drawY = getY() + offsetY;

        if (dead) {
            batch.draw(deadFrame, drawX, drawY);
        } else {
            batch.draw(normalFrame, drawX, drawY);
        }
        // اگه شیلد یا افکت خاص داری اینجا بکش
    }

}
