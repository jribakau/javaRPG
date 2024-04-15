package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Entity {
    protected String name;
    protected int level;
    protected int health;
    protected float velocity;
    protected float acceleration;
    protected float maxVelocity;
    protected Rectangle position;

    // DEBUG
    private Rectangle interactionRange;
    private Rectangle collisionRange;
    private final ShapeRenderer interactionShape;
    private final ShapeRenderer collisionShape;

    public Entity(String name, int level, int health, Rectangle position) {
        this.name = name;
        this.level = level;
        this.health = health;
        this.position = position;
        this.velocity = 0;
        this.acceleration = 1;
        this.maxVelocity = 5;

        this.interactionRange = new Rectangle(position.x - 50, position.y - 50, position.width + 100, position.height + 100);
        this.collisionRange = new Rectangle(position.x - 10, position.y - 10, position.width + 20, position.height + 20);

        interactionShape = new ShapeRenderer();
        collisionShape = new ShapeRenderer();
    }

    public void updatePosition(float x, float y) {
        this.position.x += x * velocity;
        this.position.y += y * velocity;
        updateInteractionRectangle();
        updateCollisionRectangle();
    }

    public void updateVelocity() {
        if (this.velocity < maxVelocity) {
            this.velocity += acceleration;
        }
    }

    public void updateInteractionRectangle() {
        interactionRange.x = position.x - 50;
        interactionRange.y = position.y - 50;
    }

    public void updateCollisionRectangle() {
        collisionRange.x = position.x - 10;
        collisionRange.y = position.y - 10;
    }

    public void drawDebug() {
        getInteractionShape().begin(ShapeRenderer.ShapeType.Line);
        getInteractionShape().setColor(Color.GREEN);
        Rectangle interactionRectangle = getInteractionRange();
        getInteractionShape().rect(interactionRectangle.x, interactionRectangle.y, interactionRectangle.width, interactionRectangle.height);
        getInteractionShape().end();
        getCollisionShape().begin(ShapeRenderer.ShapeType.Line);
        getCollisionShape().setColor(Color.RED);
        Rectangle collisionRectangle = getCollisionRange();
        getCollisionShape().rect(collisionRectangle.x, collisionRectangle.y, collisionRectangle.width, collisionRectangle.height);
        getCollisionShape().end();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Rectangle getPosition() {
        return position;
    }

    public void setPosition(Rectangle position) {
        this.position = position;
    }

    public Rectangle getInteractionRange() {
        return interactionRange;
    }

    public void setInteractionRange(Rectangle interactionRange) {
        this.interactionRange = interactionRange;
    }

    public Rectangle getCollisionRange() {
        return collisionRange;
    }

    public void setCollisionRange(Rectangle collisionRange) {
        this.collisionRange = collisionRange;
    }

    public ShapeRenderer getInteractionShape() {
        return interactionShape;
    }

    public ShapeRenderer getCollisionShape() {
        return collisionShape;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }
}