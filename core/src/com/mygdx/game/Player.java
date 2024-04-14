package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;

public class Player {
    private String name;
    private int level;
    private int health;
    private int stamina;
    private int experience;
    private Rectangle bucket;
    private float playerSpeed;

    public Player(String name, int screenWidth, int screenHeight, int bucketWidth, int bucketHeight) {
        this.name = name;
        this.level = 1;
        this.health = 100;
        this.stamina = 100;
        this.experience = 0;
        this.bucket = new Rectangle((float) screenWidth / 2 - (float) bucketWidth / 2, 20, bucketWidth, bucketHeight);
        this.playerSpeed = 200 * Gdx.graphics.getDeltaTime();
    }

    public void levelUp() {
        this.level++;
        this.health += 10;
        this.stamina += 10;
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public void dealDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    public void gainExperience(int exp) {
        this.experience += exp;
        if (this.experience >= this.level * 100) {
            this.levelUp();
            this.experience = 0;
        }
    }

    public void handleInput() {
        float dx = 0, dy = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) dx -= playerSpeed;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) dx += playerSpeed;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) dy += playerSpeed;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) dy -= playerSpeed;
        normalizeAndMove(dx, dy);
    }

    private void normalizeAndMove(float dx, float dy) {
        if (dx != 0 || dy != 0) {
            float length = (float) Math.sqrt(dx * dx + dy * dy);
            dx /= length;
            dy /= length;
        }
        bucket.x += (int) (dx * playerSpeed);
        bucket.y += (int) (dy * playerSpeed);
        restrictWithinScreenBounds();
    }

    private void restrictWithinScreenBounds() {
        bucket.x = Math.max(0, Math.min(Constants.SCREEN_WIDTH - bucket.width, bucket.x));
        bucket.y = Math.max(0, Math.min(Constants.SCREEN_HEIGHT - bucket.height, bucket.y));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public Rectangle getBucket() {
        return bucket;
    }

    public void setBucket(Rectangle bucket) {
        this.bucket = bucket;
    }

    public float getPlayerSpeed() {
        return playerSpeed;
    }

    public void setPlayerSpeed(float playerSpeed) {
        this.playerSpeed = playerSpeed;
    }
}
