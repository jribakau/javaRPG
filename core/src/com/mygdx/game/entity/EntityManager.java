package com.mygdx.game.entity;

import com.badlogic.gdx.utils.Array;

/**
 * EntityManager - Manages all entities in the game
 * Single source of truth for all active entities
 */
public class EntityManager {
    private final Array<Entity> entities;
    private final Array<Entity> entitiesToAdd;
    private final Array<Entity> entitiesToRemove;

    public EntityManager() {
        this.entities = new Array<>();
        this.entitiesToAdd = new Array<>();
        this.entitiesToRemove = new Array<>();
    }

    /**
     * Add an entity to the game world
     */
    public void addEntity(Entity entity) {
        entitiesToAdd.add(entity);
    }

    /**
     * Remove an entity from the game world
     */
    public void removeEntity(Entity entity) {
        entitiesToRemove.add(entity);
    }

    /**
     * Update all entities
     */
    public void update(float delta) {
        // Process additions and removals
        processEntityChanges();

        // Update all active entities
        for (Entity entity : entities) {
            if (entity.isActive()) {
                entity.update(delta);
            }
        }
    }

    /**
     * Get all entities
     */
    public Array<Entity> getEntities() {
        return entities;
    }

    /**
     * Get entities with a specific component
     */
    public <T extends Component> Array<Entity> getEntitiesWithComponent(Class<T> componentClass) {
        Array<Entity> result = new Array<>();
        for (Entity entity : entities) {
            if (entity.hasComponent(componentClass) && entity.isActive()) {
                result.add(entity);
            }
        }
        return result;
    }

    /**
     * Get the first entity with a specific component
     */
    public <T extends Component> Entity getFirstEntityWithComponent(Class<T> componentClass) {
        for (Entity entity : entities) {
            if (entity.hasComponent(componentClass) && entity.isActive()) {
                return entity;
            }
        }
        return null;
    }

    /**
     * Clear all entities
     */
    public void clear() {
        entities.clear();
        entitiesToAdd.clear();
        entitiesToRemove.clear();
    }

    /**
     * Process pending additions and removals
     */
    private void processEntityChanges() {
        // Add new entities
        if (entitiesToAdd.size > 0) {
            entities.addAll(entitiesToAdd);
            entitiesToAdd.clear();
        }

        // Remove old entities
        if (entitiesToRemove.size > 0) {
            entities.removeAll(entitiesToRemove, true);
            entitiesToRemove.clear();
        }
    }

    public int getEntityCount() {
        return entities.size;
    }
}

