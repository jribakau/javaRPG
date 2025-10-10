package com.mygdx.game.entity;

/**
 * Component - Base class for all entity components
 * Components hold data and optionally behavior
 */
public abstract class Component {
    protected Entity entity;

    /**
     * Set the entity this component belongs to
     */
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    /**
     * Get the entity this component belongs to
     */
    public Entity getEntity() {
        return entity;
    }

    /**
     * Update this component (override if needed)
     */
    public void update(float delta) {
        // Default: do nothing
    }
}

