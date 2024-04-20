package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;

public class PC extends Entity {
    private static final int width = 64;
    private static final int height = 64;

    public PC(float x, float y) {
        super(new Rectangle(x, y, width, height));
        this.setAcceleration(0.1f);
    }

    public void processInput() {
        float dx = 0, dy = 0;
        boolean isMoving = false;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            dx -= this.getAcceleration();
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            dx += this.getAcceleration();
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            dy += this.getAcceleration();
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            dy -= this.getAcceleration();
            isMoving = true;
        }
        if (!isMoving) {
            setVelocity(0);
        }
        move(dx, dy);
    }

    private void move(float dx, float dy) {
        float length = Utils.normalize(dx, dy);
        if (length > 0) {
            dx = dx / length;
            dy = dy / length;
        }
        updatePosition(dx, dy);
        if (dx != 0 || dy != 0) {
            if (getVelocity() < getMaxVelocity()) {
                setVelocity(getVelocity() + getAcceleration());
            }
        }
    }
}
