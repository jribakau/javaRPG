package com.mygdx.game.input;

/**
 * InputAction - Abstract game actions independent of input device
 * Maps logical actions to physical inputs
 */
public enum InputAction {
    // Movement
    MOVE_UP,
    MOVE_DOWN,
    MOVE_LEFT,
    MOVE_RIGHT,

    // Combat
    ATTACK,
    DEFEND,
    SPECIAL_ATTACK,

    // Interaction
    INTERACT,
    USE_ITEM,

    // UI
    INVENTORY,
    MENU,
    PAUSE,
    CONFIRM,
    CANCEL,

    // Other
    SPRINT,
    JUMP,
    CROUCH
}