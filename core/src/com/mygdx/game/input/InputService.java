package com.mygdx.game.input;

import com.badlogic.gdx.Gdx;
import lombok.Getter;
import lombok.Setter;

/**
 * InputService - Central input management system
 * Abstracts input handling to support multiple control schemes
 * Allows for easy key rebinding and input scheme switching
 */
@Getter
@Setter
public class InputService {
    private final InputBinding bindings;
    private InputScheme currentScheme;

    // Input state cache (updated each frame)
    private float moveX;
    private float moveY;

    public InputService() {
        this.bindings = new InputBinding();
        this.currentScheme = InputScheme.KEYBOARD;
    }

    /**
     * Update input state (call this once per frame)
     */
    public void update() {
        updateMovementInput();
    }

    /**
     * Check if an action is currently pressed (continuous)
     */
    public boolean isActionPressed(InputAction action) {
        return switch (currentScheme) {
            case KEYBOARD -> isKeyboardActionPressed(action);
            case GAMEPAD ->
                // TODO: Implement gamepad support (requires gdx-controllers dependency)
                    false;
            case TOUCH -> isTouchActionPressed(action);
        };
    }

    /**
     * Check if an action was just pressed this frame (single press)
     */
    public boolean isActionJustPressed(InputAction action) {
        return switch (currentScheme) {
            case KEYBOARD -> isKeyboardActionJustPressed(action);
            case GAMEPAD ->
                // TODO: Implement gamepad support (requires gdx-controllers dependency)
                    false;
            case TOUCH -> isTouchActionJustPressed(action);
        };
    }

    // ==================== KEYBOARD INPUT ====================

    private boolean isKeyboardActionPressed(InputAction action) {
        Integer keyCode = bindings.getKeyBinding(action);
        return keyCode != null && Gdx.input.isKeyPressed(keyCode);
    }

    private boolean isKeyboardActionJustPressed(InputAction action) {
        Integer keyCode = bindings.getKeyBinding(action);
        return keyCode != null && Gdx.input.isKeyJustPressed(keyCode);
    }

    private void updateKeyboardMovement() {
        moveX = 0;
        moveY = 0;

        if (isKeyboardActionPressed(InputAction.MOVE_UP)) moveY += 1;
        if (isKeyboardActionPressed(InputAction.MOVE_DOWN)) moveY -= 1;
        if (isKeyboardActionPressed(InputAction.MOVE_LEFT)) moveX -= 1;
        if (isKeyboardActionPressed(InputAction.MOVE_RIGHT)) moveX += 1;

        // Normalize diagonal movement
        if (moveX != 0 && moveY != 0) {
            float length = (float) Math.sqrt(moveX * moveX + moveY * moveY);
            moveX /= length;
            moveY /= length;
        }
    }

    // ==================== GAMEPAD INPUT ====================
    // TODO: Implement when gdx-controllers dependency is added

    private void updateGamepadMovement() {
        moveX = 0;
        moveY = 0;
        // TODO: Read from gamepad analog stick
    }

    // ==================== TOUCH INPUT ====================

    private boolean isTouchActionPressed(InputAction action) {
        // TODO: Implement touch/virtual button support
        return false;
    }

    private boolean isTouchActionJustPressed(InputAction action) {
        // TODO: Implement touch/virtual button support
        return false;
    }

    private void updateTouchMovement() {
        // TODO: Implement virtual joystick for mobile
        moveX = 0;
        moveY = 0;
    }

    // ==================== HELPER METHODS ====================

    private void updateMovementInput() {
        switch (currentScheme) {
            case KEYBOARD:
                updateKeyboardMovement();
                break;
            case GAMEPAD:
                updateGamepadMovement();
                break;
            case TOUCH:
                updateTouchMovement();
                break;
        }
    }

    /**
     * Switch input scheme manually
     */
    public void setInputScheme(InputScheme scheme) {
        this.currentScheme = scheme;
    }
}
