package com.mygdx.game.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import lombok.Getter;

/**
 * TileMap - Manages a 2D grid of tiles
 * Provides methods for accessing, rendering, and querying tiles
 */
@Getter
public class TileMap {
    private final int width;          // Width in tiles
    private final int height;         // Height in tiles
    private final Tile[][] tiles;     // 2D array of tiles

    public TileMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width][height];
    }

    /**
     * Set a tile at grid position
     */
    public void setTile(int x, int y, Tile tile) {
        if (isValidPosition(x, y)) {
            tiles[x][y] = tile;
        }
    }

    /**
     * Get a tile at grid position
     */
    public Tile getTile(int x, int y) {
        if (isValidPosition(x, y)) {
            return tiles[x][y];
        }
        return null;
    }

    /**
     * Get a tile at world coordinates
     */
    public Tile getTileAtWorldPosition(float worldX, float worldY) {
        int tileX = (int) (worldX / Tile.TILE_SIZE);
        int tileY = (int) (worldY / Tile.TILE_SIZE);
        return getTile(tileX, tileY);
    }

    /**
     * Check if a grid position is valid
     */
    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    /**
     * Check if a tile is walkable
     */
    public boolean isWalkable(int x, int y) {
        Tile tile = getTile(x, y);
        return tile != null && tile.isWalkable();
    }

    /**
     * Get the tile size in pixels
     */
    public int getTileSize() {
        return Tile.TILE_SIZE;
    }

    /**
     * Get all tiles in the map
     */
    public Array<Tile> getAllTiles() {
        Array<Tile> allTiles = new Array<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (tiles[x][y] != null) {
                    allTiles.add(tiles[x][y]);
                }
            }
        }
        return allTiles;
    }

    /**
     * Get tiles within a rectangular area (useful for rendering only visible tiles)
     */
    public Array<Tile> getTilesInArea(int startX, int startY, int endX, int endY) {
        Array<Tile> tilesInArea = new Array<>();

        // Clamp to valid bounds
        startX = Math.max(0, startX);
        startY = Math.max(0, startY);
        endX = Math.min(width - 1, endX);
        endY = Math.min(height - 1, endY);

        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                if (tiles[x][y] != null) {
                    tilesInArea.add(tiles[x][y]);
                }
            }
        }
        return tilesInArea;
    }

    /**
     * Render all tiles
     */
    public void render(SpriteBatch batch) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Tile tile = tiles[x][y];
                if (tile != null && tile.getTexture() != null) {
                    batch.draw(tile.getTexture(),
                             tile.getWorldX(),
                             tile.getWorldY(),
                             Tile.TILE_SIZE,
                             Tile.TILE_SIZE);
                }
            }
        }
    }

    /**
     * Render only visible tiles within camera bounds
     */
    public void render(SpriteBatch batch, float camX, float camY, float camWidth, float camHeight) {
        // Calculate tile boundaries based on camera view
        int startX = (int) Math.max(0, (camX - camWidth / 2) / Tile.TILE_SIZE);
        int startY = (int) Math.max(0, (camY - camHeight / 2) / Tile.TILE_SIZE);
        int endX = (int) Math.min(width - 1, (camX + camWidth / 2) / Tile.TILE_SIZE + 1);
        int endY = (int) Math.min(height - 1, (camY + camHeight / 2) / Tile.TILE_SIZE + 1);

        // Render only visible tiles
        Array<Tile> visibleTiles = getTilesInArea(startX, startY, endX, endY);
        for (Tile tile : visibleTiles) {
            if (tile.getTexture() != null) {
                batch.draw(tile.getTexture(),
                         tile.getWorldX(),
                         tile.getWorldY(),
                         Tile.TILE_SIZE,
                         Tile.TILE_SIZE);
            }
        }
    }

    /**
     * Get the world width in pixels
     */
    public float getWorldWidth() {
        return width * Tile.TILE_SIZE;
    }

    /**
     * Get the world height in pixels
     */
    public float getWorldHeight() {
        return height * Tile.TILE_SIZE;
    }

    /**
     * Clear all tiles
     */
    public void clear() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = null;
            }
        }
    }
}
