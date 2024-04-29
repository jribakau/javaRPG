package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player extends Entity {
    public Player(float x, float y) {
        super(new Rectangle(x, y, 64, 64));
        this.setAcceleration(0.1f);
        this.setIsVisible(true);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(getTexture(), getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void drawDebug() {
        renderCollisionBox();
        renderInteractionBox();
    }

    public void move(float dx, float dy) {
        calculateMovement(dx, dy);
    }

    public void stop() {
        setVelocity(0);
    }
}
