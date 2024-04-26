package com.mygdx.game.entities;

import com.badlogic.gdx.math.Rectangle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player extends Entity {
    private static final int width = 64;
    private static final int height = 64;

    public Player(float x, float y) {
        super(new Rectangle(x, y, width, height));
        this.setAcceleration(0.1f);
    }
}
