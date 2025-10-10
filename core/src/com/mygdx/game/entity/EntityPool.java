package com.mygdx.game.entity;

import com.badlogic.gdx.utils.Pool;

/**
 * EntityPool - Reuses Entity objects to avoid garbage collection
 * PERFORMANCE: Eliminates allocation/deallocation overhead
 */
public class EntityPool extends Pool<Entity> {

    public EntityPool() {
        super(64, 1000); // Initial size 64, max 1000 entities
    }

    public EntityPool(int initialCapacity, int max) {
        super(initialCapacity, max);
    }

    @Override
    protected Entity newObject() {
        return new Entity();
    }

    /**
     * Obtain an entity from the pool (reuses if available)
     */
    @Override
    public Entity obtain() {
        Entity entity = super.obtain();
        entity.setActive(true);
        return entity;
    }

    /**
     * Return an entity to the pool for reuse
     */
    @Override
    public void free(Entity entity) {
        if (entity == null) return;

        // Clean up the entity before returning to pool
        entity.setActive(false);
        entity.getAllComponents().clear();

        super.free(entity);
    }

    /**
     * Free multiple entities at once
     */
    public void freeAll(com.badlogic.gdx.utils.Array<Entity> entities) {
        for (Entity entity : entities) {
            free(entity);
        }
    }
}

