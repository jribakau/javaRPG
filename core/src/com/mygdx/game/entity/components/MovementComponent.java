package com.mygdx.game.entity.components;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entity.Component;
import lombok.Getter;
import lombok.Setter;

/**
 * MovementComponent - Stores movement data for an entity
 * Movement logic is handled by MovementSystem
 */
@Getter
@Setter
public class MovementComponent extends Component {
    private Vector2 velocity;
    private float speed;
    private float maxSpeed;
    private boolean canMove;
    private float friction;

    public MovementComponent(float speed) {
        this.velocity = new Vector2(0, 0);
        this.speed = speed;
        this.maxSpeed = speed;
        this.canMove = true;
        this.friction = 5.0f; // Default friction value
    }

    public void moveInDirection(float dirX, float dirY) {
        if (!canMove) return;

        velocity.set(dirX, dirY).nor().scl(speed);
    }

    public void stop() {
        velocity.set(0, 0);
    }
}