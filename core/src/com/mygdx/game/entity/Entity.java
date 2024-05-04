package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.aiManager.EntityBehavior;
import com.mygdx.game.enums.EntityTypeEnum;
import com.mygdx.game.utils.Utils;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public abstract class Entity {
    private UUID id;
    private float velocity;
    private float acceleration;
    private float maxVelocity;
    private float collisionRange;
    private float interactionRange;
    private Rectangle position;
    private final Rectangle collisionBox;
    private final Rectangle interactionBox;
    private String name;
    private Texture texture;
    private ShapeRenderer shapeRenderer;
    private Boolean isVisible;
    private EntityTypeEnum entityTypeEnum;
    private EntityBehavior entityBehavior;
    private boolean highlight = false;

    public Entity(Rectangle position) {
        this.id = UUID.randomUUID();
        this.position = position;
        this.collisionBox = new Rectangle();
        this.interactionBox = new Rectangle();
        this.shapeRenderer = new ShapeRenderer();
        updateCollisionBox();
        updateInteractionBox();
    }

    public void updatePosition(float x, float y) {
        position.x = (this.position.x + x * getVelocity());
        position.y = (this.position.y + y * getVelocity());
        updateCollisionBox();
        updateInteractionBox();
    }

    public void calculateMovement(float dx, float dy) {
        float length = Utils.normalize(dx, dy);
        if (length > 0) {
            dx = dx / length;
            dy = dy / length;
        }
        if (dx != 0 || dy != 0) {
            if (getVelocity() < getMaxVelocity()) {
                setVelocity(getVelocity() + getAcceleration());
            }
        }
        updatePosition(dx, dy);
    }

    public boolean collidesWith(Entity other) {
        return collisionBox.overlaps(other.collisionBox);
    }

    public boolean interactsWith(Entity other) {
        return interactionBox.overlaps(other.interactionBox);
    }

    private void updateCollisionBox() {
        collisionBox.set(position.x - collisionRange, position.y - collisionRange, position.width + collisionRange * 2, position.height + collisionRange * 2);
    }

    private void updateInteractionBox() {
        interactionBox.set(position.x - interactionRange, position.y - interactionRange, position.width + interactionRange * 2, position.height + interactionRange * 2);
    }

    public void renderCollisionBox() {
        renderBox(Color.RED, collisionBox);
    }

    public void renderInteractionBox() {
        renderBox(Color.GREEN, interactionBox);
    }

    public void renderHighlight() {
        renderBox(Color.BLUE, position);
    }

    private void renderBox(Color color, Rectangle box) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(color);
        shapeRenderer.rect(box.x, box.y, box.width, box.height);
        shapeRenderer.end();
    }

    public abstract void draw(SpriteBatch batch);

    public abstract void drawDebug();

    public abstract void drawHighlight();

    public void dispose() {
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
        if (texture != null) {
            texture.dispose();
        }
    }

    public boolean containsPoint(float x, float y) {
        return position.contains(x, y);
    }
}