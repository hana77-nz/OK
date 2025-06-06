package io.github.some_example_name.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.some_example_name.Main;

public class Bullet {
    private Sprite sprite;
    private Vector2 position;
    private Vector2 velocity;
    private boolean active = true;
    private float speed;
    private float damage;
    private float maxDistance;
    private float traveledDistance;

    public enum BulletOwner {
        PLAYER, ENEMY
    }

    public Bullet(float startX, float startY, float targetX, float targetY, float speed, float damage, BulletOwner owner) {
        this.position = new Vector2(startX, startY);
        this.speed = speed;
        this.damage = damage;
        this.maxDistance = 2000f;
        this.traveledDistance = 0;

        Vector2 direction = new Vector2(targetX - startX, targetY - startY).nor();
        this.velocity = direction.scl(speed);

        Texture texture;
        if (owner == BulletOwner.PLAYER) {
            texture = new Texture(Gdx.files.internal("Images/CharBullet.png"));
        } else {
            texture = new Texture(Gdx.files.internal("Images/Bullet.png"));
        }
        this.sprite = new Sprite(texture);
        this.sprite.setSize(8, 8); // اندازه استاندارد
        this.sprite.setOriginCenter();
        this.sprite.setRotation(direction.angleDeg());
        this.sprite.setPosition(position.x, position.y);

        this.active = true;
    }

    public void update(float delta) {
        if (!active) return;

        position.x += velocity.x * delta;
        position.y += velocity.y * delta;

        traveledDistance += velocity.len() * delta;
        if (traveledDistance >= maxDistance) {
            active = false;
        }

        sprite.setPosition(position.x, position.y);
    }

    public void render(SpriteBatch batch, float x, float y) {
        if (active) {
            sprite.setPosition(x, y);
            sprite.draw(batch);
        }
    }


    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        this.active = false;
    }

    public float getDamage() {
        return damage;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, sprite.getWidth(), sprite.getHeight());
    }

    public void draw(Batch batch) {
        if (active) {
            sprite.draw(batch);
        }

    }}
