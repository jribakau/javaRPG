package com.mygdx.game.entity;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.mygdx.game.entity.components.PositionComponent;

/**
 * SpatialGrid - Spatial partitioning for fast entity lookups
 * PERFORMANCE: O(1) lookup instead of O(n) for nearby entities
 *
 * Divides the world into grid cells. Instead of checking ALL entities
 * for collisions or proximity, only check entities in nearby cells.
 */
public class SpatialGrid {
    private final int cellSize;
    private final ObjectMap<Long, Array<Entity>> grid;
    private final Array<Entity> tempResults;

    public SpatialGrid(int cellSize) {
        this.cellSize = cellSize;
        this.grid = new ObjectMap<>();
        this.tempResults = new Array<>();
    }

    /**
     * Clear the grid (call before rebuilding each frame)
     */
    public void clear() {
        for (Array<Entity> cell : grid.values()) {
            cell.clear();
        }
    }

    /**
     * Insert an entity into the grid
     */
    public void insert(Entity entity) {
        PositionComponent pos = entity.getComponent(PositionComponent.class);
        if (pos == null) return;

        long cellKey = getCellKey(pos.getX(), pos.getY());
        Array<Entity> cell = grid.get(cellKey);

        if (cell == null) {
            cell = new Array<>(16);
            grid.put(cellKey, cell);
        }

        cell.add(entity);
    }

    /**
     * Get all entities near a point (within radius)
     */
    public Array<Entity> getNearby(float x, float y, float radius) {
        tempResults.clear();

        // Calculate which cells to check
        int minCellX = (int) ((x - radius) / cellSize);
        int maxCellX = (int) ((x + radius) / cellSize);
        int minCellY = (int) ((y - radius) / cellSize);
        int maxCellY = (int) ((y + radius) / cellSize);

        float radiusSquared = radius * radius;

        // Check all cells in range
        for (int cellX = minCellX; cellX <= maxCellX; cellX++) {
            for (int cellY = minCellY; cellY <= maxCellY; cellY++) {
                long cellKey = getCellKey(cellX * cellSize, cellY * cellSize);
                Array<Entity> cell = grid.get(cellKey);

                if (cell != null) {
                    // Check each entity in the cell
                    for (Entity entity : cell) {
                        PositionComponent pos = entity.getComponent(PositionComponent.class);
                        if (pos == null) continue;

                        // Distance check
                        float dx = pos.getX() - x;
                        float dy = pos.getY() - y;
                        float distSquared = dx * dx + dy * dy;

                        if (distSquared <= radiusSquared) {
                            tempResults.add(entity);
                        }
                    }
                }
            }
        }

        return tempResults;
    }

    /**
     * Get all entities in a rectangular area
     */
    public Array<Entity> getInBounds(Rectangle bounds) {
        tempResults.clear();

        int minCellX = (int) (bounds.x / cellSize);
        int maxCellX = (int) ((bounds.x + bounds.width) / cellSize);
        int minCellY = (int) (bounds.y / cellSize);
        int maxCellY = (int) ((bounds.y + bounds.height) / cellSize);

        for (int cellX = minCellX; cellX <= maxCellX; cellX++) {
            for (int cellY = minCellY; cellY <= maxCellY; cellY++) {
                long cellKey = getCellKey(cellX * cellSize, cellY * cellSize);
                Array<Entity> cell = grid.get(cellKey);

                if (cell != null) {
                    for (Entity entity : cell) {
                        PositionComponent pos = entity.getComponent(PositionComponent.class);
                        if (pos == null) continue;

                        if (bounds.contains(pos.getX(), pos.getY())) {
                            tempResults.add(entity);
                        }
                    }
                }
            }
        }

        return tempResults;
    }

    /**
     * Generate a unique key for a grid cell
     */
    private long getCellKey(float x, float y) {
        int cellX = (int) (x / cellSize);
        int cellY = (int) (y / cellSize);
        return ((long) cellX << 32) | (cellY & 0xFFFFFFFFL);
    }

    /**
     * Get statistics for debugging
     */
    public String getStats() {
        int totalCells = grid.size;
        int totalEntities = 0;
        int maxEntitiesInCell = 0;

        for (Array<Entity> cell : grid.values()) {
            totalEntities += cell.size;
            maxEntitiesInCell = Math.max(maxEntitiesInCell, cell.size);
        }

        return String.format("Grid: %d cells, %d entities, max %d per cell",
            totalCells, totalEntities, maxEntitiesInCell);
    }
}

