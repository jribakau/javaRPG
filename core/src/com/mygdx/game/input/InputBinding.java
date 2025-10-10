package com.mygdx.game.input;

import com.badlogic.gdx.Input;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * InputBinding - Maps InputActions to physical keys/buttons
 * Supports rebinding for customizable controls
 */
@Getter
@Setter
public class InputBinding {
    private final Map<InputAction, Integer> keyboardBindings;
    private final Map<InputAction, Integer> gamepadBindings;

    public InputBinding() {
        this.keyboardBindings = new HashMap<>();
        this.gamepadBindings = new HashMap<>();
        loadDefaultKeyboardBindings();
        loadDefaultGamepadBindings();
    }

    /**
     * Load default keyboard bindings (WASD + standard keys)
     */
    private void loadDefaultKeyboardBindings() {
        // Movement
        keyboardBindings.put(InputAction.MOVE_UP, Input.Keys.W);
        keyboardBindings.put(InputAction.MOVE_DOWN, Input.Keys.S);
        keyboardBindings.put(InputAction.MOVE_LEFT, Input.Keys.A);
        keyboardBindings.put(InputAction.MOVE_RIGHT, Input.Keys.D);

        // Combat
        keyboardBindings.put(InputAction.ATTACK, Input.Keys.SPACE);
        keyboardBindings.put(InputAction.DEFEND, Input.Keys.SHIFT_LEFT);
        keyboardBindings.put(InputAction.SPECIAL_ATTACK, Input.Keys.Q);

        // Interaction
        keyboardBindings.put(InputAction.INTERACT, Input.Keys.E);
        keyboardBindings.put(InputAction.USE_ITEM, Input.Keys.F);

        // UI
        keyboardBindings.put(InputAction.INVENTORY, Input.Keys.I);
        keyboardBindings.put(InputAction.MENU, Input.Keys.ESCAPE);
        keyboardBindings.put(InputAction.PAUSE, Input.Keys.P);
        keyboardBindings.put(InputAction.CONFIRM, Input.Keys.ENTER);
        keyboardBindings.put(InputAction.CANCEL, Input.Keys.ESCAPE);

        // Other
        keyboardBindings.put(InputAction.SPRINT, Input.Keys.SHIFT_LEFT);
    }

    /**
     * Load default gamepad bindings (Xbox/PlayStation style)
     */
    private void loadDefaultGamepadBindings() {
        // Movement handled by analog stick (not button-based)

        // Combat
        gamepadBindings.put(InputAction.ATTACK, 0); // A/X button
        gamepadBindings.put(InputAction.DEFEND, 1); // B/Circle button
        gamepadBindings.put(InputAction.SPECIAL_ATTACK, 2); // X/Square button

        // Interaction
        gamepadBindings.put(InputAction.INTERACT, 3); // Y/Triangle button
        gamepadBindings.put(InputAction.USE_ITEM, 4); // Left bumper

        // UI
        gamepadBindings.put(InputAction.INVENTORY, 5); // Right bumper
        gamepadBindings.put(InputAction.MENU, 6); // Start button
        gamepadBindings.put(InputAction.PAUSE, 6); // Start button
    }

    /**
     * Get the key code for a specific action (keyboard)
     */
    public Integer getKeyBinding(InputAction action) {
        return keyboardBindings.get(action);
    }

    /**
     * Get the button code for a specific action (gamepad)
     */
    public Integer getGamepadBinding(InputAction action) {
        return gamepadBindings.get(action);
    }

    /**
     * Rebind a keyboard key to an action
     */
    public void rebindKey(InputAction action, int keyCode) {
        keyboardBindings.put(action, keyCode);
    }

    /**
     * Rebind a gamepad button to an action
     */
    public void rebindGamepadButton(InputAction action, int buttonCode) {
        gamepadBindings.put(action, buttonCode);
    }

    /**
     * Reset all bindings to defaults
     */
    public void resetToDefaults() {
        keyboardBindings.clear();
        gamepadBindings.clear();
        loadDefaultKeyboardBindings();
        loadDefaultGamepadBindings();
    }
}

