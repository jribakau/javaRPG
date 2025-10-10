package com.mygdx.game.entity.components;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entity.Component;
import lombok.Getter;
import lombok.Setter;

/**
 * MovementComponent - Handles entity movement and velocity
 */
@Getter
@Setter
public class MovementComponent extends Component {
    private Vector2 velocity;
    private float speed;
    private float maxSpeed;
    private boolean canMove;

    public MovementComponent(float speed) {
        this.velocity = new Vector2(0, 0);
        this.speed = speed;
        this.maxSpeed = speed;
        this.canMove = true;
    }

    @Override
    public void update(float delta) {
        if (!canMove) return;

        PositionComponent position = entity.getComponent(PositionComponent.class);
        if (position != null) {
            // Apply velocity to position
            position.setX(position.getX() + velocity.x * delta);
            position.setY(position.getY() + velocity.y * delta);

            // Apply friction/damping
            velocity.scl(0.9f);
        }
    }

    public void moveInDirection(float dirX, float dirY) {
        if (!canMove) return;

        velocity.set(dirX, dirY).nor().scl(speed);
    }

    public void stop() {
        velocity.set(0, 0);
    }
}

