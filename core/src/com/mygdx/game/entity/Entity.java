package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.enums.EntityTypeEnum;
import com.mygdx.game.utils.Utils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Entity {
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

    public Entity(Rectangle position) {
        this.position = position;
        this.collisionBox = new Rectangle();
        this.interactionBox = new Rectangle();
        this.shapeRenderer = new ShapeRenderer();
        updateBoxes();
    }

    private void updateBoxes() {
        collisionBox.set(getX() - getCollisionRange(), getY() - getCollisionRange(), getWidth() + getCollisionRange() * 2, getHeight() + getCollisionRange() * 2);
        interactionBox.set(getX() - getInteractionRange(), getY() - getInteractionRange(), getWidth() + getInteractionRange() * 2, getHeight() + getInteractionRange() * 2);
    }

    public void updatePosition(float x, float y) {
        this.setX(this.getX() + x * getVelocity());
        this.setY(this.getY() + y * getVelocity());
        updateBoxes();
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

    public void renderCollisionBox() {
        getShapeRenderer().begin(ShapeRenderer.ShapeType.Line);
        getShapeRenderer().setColor(Color.RED);
        getShapeRenderer().rect(getX() - getCollisionRange(), getY() - getCollisionRange(), getWidth() + getCollisionRange() * 2, getHeight() + getCollisionRange() * 2);
        getShapeRenderer().end();
    }

    public void renderInteractionBox() {
        getShapeRenderer().begin(ShapeRenderer.ShapeType.Line);
        getShapeRenderer().setColor(Color.GREEN);
        getShapeRenderer().rect(getX() - getInteractionRange(), getY() - getInteractionRange(), getWidth() + getInteractionRange() * 2, getHeight() + getInteractionRange() * 2);
        getShapeRenderer().end();
    }

    public float getX() {
        return position.getX();
    }

    public float getY() {
        return position.getY();
    }

    public void setX(float x) {
        position.setX(x);
    }

    public void setY(float y) {
        position.setY(y);
    }

    public float getWidth() {
        return position.getWidth();
    }

    public float getHeight() {
        return position.getHeight();
    }

    public abstract void draw(SpriteBatch batch);

    public abstract void drawDebug();

    public void dispose() {
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
        if (texture != null) {
            texture.dispose();
        }
    }
}