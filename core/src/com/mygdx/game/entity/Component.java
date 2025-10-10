package com.mygdx.game.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Component - Base class for all entity components
 * Components hold data and optionally behavior
 */
@Setter
@Getter
public abstract class Component {
    /**
     * -- GETTER --
     *  Get the entity this component belongs to
     * -- SETTER --
     *  Set the entity this component belongs to

     */
    protected Entity entity;

    /**
     * Update this component (override if needed)
     */
    public void update(float delta) {
        // Default: do nothing
    }
}

