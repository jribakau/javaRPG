package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.utils.Utils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Entity {
    private float velocity;
    private float acceleration;
    private float maxVelocity;
    private float collisionRange;
    private float interactionRange;
    private Rectangle position;
    private final Rectangle collisionBox;
    private final Rectangle interactionBox;
    private final String name;

    public Entity(Rectangle position) {
        this.position = position;
        this.velocity = 0;
        this.acceleration = 1;
        this.maxVelocity = 5;
        this.interactionRange = 50;
        this.collisionRange = 10;
        this.collisionBox = new Rectangle();
        this.interactionBox = new Rectangle();
        this.name = "Entity";
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

    public void move(float dx, float dy) {
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

    public void renderCollisionBox(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(getX() - getCollisionRange(), getY() - getCollisionRange(), getWidth() + getCollisionRange() * 2, getHeight() + getCollisionRange() * 2);
        shapeRenderer.end();
    }

    public void renderInteractionBox(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(getX() - getInteractionRange(), getY() - getInteractionRange(), getWidth() + getInteractionRange() * 2, getHeight() + getInteractionRange() * 2);
        shapeRenderer.end();
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
}