package com.mygdx.game.entity.components;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entity.Component;
import lombok.Getter;
import lombok.Setter;

/**
 * PositionComponent - Handles entity position and dimensions
 */
@Getter
@Setter
public class PositionComponent extends Component {
    private Vector2 position;
    private float width;
    private float height;

    public PositionComponent(float x, float y, float width, float height) {
        this.position = new Vector2(x, y);
        this.width = width;
        this.height = height;
    }

    public PositionComponent(float x, float y) {
        this(x, y, 32, 32); // Default size
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public void setX(float x) {
        position.x = x;
    }

    public void setY(float y) {
        position.y = y;
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
    }

    public Vector2 getCenter() {
        return new Vector2(position.x + width / 2, position.y + height / 2);
    }
}

