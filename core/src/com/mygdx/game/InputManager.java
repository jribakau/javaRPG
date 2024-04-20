package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class InputManager {
    public void movement(Player player) {
        float dx = 0, dy = 0;
        boolean isMoving = false;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            dx -= player.getAcceleration();
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            dx += player.getAcceleration();
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            dy += player.getAcceleration();
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            dy -= player.getAcceleration();
            isMoving = true;
        }

        if (!isMoving) {
            player.setVelocity(0);
        } else {
            player.move(dx, dy);
        }
    }
}