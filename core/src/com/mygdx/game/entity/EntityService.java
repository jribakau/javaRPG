package com.mygdx.game.entity;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import lombok.Getter;

/**
 * EntityService - Manages all entities in the game
 * OPTIMIZED: Component query caching, dirty flag system, optional pooling, spatial partitioning
 */
public class EntityService {
    private final Array<Entity> entities;
    private final Array<Entity> entitiesToAdd;
    private final Array<Entity> entitiesToRemove;

    // Component query cache - reuse arrays instead of allocating every frame
    private final ObjectMap<Class<? extends Component>, Array<Entity>> componentCache;
    private boolean cacheDirty = true;

    // Entity pooling (optional)
    private final EntityPool entityPool;
    private final boolean poolingEnabled;

    // Spatial partitioning for fast proximity queries
    private final SpatialGrid spatialGrid;
    /**
     * -- GETTER --
     *  Check if spatial grid is enabled
     */
    @Getter
    private boolean spatialGridEnabled;

    public EntityService() {
        this(false, false); // Pooling and spatial grid disabled by default
    }

    public EntityService(boolean enablePooling) {
        this(enablePooling, false);
    }

    public EntityService(boolean enablePooling, boolean enableSpatialGrid) {
        this.entities = new Array<>();
        this.entitiesToAdd = new Array<>();
        this.entitiesToRemove = new Array<>();
        this.componentCache = new ObjectMap<>();
        this.poolingEnabled = enablePooling;
        this.entityPool = enablePooling ? new EntityPool() : null;
        this.spatialGridEnabled = enableSpatialGrid;
        this.spatialGrid = enableSpatialGrid ? new SpatialGrid(64) : null; // 64px cell size
    }

    /**
     * Add an entity to the game world
     */
    public void addEntity(Entity entity) {
        entitiesToAdd.add(entity);
        cacheDirty = true; // Mark cache as needing rebuild
    }

    /**
     * Remove an entity from the game world
     */
    public void removeEntity(Entity entity) {
        entitiesToRemove.add(entity);
        cacheDirty = true; // Mark cache as needing rebuild
    }

    /**
     * Obtain a new entity (from pool if enabled, or create new)
     */
    public Entity createEntity() {
        if (poolingEnabled && entityPool != null) {
            return entityPool.obtain();
        }
        return new Entity();
    }

    /**
     * Update all entities
     */
    public void update(float delta) {
        // Process additions and removals
        processEntityChanges();

        // Rebuild spatial grid if enabled
        if (spatialGridEnabled && spatialGrid != null) {
            updateSpatialGrid();
        }

        // Update all active entities
        for (Entity entity : entities) {
            if (entity.isActive()) {
                entity.update(delta);
            }
        }
    }

    /**
     * Get entities with a specific component (CACHED - no allocation!)
     * This is now extremely fast and creates zero garbage
     */
    public <T extends Component> Array<Entity> getEntitiesWithComponent(Class<T> componentClass) {
        // Rebuild cache if entities changed
        if (cacheDirty) {
            rebuildComponentCache();
        }

        // Return cached array (or empty array if none found)
        Array<Entity> cached = componentCache.get(componentClass);
        if (cached == null) {
            // Cache miss - build and cache it
            cached = new Array<>();
            for (Entity entity : entities) {
                if (entity.hasComponent(componentClass) && entity.isActive()) {
                    cached.add(entity);
                }
            }
            componentCache.put(componentClass, cached);
        }

        return cached;
    }

    /**
     * Get entities near a point (requires spatial grid enabled)
     * FAST: O(1) instead of O(n) - only checks nearby entities
     */
    public Array<Entity> getEntitiesNear(float x, float y, float radius) {
        if (spatialGridEnabled && spatialGrid != null) {
            return spatialGrid.getNearby(x, y, radius);
        }

        // Fallback: brute force (slow)
        Array<Entity> result = new Array<>();
        float radiusSquared = radius * radius;

        for (Entity entity : entities) {
            if (!entity.isActive()) continue;

            com.mygdx.game.entity.components.PositionComponent pos =
                entity.getComponent(com.mygdx.game.entity.components.PositionComponent.class);
            if (pos == null) continue;

            float dx = pos.getX() - x;
            float dy = pos.getY() - y;
            float distSquared = dx * dx + dy * dy;

            if (distSquared <= radiusSquared) {
                result.add(entity);
            }
        }

        return result;
    }

    /**
     * Get entities in a rectangular area (requires spatial grid enabled)
     */
    public Array<Entity> getEntitiesInBounds(Rectangle bounds) {
        if (spatialGridEnabled && spatialGrid != null) {
            return spatialGrid.getInBounds(bounds);
        }

        // Fallback: brute force
        Array<Entity> result = new Array<>();
        for (Entity entity : entities) {
            if (!entity.isActive()) continue;

            com.mygdx.game.entity.components.PositionComponent pos =
                entity.getComponent(com.mygdx.game.entity.components.PositionComponent.class);
            if (pos == null) continue;

            if (bounds.contains(pos.getX(), pos.getY())) {
                result.add(entity);
            }
        }

        return result;
    }

    /**
     * Clear all entities
     */
    public void clear() {
        // Return entities to pool if pooling is enabled
        if (poolingEnabled && entityPool != null) {
            entityPool.freeAll(entities);
        }

        entities.clear();
        entitiesToAdd.clear();
        entitiesToRemove.clear();
        componentCache.clear();
        cacheDirty = true;
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

        // Remove old entities and return to pool
        if (entitiesToRemove.size > 0) {
            // Return to pool if enabled
            if (poolingEnabled && entityPool != null) {
                entityPool.freeAll(entitiesToRemove);
            }

            entities.removeAll(entitiesToRemove, true);
            entitiesToRemove.clear();
        }
    }

    /**
     * Rebuild component cache when entities change
     */
    private void rebuildComponentCache() {
        // Clear all cached arrays
        for (Array<Entity> cache : componentCache.values()) {
            cache.clear();
        }

        // Rebuild each cached component type
        for (Class<? extends Component> componentClass : componentCache.keys()) {
            Array<Entity> cache = componentCache.get(componentClass);
            for (Entity entity : entities) {
                if (entity.hasComponent(componentClass) && entity.isActive()) {
                    cache.add(entity);
                }
            }
        }

        cacheDirty = false;
    }

    /**
     * Mark cache as dirty (call this if entity components change at runtime)
     */
    public void invalidateCache() {
        cacheDirty = true;
    }

    public int getEntityCount() {
        return entities.size;
    }

    /**
     * Get all entities (use sparingly - prefer getEntitiesWithComponent)
     */
    public Array<Entity> getAllEntities() {
        return entities;
    }

    /**
     * Get spatial grid statistics (for debugging)
     */
    public String getSpatialGridStats() {
        if (spatialGrid != null) {
            return spatialGrid.getStats();
        }
        return "Spatial grid not enabled";
    }

    /**
     * Update the spatial grid with current entity positions
     */
    private void updateSpatialGrid() {
        spatialGrid.clear();
        for (Entity entity : entities) {
            if (entity.isActive()) {
                spatialGrid.insert(entity);
            }
        }
    }
}
