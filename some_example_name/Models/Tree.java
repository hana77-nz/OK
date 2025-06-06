package io.github.some_example_name.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Tree extends Enemy {
    private Animation<TextureRegion> idleAnimation;
    private float stateTime = 0f;
    private boolean dead = false;

    public Tree(Vector2 spawnPosition) {
        super("Tree", 300, getFirstFrameTexture(), spawnPosition);

        // چهار فریم انیمیشن ایستاده
        TextureRegion[] frames = new TextureRegion[4];
        frames[0] = new TextureRegion(getFirstFrameTexture());
        frames[1] = new TextureRegion(new Texture(Gdx.files.internal("Images/Sprite/Monsters/T_TreeMonster_1.png")));
        frames[2] = new TextureRegion(new Texture(Gdx.files.internal("Images/Sprite/Monsters/T_TreeMonster_2.png")));
        frames[3] = new TextureRegion(new Texture(Gdx.files.internal("Images/Sprite/Monsters/T_TreeMonsterWalking.png")));

        idleAnimation = new Animation<TextureRegion>(0.22f, frames);
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);

        this.sprite.setSize(frames[0].getRegionWidth(), frames[0].getRegionHeight());
        this.sprite.setTexture(frames[0].getTexture());

        this.xpDrop = 20;
    }

    private static Texture treeTex0;
    private static Texture getFirstFrameTexture() {
        if (treeTex0 == null)
            treeTex0 = new Texture(Gdx.files.internal("Images/Sprite/Monsters/T_TreeMonster_0.png"));
        return treeTex0;
    }

    @Override
    public void update(float delta, Player player) {
        if (!dead) {
            stateTime += delta;
        }
    }

    @Override
    public void die() {
        dead = true;
    }

    @Override
    public void draw(Batch batch, float offsetX, float offsetY) {
        TextureRegion frame = idleAnimation.getKeyFrame(stateTime, true);
        batch.draw(frame, getX() + offsetX, getY() + offsetY);
    }

}
