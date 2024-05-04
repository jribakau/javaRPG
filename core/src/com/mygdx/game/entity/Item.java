package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Item extends Entity {
    public Item(Rectangle position) {
        super(position);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(getTexture(), getPosition().getX(), getPosition().getY(), getPosition().getWidth(), getPosition().getHeight());
    }

    @Override
    public void drawDebug() {
        renderCollisionBox();
        renderInteractionBox();
    }

    @Override
    public void drawHighlight() {
        renderHighlight();
    }
}
