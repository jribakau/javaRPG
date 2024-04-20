package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Entity {
    private float velocity;
    private float acceleration;
    private float maxVelocity;
    private Rectangle position;

    // DEBUG
    private final ShapeRenderer interactionShape;
    private final ShapeRenderer collisionShape;

    public Entity(Rectangle position) {
        this.position = position;
        this.velocity = 0;
        this.acceleration = 1;
        this.maxVelocity = 5;

        interactionShape = new ShapeRenderer();
        collisionShape = new ShapeRenderer();
    }

    public void updatePosition(float x, float y) {
        setX(getX() + x * getVelocity());
        setY(getY() + y * getVelocity());
    }

    public void drawDebug() {
        float interactionRange = 150;
        float collisionRange = 75;

        float interactionX = getX() + getWidth() / 2 - interactionRange / 2;
        float interactionY = getY() + getHeight() / 2 - interactionRange / 2;

        float collisionX = getX() + getWidth() / 2 - collisionRange / 2;
        float collisionY = getY() + getHeight() / 2 - collisionRange / 2;

        this.interactionShape.begin(ShapeRenderer.ShapeType.Line);
        this.interactionShape.setColor(Color.GREEN);
        this.interactionShape.rect(interactionX, interactionY, interactionRange, interactionRange);
        this.interactionShape.end();

        this.collisionShape.begin(ShapeRenderer.ShapeType.Line);
        this.collisionShape.setColor(Color.RED);
        this.collisionShape.rect(collisionX, collisionY, collisionRange, collisionRange);
        this.collisionShape.end();
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public float getMaxVelocity() {
        return maxVelocity;
    }

    public void setMaxVelocity(float maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

    public Rectangle getPosition() {
        return position;
    }

    public void setPosition(Rectangle position) {
        this.position = position;
    }

    public ShapeRenderer getInteractionShape() {
        return interactionShape;
    }

    public ShapeRenderer getCollisionShape() {
        return collisionShape;
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