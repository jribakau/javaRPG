package com.mygdx.game.screens;

/**
 * @deprecated This class is obsolete and part of the old architecture.
 *
 * The game now uses a modern state management system instead of LibGDX Screen classes.
 *
 * For gameplay, use:
 * - {@link com.mygdx.game.state.PlayingState} - Main gameplay with modern UI
 * - {@link com.mygdx.game.state.MenuState} - Main menu
 * - {@link com.mygdx.game.state.PauseState} - Pause menu overlay
 * - {@link com.mygdx.game.state.InventoryState} - Inventory screen overlay
 *
 * The state system is managed by {@link com.mygdx.game.state.GameStateManager}
 * and initialized in {@link com.mygdx.game.RPG#create()}
 *
 * Benefits of the new system:
 * - Clean state transitions
 * - Overlay support (pause/inventory over gameplay)
 * - Modern UI system with {@link com.mygdx.game.ui.UIService}
 * - Professional architecture following industry best practices
 *
 * See ARCHITECTURE.md for full documentation.
 */
@Deprecated
public class GameScreen {
    private GameScreen() {
        throw new UnsupportedOperationException(
            "GameScreen is deprecated. Use the state management system instead. " +
            "See com.mygdx.game.state.PlayingState for the modern implementation."
        );
    }
}
