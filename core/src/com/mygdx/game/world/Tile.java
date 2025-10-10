package com.mygdx.game.world;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lombok.Getter;
import lombok.Setter;

/**
 * Tile - Represents an individual tile in the game world
 * Contains position, type, and rendering information
 */
@Getter
@Setter
public class Tile {
    public static final int TILE_SIZE = 32; // Default tile size in pixels

    private final int x;           // Grid x position
    private final int y;           // Grid y position
    private final int tileType;    // Type of tile (corresponds to level data)

    private TextureRegion texture; // Visual representation
    private boolean walkable;      // Can entities walk through this tile?
    private boolean transparent;   // Does this tile block vision?

    public Tile(int x, int y, int tileType) {
        this.x = x;
        this.y = y;
        this.tileType = tileType;
        this.walkable = true;
        this.transparent = true;
    }

    /**
     * Get world position X (in pixels)
     */
    public float getWorldX() {
        return x * TILE_SIZE;
    }

    /**
     * Get world position Y (in pixels)
     */
    public float getWorldY() {
        return y * TILE_SIZE;
    }

    /**
     * Check if a world position is within this tile
     */
    public boolean contains(float worldX, float worldY) {
        return worldX >= getWorldX() && worldX < getWorldX() + TILE_SIZE &&
               worldY >= getWorldY() && worldY < getWorldY() + TILE_SIZE;
    }

    @Override
    public String toString() {
        return String.format("Tile[%d,%d type=%d walkable=%b]", x, y, tileType, walkable);
    }
}

