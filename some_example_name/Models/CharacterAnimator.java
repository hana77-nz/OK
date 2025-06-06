package io.github.some_example_name.Models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class CharacterAnimator {
    public enum State { IDLE, RUN, WALK }
    private Animation<TextureRegion> idleAnimation, runAnimation, walkAnimation;
    private State currentState = State.IDLE;
    private float stateTime = 0;
    private float x, y;
    private int width, height;

    public CharacterAnimator(String characterName) {
        switch (characterName.toUpperCase()) {
            case "SHANA":
                setupAnimations("T_Shana.png", 8, 32, 32);
                break;
            case "DIAMOND":
                setupAnimations("T_Diamond #7829.png", 8, 32, 32);
                break;
            case "SCARLET":
                setupAnimations("T_Scarlett.png", 8, 32, 32);
                break;
            case "LILITH":
                setupAnimations("T_Lilith.png", 8, 32, 32);
                break;
            case "DASHER":
                // Dasher فریم‌ها و ابعاد خاص خودش
                setupAnimations("T_Dasher.png", 12, 32, 32); // فرض بر ۱۲ فریم/حالت خاص
                break;
            default:
                setupAnimations("T_Shana.png", 8, 32, 32);
                break;
        }
    }

    private void setupAnimations(String fileName, int framesPerRow, int frameWidth, int frameHeight) {
        Texture sheet = new Texture("Images/Texture2D/Character/" + fileName);//Images\Texture2D\Character\T_Dasher.png"
        // فرض: هر سطر یک حالت (idle, run, walk)، هر ستون یک فریم
        TextureRegion[][] regions = TextureRegion.split(sheet, frameWidth, frameHeight);

        idleAnimation = new Animation<>(0.1f, new Array<>(regions[0]), Animation.PlayMode.LOOP);
        runAnimation  = new Animation<>(0.09f, new Array<>(regions[1]), Animation.PlayMode.LOOP);
        walkAnimation = new Animation<>(0.1f, new Array<>(regions[2]), Animation.PlayMode.LOOP);

        width = frameWidth;
        height = frameHeight;
    }

    public void update(float delta, State state, float posX, float posY) {
        this.currentState = state;
        this.x = posX;
        this.y = posY;
        stateTime += delta;
    }

    public void render(Batch batch, float x, float y) {
        Animation<TextureRegion> anim = idleAnimation;
        switch (currentState) {
            case RUN:  anim = runAnimation; break;
            case WALK: anim = walkAnimation; break;
            case IDLE:
            default: anim = idleAnimation;
        }
        TextureRegion frame = anim.getKeyFrame(stateTime, true);
        batch.draw(frame, x, y, width, height);
    }

}
