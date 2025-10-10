package com.mygdx.game.entity.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.entity.Component;
import lombok.Getter;
import lombok.Setter;

/**
 * InputComponent - Handles input for player-controlled entities
 */
@Getter
@Setter
public class InputComponent extends Component {
    private boolean enabled;

    // Input state
    private float moveX;
    private float moveY;
    private boolean actionPressed;
    private boolean attackPressed;
    private boolean interactPressed;

    // Key bindings
    private int keyUp = Input.Keys.W;
    private int keyDown = Input.Keys.S;
    private int keyLeft = Input.Keys.A;
    private int keyRight = Input.Keys.D;
    private int keyAttack = Input.Keys.SPACE;
    private int keyInteract = Input.Keys.E;
    private int keyAction = Input.Keys.F;

    public InputComponent() {
        this.enabled = true;
    }

    @Override
    public void update(float delta) {
        if (!enabled) {
            resetInput();
            return;
        }

        // Read movement input
        moveX = 0;
        moveY = 0;

        if (Gdx.input.isKeyPressed(keyUp)) moveY = 1;
        if (Gdx.input.isKeyPressed(keyDown)) moveY = -1;
        if (Gdx.input.isKeyPressed(keyLeft)) moveX = -1;
        if (Gdx.input.isKeyPressed(keyRight)) moveX = 1;

        // Read action inputs
        attackPressed = Gdx.input.isKeyJustPressed(keyAttack);
        interactPressed = Gdx.input.isKeyJustPressed(keyInteract);
        actionPressed = Gdx.input.isKeyJustPressed(keyAction);

        // Apply input to movement component
        applyMovement();
    }

    private void applyMovement() {
        MovementComponent movement = entity.getComponent(MovementComponent.class);
        if (movement != null) {
            if (moveX != 0 || moveY != 0) {
                movement.moveInDirection(moveX, moveY);
            } else {
                movement.stop();
            }
        }
    }

    private void resetInput() {
        moveX = 0;
        moveY = 0;
        actionPressed = false;
        attackPressed = false;
        interactPressed = false;
    }
}

