package com.mygdx.game.entities;

import com.badlogic.gdx.math.Rectangle;

public class NPC extends Entity {
    private static final int width = 64;
    private static final int height = 64;

    public NPC(float x, float y) {
        super(new Rectangle(x, y, width, height));
    }
}
