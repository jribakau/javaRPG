package com.mygdx.game.entity;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity - Represents any game object (player, NPC, item, etc.)
 * Uses Component-based design for maximum flexibility
 */
@Getter
@Setter
public class Entity {
    private static int nextId = 0;

    private final int id;
    private final ObjectMap<Class<? extends Component>, Component> components;
    private boolean active;

    public Entity() {
        this.id = nextId++;
        this.components = new ObjectMap<>();
        this.active = true;
    }

    /**
     * Add a component to this entity
     */
    public <T extends Component> Entity addComponent(T component) {
        components.put(component.getClass(), component);
        component.setEntity(this);
        return this;
    }

    /**
     * Get a component of specific type
     */
    @SuppressWarnings("unchecked")
    public <T extends Component> T getComponent(Class<T> componentClass) {
        return (T) components.get(componentClass);
    }

    /**
     * Check if entity has a specific component
     */
    public <T extends Component> boolean hasComponent(Class<T> componentClass) {
        return components.containsKey(componentClass);
    }

    /**
     * Remove a component from this entity
     */
    public <T extends Component> void removeComponent(Class<T> componentClass) {
        components.remove(componentClass);
    }

    /**
     * Get all components
     */
    public Array<Component> getAllComponents() {
        return components.values().toArray();
    }

    /**
     * Update all components
     */
    public void update(float delta) {
        if (!active) return;

        for (Component component : components.values()) {
            component.update(delta);
        }
    }
}