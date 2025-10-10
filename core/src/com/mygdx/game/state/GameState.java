package com.mygdx.game.state;

import com.badlogic.gdx.InputProcessor;

/**
 * GameState - Base class for all game states
 * Represents a distinct mode/screen of the game (Menu, Playing, Paused, etc.)
 * <p>
 * Each state has its own:
 * - Update logic
 * - Render logic
 * - Input handling
 * - Lifecycle (enter, exit, pause, resume)
 */
public abstract class GameState {
    protected GameStateManager stateManager;
    protected boolean initialized;

    public GameState(GameStateManager stateManager) {
        this.stateManager = stateManager;
        this.initialized = false;
    }

    /**
     * Initialize the state (called once when first created)
     */
    public void initialize() {
        if (!initialized) {
            onCreate();
            initialized = true;
        }
    }

    /**
     * Called when state is created (override for setup logic)
     */
    protected abstract void onCreate();

    /**
     * Called when this state becomes active
     */
    public abstract void onEnter();

    /**
     * Called when this state becomes inactive
     */
    public abstract void onExit();

    /**
     * Called when state is paused (another state pushed on top)
     */
    public void onPause() {
        // Default: do nothing
    }

    /**
     * Called when state is resumed (top state was popped)
     */
    public void onResume() {
        // Default: do nothing
    }

    /**
     * Update state logic
     *
     * @param delta Time since last frame in seconds
     */
    public abstract void update(float delta);

    /**
     * Render the state
     *
     * @param delta Time since last frame in seconds
     */
    public abstract void render(float delta);

    /**
     * Handle window resize
     */
    public void resize(int width, int height) {
        // Default: do nothing
    }

    /**
     * Get input processor for this state (optional)
     *
     * @return InputProcessor or null if state doesn't need input
     */
    public InputProcessor getInputProcessor() {
        return null;
    }

    /**
     * Dispose resources
     */
    public void dispose() {
        // Default: do nothing
    }

    /**
     * Check if this state should block updates to states below it
     *
     * @return true if lower states should not update
     */
    public boolean blocksUpdate() {
        return true;
    }

    /**
     * Check if this state should block rendering of states below it
     *
     * @return true if lower states should not render
     */
    public boolean blocksRender() {
        return true;
    }
}