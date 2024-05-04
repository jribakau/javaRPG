package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.enums.EntityTypeEnum;
import com.mygdx.game.utils.Constants;

public class Tile extends Entity {
    public Tile(float x, float y, Texture texture) {
        super(new Rectangle(x, y, Constants.TILE_WIDTH, Constants.TILE_HEIGHT));
        this.setEntityTypeEnum(EntityTypeEnum.TILE);
        this.setTexture(texture);
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
