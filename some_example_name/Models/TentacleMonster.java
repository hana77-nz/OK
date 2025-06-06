package io.github.some_example_name.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class TentacleMonster extends Enemy {
    private float speed = 1.2f;

    // Animation fields
    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> spawnAnimation;
    private TextureRegion attackFrame;
    private float stateTime = 0f;
    private boolean dead = false;
    private boolean spawning = true;
    private boolean attacking = false;

    public TentacleMonster(Vector2 spawnPosition) {
        super("TentacleMonster", 25, getIdleFrameTexture(), spawnPosition);

        // Idle animation
        TextureRegion[] idleFrames = new TextureRegion[4];
        idleFrames[0] = new TextureRegion(getIdleFrameTexture());
        idleFrames[1] = new TextureRegion(new Texture(Gdx.files.internal("Images/Sprite/Monsters/T_TentacleEnemy_1.png")));
        idleFrames[2] = new TextureRegion(new Texture(Gdx.files.internal("Images/Sprite/Monsters/T_TentacleEnemy_2.png")));
        idleFrames[3] = new TextureRegion(new Texture(Gdx.files.internal("Images/Sprite/Monsters/T_TentacleEnemy_3.png")));
        idleAnimation = new Animation<TextureRegion>(0.12f, idleFrames);
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);

        // Spawn animation
        TextureRegion[] spawnFrames = new TextureRegion[3];
        spawnFrames[0] = new TextureRegion(new Texture(Gdx.files.internal("Images/Sprite/Monsters/T_TentacleSpawn_0.png")));
        spawnFrames[1] = new TextureRegion(new Texture(Gdx.files.internal("Images/Sprite/Monsters/T_TentacleSpawn_1.png")));
        spawnFrames[2] = new TextureRegion(new Texture(Gdx.files.internal("Images/Sprite/Monsters/T_TentacleSpawn_2.png")));
        spawnAnimation = new Animation<TextureRegion>(0.09f, spawnFrames);

        // Attack frame
        attackFrame = new TextureRegion(new Texture(Gdx.files.internal("Images/Sprite/Monsters/T_TentacleAttack.png")));

        // Set size
        this.sprite.setSize(idleFrames[0].getRegionWidth(), idleFrames[0].getRegionHeight());
        this.sprite.setTexture(idleFrames[0].getTexture());

        this.xpDrop = 10;
    }

    private static Texture idleTex0;
    private static Texture getIdleFrameTexture() {
        if (idleTex0 == null)
            idleTex0 = new Texture(Gdx.files.internal("Images/Sprite/Monsters/T_TentacleEnemy_0.png"));
        return idleTex0;
    }

    @Override
    public void update(float delta, Player player) {
        if (!dead) {
            // اگر تازه ظاهر شده: فقط انیمیشن اسپاون تا پایانش
            if (spawning) {
                stateTime += delta;
                if (spawnAnimation.isAnimationFinished(stateTime)) {
                    spawning = false;
                    stateTime = 0f;
                }
                return;
            }
            // حمله
            if (!attacking) {
                moveToPlayer(player, speed);
                stateTime += delta;
            }
        }
    }

    public void startAttack() {
        attacking = true;
        stateTime = 0f;
    }

    public void stopAttack() {
        attacking = false;
        stateTime = 0f;
    }

    @Override
    public void die() {
        dead = true;
    }

    @Override
    public void draw(Batch batch, float offsetX, float offsetY) {
        float drawX = getX() + offsetX;
        float drawY = getY() + offsetY;

        if (dead) {
            TextureRegion frame = idleAnimation.getKeyFrame(stateTime, false);
            batch.draw(frame, drawX, drawY);
        } else if (spawning) {
            TextureRegion frame = spawnAnimation.getKeyFrame(stateTime, false);
            batch.draw(frame, drawX, drawY);
        } else if (attacking) {
            batch.draw(attackFrame, drawX, drawY);
        } else {
            TextureRegion frame = idleAnimation.getKeyFrame(stateTime, true);
            batch.draw(frame, drawX, drawY);
        }
    }

}
