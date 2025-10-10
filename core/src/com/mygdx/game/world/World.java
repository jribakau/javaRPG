package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entity.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * World - Represents the complete game world
 * Contains the tile map, entities, and provides high-level world operations
 */
@Getter
public class World {
    private final TileMap tileMap;
    private final Array<Entity> worldEntities;
    /**
     * -- SETTER --
     *  Set the world name
     */
    @Setter
    private String worldName;

    public World(TileMap tileMap) {
        this(tileMap, "Unnamed World");
    }

    public World(TileMap tileMap, String worldName) {
        this.tileMap = tileMap;
        this.worldName = worldName;
        this.worldEntities = new Array<>();

        Gdx.app.log("World", "Created world: " + worldName +
                   " (Size: " + tileMap.getWidth() + "x" + tileMap.getHeight() + ")");
    }

    /**
     * Update the world
     */
    public void update(float delta) {
        // World-specific updates can go here
        // For now, entity updates are handled by EntityService
    }

    /**
     * Render the world (tiles only)
     */
    public void render(SpriteBatch batch) {
        tileMap.render(batch);
    }

    /**
     * Render the world with camera culling
     */
    public void render(SpriteBatch batch, float camX, float camY, float camWidth, float camHeight) {
        tileMap.render(batch, camX, camY, camWidth, camHeight);
    }

    /**
     * Add an entity to this world
     */
    public void addEntity(Entity entity) {
        worldEntities.add(entity);
    }

    /**
     * Remove an entity from this world
     */
    public void removeEntity(Entity entity) {
        worldEntities.removeValue(entity, true);
    }

    /**
     * Get all entities in the world
     */
    public Array<Entity> getEntities() {
        return worldEntities;
    }

    /**
     * Check if a world position is walkable
     */
    public boolean isWalkable(float worldX, float worldY) {
        Tile tile = tileMap.getTileAtWorldPosition(worldX, worldY);
        return tile != null && tile.isWalkable();
    }

    /**
     * Check if a grid position is walkable
     */
    public boolean isWalkableGrid(int gridX, int gridY) {
        return tileMap.isWalkable(gridX, gridY);
    }

    /**
     * Get a tile at world coordinates
     */
    public Tile getTileAt(float worldX, float worldY) {
        return tileMap.getTileAtWorldPosition(worldX, worldY);
    }

    /**
     * Get a tile at grid coordinates
     */
    public Tile getTileAtGrid(int gridX, int gridY) {
        return tileMap.getTile(gridX, gridY);
    }

    /**
     * Get the world width in pixels
     */
    public float getWorldWidth() {
        return tileMap.getWorldWidth();
    }

    /**
     * Get the world height in pixels
     */
    public float getWorldHeight() {
        return tileMap.getWorldHeight();
    }

    /**
     * Check if world coordinates are within bounds
     */
    public boolean isInBounds(float worldX, float worldY) {
        return worldX >= 0 && worldX < getWorldWidth() &&
               worldY >= 0 && worldY < getWorldHeight();
    }

    /**
     * Convert world coordinates to grid coordinates
     */
    public int worldToGridX(float worldX) {
        return (int) (worldX / Tile.TILE_SIZE);
    }

    public int worldToGridY(float worldY) {
        return (int) (worldY / Tile.TILE_SIZE);
    }

    /**
     * Convert grid coordinates to world coordinates (center of tile)
     */
    public float gridToWorldX(int gridX) {
        return gridX * Tile.TILE_SIZE + Tile.TILE_SIZE / 2f;
    }

    public float gridToWorldY(int gridY) {
        return gridY * Tile.TILE_SIZE + Tile.TILE_SIZE / 2f;
    }

    /**
     * Get entities near a position
     */
    public Array<Entity> getEntitiesNear(float worldX, float worldY, float radius) {
        Array<Entity> nearbyEntities = new Array<>();
        float radiusSq = radius * radius;

        for (Entity entity : worldEntities) {
            // Note: This assumes entities have a position component
            // You'll need to adapt this based on your entity component structure
            float dx = worldX; // entity.getX() - worldX
            float dy = worldY; // entity.getY() - worldY

            if (dx * dx + dy * dy <= radiusSq) {
                nearbyEntities.add(entity);
            }
        }

        return nearbyEntities;
    }

    /**
     * Clear all entities from the world
     */
    public void clearEntities() {
        worldEntities.clear();
    }

    /**
     * Dispose of world resources
     */
    public void dispose() {
        Gdx.app.log("World", "Disposing world: " + worldName);
        clearEntities();
        tileMap.clear();
    }

    @Override
    public String toString() {
        return String.format("World[%s, size=%dx%d, entities=%d]",
                worldName,
                tileMap.getWidth(),
                tileMap.getHeight(),
                worldEntities.size);
    }
}

