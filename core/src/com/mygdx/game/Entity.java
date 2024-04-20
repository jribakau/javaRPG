package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Entity {
    private float velocity;
    private float acceleration;
    private float maxVelocity;
    private Rectangle position;

    public Entity(Rectangle position) {
        this.position = position;
        this.velocity = 0;
        this.acceleration = 1;
        this.maxVelocity = 5;
    }

    public void updatePosition(float x, float y) {
        this.setX(this.getX() + x * getVelocity());
        this.setY(this.getY() + y * getVelocity());
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

    public void renderCollisionBox(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
        shapeRenderer.end();
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