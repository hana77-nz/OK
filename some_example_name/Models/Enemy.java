package io.github.some_example_name.Models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.some_example_name.Main;

public abstract class Enemy {
    protected String name;
    protected int health;
    protected Texture texture;
    protected Sprite sprite;
    protected int xpDrop = 10; // پیش‌فرض


    public Enemy(String name, int health, Texture texture, Vector2 spawnPosition){
        this.name = name;
        this.health = health;
        this.sprite = new Sprite(texture);
        this.sprite.setPosition(spawnPosition.x, spawnPosition.y);
    }

    public abstract void update(float delta, Player player);

    public void moveToPlayer(Player player, float speed) {
        Vector2 playerPos = new Vector2(player.getPosX(), player.getPosY());
        Vector2 direction = playerPos.sub(sprite.getX(), sprite.getY()).nor();
        sprite.translate(direction.x * speed, direction.y * speed);
    }

//    public void render() {
//        if (texture != null) {
//            sprite.draw(Main.getBatch());
//
//        }
//    }



    public void render(SpriteBatch batch, float drawX, float drawY) {
        if (sprite != null) {
            sprite.setPosition(drawX, drawY);
            sprite.draw(batch);
        }
        // اگر Enemy انیمیتوره، render(animator, ...) مشابه بالا
    }




    public Sprite getSprite() {
        return sprite;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void takeDamage(float dmg) {
        this.health -= dmg;
    }

    public Rectangle getBounds() {
        return new Rectangle(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }


    public Vector2 getPosition() {
        return new Vector2(sprite.getX(), sprite.getY());
    }

    public Vector2 getPositionCenter() {
        return new Vector2(sprite.getX() + sprite.getWidth() / 2f,
            sprite.getY() + sprite.getHeight() / 2f);
    }
    public int getXpDrop() {
        return xpDrop;
    }
    public String getType() {
        return this.getClass().getSimpleName();
    }

    public float getX() { return sprite.getX(); }
    public float getY() { return sprite.getY(); }

    // Enemy.java
    public abstract void draw(Batch batch, float offsetX, float offsetY);

    public void die() {
        // پیاده‌سازی پایه. اگر Enemy خاص نیاز به انیمیشن مرگ داشت، override میشه.
        // مثلاً:
        // this.dead = true;   (اگر متغیر dead داشتی)
    }
}
