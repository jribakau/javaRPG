package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.enums.EntityType;
import com.mygdx.game.utils.Constants;
import com.mygdx.game.utils.Files;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player extends Entity {
    public Player(float x, float y) {
        super(new Rectangle(x, y, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT));
        this.setAcceleration(0.1f);
        this.setIsVisible(true);
        this.setWidth(Constants.PLAYER_WIDTH);
        this.setHeight(Constants.PLAYER_HEIGHT);
        this.setTexture(new Texture(Files.BUCKET_IMG_PATH));
        this.setName("Player");
        this.setEntityType(EntityType.PLAYER);
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
