package io.github.some_example_name.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.some_example_name.Models.GameAssetManager;
import io.github.some_example_name.Main;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String username;
    private String password;
    private String avatarPath;
    private String securityQuestion;
    private String securityAnswer;

    private int score;
    private int kills = 0;
    private float surviveTime = 0;

    private Texture playerTexture;
    private Sprite playerSprite;
    private CollisionRect rect;

    private float posX = 0;
    private float posY = 0;
    private int playerHealth = 100;
    private float time = 0;
    private float speed = 4;

    private boolean isPlayerIdle = true;
    private boolean isPlayerRunning = false;
    // حرکت
    private boolean moveUp = false, moveDown = false, moveLeft = false, moveRight = false;
    private int level = 1;
    private int xp = 0;
    private int xpToNextLevel = 20;
    private String selectedCharacter;
    protected Sprite sprite;
    private List<Ability> unlockedAbilities = new ArrayList<>();
    private Ability currentAbility;
    private CharacterAnimator animator;
    private CharacterAnimator.State animState = CharacterAnimator.State.IDLE;
    private float invincibleTimer = 0f;


    public Player() {
        this("guest", "", "SHANA");
    }

    public Player(String username, String password, String charName) {
      this.selectedCharacter = charName;
       this.animator = new CharacterAnimator(charName);
       this.posX = Gdx.graphics.getWidth() / 2f;
       this.posY = Gdx.graphics.getHeight() / 2f;
        this.username = username;
        this.password = password;
        this.avatarPath = "avatars/default.png";
        this.playerTexture = GameAssetManager.getCharacterTexture(); // فرض بر اینه که لود شده
        this.playerSprite = new Sprite(playerTexture);
        this.posX = Gdx.graphics.getWidth() / 2f;
        this.posY = Gdx.graphics.getHeight() / 2f;
        this.playerSprite.setPosition(posX, posY);
        this.playerSprite.setSize(64, 64); // اندازه منطقی‌تر
        this.rect = new CollisionRect(posX, posY, 64, 64);
    }

    public Player(String username, String password) {
        this.posX = Gdx.graphics.getWidth() / 2f;
        this.posY = Gdx.graphics.getHeight() / 2f;
        this.username = username;
        this.password = password;
        this.avatarPath = "avatars/default.png";
        this.playerTexture = GameAssetManager.getCharacterTexture(); // فرض بر اینه که لود شده
        this.playerSprite = new Sprite(playerTexture);
        this.posX = Gdx.graphics.getWidth() / 2f;
        this.posY = Gdx.graphics.getHeight() / 2f;
        this.playerSprite.setPosition(posX, posY);
        this.playerSprite.setSize(64, 64); // اندازه منطقی‌تر
        this.rect = new CollisionRect(posX, posY, 64, 64);
    }

    public void setSelectedCharacter(String charName) {
        this.selectedCharacter = charName;
        this.animator = new CharacterAnimator(charName);
    }
        public void update(float delta) {
        boolean isMoving = moveUp || moveDown || moveLeft || moveRight;
        animState = isMoving ? CharacterAnimator.State.RUN : CharacterAnimator.State.IDLE;
        if (invincibleTimer > 0) invincibleTimer -= delta;
        if (moveUp) posY += speed;
        if (moveDown) posY -= speed;
        if (moveLeft) posX -= speed;
        if (moveRight) posX += speed;

        if (animator != null)
            animator.update(delta, animState, posX, posY);

        surviveTime += delta;
    }
    public void render(float centerX, float centerY) {
        if (animator != null) {
            animator.render(Main.getBatch(), centerX, centerY);
        } else if (playerSprite != null) {
            playerSprite.setPosition(centerX, centerY);
            playerSprite.draw(Main.getBatch());
        }
    }

    // کنترل حرکت
    public void setMoveUp(boolean value) { this.moveUp = value; }
    public void setMoveDown(boolean value) { this.moveDown = value; }
    public void setMoveLeft(boolean value) { this.moveLeft = value; }
    public void setMoveRight(boolean value) { this.moveRight = value; }

    // تعاملات گیم‌پلی
    public void takeDamage(float dmg) {
        playerHealth -= dmg;
        if (playerHealth < 0) playerHealth = 0;
    }

    public void addKill() {
        kills++;
    }

    // ========== Getters & Setters ==========

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getAvatarPath() { return avatarPath; }
    public void setAvatarPath(String avatarPath) { this.avatarPath = avatarPath; }

    public String getSecurityQuestion() { return securityQuestion; }
    public void setSecurityQuestion(String securityQuestion) { this.securityQuestion = securityQuestion; }

    public String getSecurityAnswer() { return securityAnswer; }
    public void setSecurityAnswer(String securityAnswer) { this.securityAnswer = securityAnswer; }

    public void setScore(int score) { this.score = score; }


    public float getSurviveTime() { return surviveTime; }
    public void addSurviveTime(float delta) {
        surviveTime += delta;
    }
    public Texture getPlayerTexture() { return playerTexture; }
    public void setPlayerTexture(Texture playerTexture) { this.playerTexture = playerTexture; }

    public Sprite getPlayerSprite() { return playerSprite; }
    public void setPlayerSprite(Sprite playerSprite) { this.playerSprite = playerSprite; }

    public float getPosX() { return posX; }
    public void setPosX(float posX) { this.posX = posX; }

    public float getPosY() { return posY; }
    public void setPosY(float posY) { this.posY = posY; }

    public int getPlayerHealth() { return playerHealth; }
    public void setPlayerHealth(int playerHealth) { this.playerHealth = playerHealth; }

    public CollisionRect getRect() { return rect; }
    public void setRect(CollisionRect rect) { this.rect = rect; }

    public boolean isPlayerIdle() { return isPlayerIdle; }
    public void setPlayerIdle(boolean playerIdle) { isPlayerIdle = playerIdle; }

    public boolean isPlayerRunning() { return isPlayerRunning; }
    public void setPlayerRunning(boolean playerRunning) { isPlayerRunning = playerRunning; }

    public float getTime() { return time; }
    public void setTime(float time) { this.time = time; }

    public float getSpeed() { return speed; }
    public void setSpeed(float speed) { this.speed = speed; }

    public void addKills(int value) {
        this.kills += value;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public String getSelectedCharacter() {
        return selectedCharacter;
    }

    public Vector2 getPosition() {
        return new Vector2(playerSprite.getX(), playerSprite.getY());
    }

    public Vector2 getPositionCenter() {
        return new Vector2(playerSprite.getX() + playerSprite.getWidth() / 2f,
            playerSprite.getY() + playerSprite.getHeight() / 2f);
    }

    public void gainXP(int amount) {
        xp += amount;
        while (xp >= xpToNextLevel) {
            xp -= xpToNextLevel;
            level++;
            xpToNextLevel = 20 * level;
            grantRandomAbility();
        }
    }

    public int getLevel() {
        return level;
    }

    public int getXP() {
        return xp;
    }

    public int getXPToNextLevel() {
        return xpToNextLevel;
    }

    private void grantRandomAbility() {
        // فقط برای نمایش، بعداً UI نشون می‌دیم
        String[] abilities = {"VITALITY", "DAMAGER", "PROCIncrease", "AMOIncrease", "SPEEDY"};
        String chosen = abilities[(int)(Math.random() * abilities.length)];
        System.out.println("New ability unlocked: " + chosen);
    }

    public Rectangle getBounds() {
        return new Rectangle(playerSprite.getX(), playerSprite.getY(), playerSprite.getWidth(), playerSprite.getHeight());
    }
    public void takeDamage(int dmg) {
        if (invincibleTimer > 0) return;
        playerHealth -= dmg;
        invincibleTimer = 3f;
        if (playerHealth < 0) playerHealth = 0;
    }
    public boolean isInvincible() { return invincibleTimer > 0; }


    public void setPos(float x, float y) {
        this.posX = x;
        this.posY = y;
    }

    public int getScore() {
        // فرض: هر kill 100 امتیاز + مدت زنده ماندن * 10
        return kills * 100 + (int)(surviveTime * 10);
    }

    public List<Ability> getUnlockedAbilities() {
        return unlockedAbilities;
    }

    public void addAbility(Ability ability) {
        if (!unlockedAbilities.contains(ability))
            unlockedAbilities.add(ability);
    }

    public void setCurrentAbility(Ability ability) {
        this.currentAbility = ability;
    }

    public Ability getCurrentAbility() {
        return currentAbility;
    }


}
