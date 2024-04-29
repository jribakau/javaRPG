package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.enums.EntityType;
import com.mygdx.game.utils.Constants;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NPC extends Entity {
    public NPC(float x, float y, Texture texture) {
        super(new Rectangle(x, y, Constants.NPC_WIDTH, Constants.NPC_HEIGHT));
        this.setWidth(Constants.NPC_WIDTH);
        this.setHeight(Constants.NPC_HEIGHT);
        this.setTexture(texture);
        this.setName("NPC");
        this.setEntityType(EntityType.NPC);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(getTexture(), getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void drawDebug() {
        if (getIsVisible()) {
            renderCollisionBox();
            renderInteractionBox();
        }
    }
}
