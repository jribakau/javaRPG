package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.enums.EntityTypeEnum;
import com.mygdx.game.utils.Constants;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player extends Entity {
    public Player(float x, float y, Texture texture) {
        super(new Rectangle(x, y, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT));
        this.setAcceleration(0.1f);
        this.setIsVisible(true);
        this.setTexture(texture);
        this.setName("Player");
        this.setEntityTypeEnum(EntityTypeEnum.PLAYER);
        this.setInteractionRange(50);
        this.setCollisionRange(10);
        this.setVelocity(0);
        this.setAcceleration(1);
        this.setMaxVelocity(5);
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

    @Override
    public void update() {

    }

    public void move(float dx, float dy) {
        calculateMovement(dx, dy);
    }

    public void stop() {
        setVelocity(0);
    }
}
