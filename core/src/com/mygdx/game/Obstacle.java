package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;

public class Obstacle {
    private final Rectangle bounds;

    public Obstacle(int x, int y, int width, int height) {
        this.bounds = new Rectangle(x, y, width, height);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean collidesWith(Rectangle playerBounds) {
        return bounds.overlaps(playerBounds);
    }
}
