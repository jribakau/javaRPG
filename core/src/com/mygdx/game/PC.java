package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;

public class PC extends Entity {
    private int stamina;
    private int experience;

    public PC(String name, int screenWidth, int screenHeight, int bucketWidth, int bucketHeight) {
        super(name, 1, 100, new Rectangle((float) screenWidth / 2 - (float) bucketWidth / 2, 20, bucketWidth, bucketHeight));
        this.stamina = 100;
        this.experience = 0;
        this.position = new Rectangle((float) screenWidth / 2 - (float) bucketWidth / 2, (float) screenHeight / 2 - (float) bucketHeight / 2, bucketWidth, bucketHeight);
        this.acceleration = 0.1f;
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
        boolean isMoving = false;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            dx -= acceleration;
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            dx += acceleration;
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            dy += acceleration;
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            dy -= acceleration;
            isMoving = true;
        }
        if (!isMoving) {
            this.velocity = 0;
        }
        normalizeAndMove(dx, dy);
    }

    private void normalizeAndMove(float dx, float dy) {
        float length = (float) Math.sqrt(dx * dx + dy * dy);
        if (length > 0) {
            dx = dx / length;
            dy = dy / length;
        }
        updatePosition(dx, dy);
        if (dx != 0 || dy != 0) {
            updateVelocity();
        }
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
}
