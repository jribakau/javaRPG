package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.aiManager.RandomWalkBehavior;
import com.mygdx.game.enums.EntityTypeEnum;
import com.mygdx.game.utils.Constants;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Character extends Entity {
    public Character(float x, float y, Texture texture) {
        super(new Rectangle(x, y, Constants.NPC_WIDTH, Constants.NPC_HEIGHT));
        this.setTexture(texture);
        this.setName("Character");
        this.setEntityTypeEnum(EntityTypeEnum.NPC);
        this.setInteractionRange(50);
        this.setCollisionRange(10);
        this.setVelocity(2);
        this.setEntityBehavior(new RandomWalkBehavior(this));
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(getTexture(), getPosition().getX(), getPosition().getY(), getPosition().getWidth(), getPosition().getHeight());
    }

    @Override
    public void drawDebug() {
        if (getIsVisible()) {
            renderCollisionBox();
            renderInteractionBox();

        }
    }

    @Override
    public void drawHighlight() {
        renderHighlight();
    }
}
