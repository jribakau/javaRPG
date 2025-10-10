package com.mygdx.game.systems;

import com.mygdx.game.entity.EntityService;

/**
 * GameSystem - Base class for all game systems
 * Systems contain logic and operate on entities with specific components
 *
 * Philosophy:
 * - Systems contain LOGIC
 * - Components contain DATA
 * - Entities are containers for components
 */
public abstract class GameSystem {
    protected final EntityService entityService;
    protected boolean enabled;

    public GameSystem(EntityService entityService) {
        this.entityService = entityService;
        this.enabled = true;
    }

    /**
     * Update this system (called every frame)
     * @param delta Time since last frame in seconds
     */
    public abstract void update(float delta);

    /**
     * Enable or disable this system
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Check if this system is enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Initialize system (called once after construction)
     * Override if needed
     */
    public void initialize() {
        // Default: do nothing
    }

    /**
     * Cleanup system resources
     * Override if needed
     */
    public void dispose() {
        // Default: do nothing
    }

    /**
     * Get the priority of this system (lower = earlier execution)
     * Override to change execution order
     */
    public int getPriority() {
        return 0;
    }
}