package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.assets.managers.AssetManager;
import com.mygdx.game.assets.types.TileType;

import java.util.HashMap;
import java.util.Map;

/**
 * MapLoader - Loads tile maps from text files
 * Parses level files and creates TileMap instances with appropriate tile types
 */
public class MapLoader {
    private final AssetManager assetManager;
    private final Map<Integer, TileConfig> tileConfigs;

    public MapLoader(AssetManager assetManager) {
        this.assetManager = assetManager;
        this.tileConfigs = new HashMap<>();
        initializeTileConfigs();
    }

    /**
     * Initialize default tile configurations
     * Maps tile type numbers to texture names and properties
     */
    private void initializeTileConfigs() {
        // Type 1: Walls (not walkable)
        tileConfigs.put(1, new TileConfig(TileType.DIRT_BRICK_WALL, false, false));

        // Type 2: Ground tiles (walkable)
        tileConfigs.put(2, new TileConfig(TileType.DIRT_BRICK_FLOOR, true, true));

        // Type 3: Brick ground
        tileConfigs.put(3, new TileConfig(TileType.STONE_BRICK_FLOOR, true, true));

        // Type 4: Rock ground
        tileConfigs.put(4, new TileConfig(TileType.DARK_COBBLESTONE_FLOOR, true, true));

        // Type 5: Red brick
        tileConfigs.put(5, new TileConfig(TileType.RED_BRICK_FLOOR, true, true));

        Gdx.app.log("MapLoader", "Initialized " + tileConfigs.size() + " tile configurations");
    }

    /**
     * Add or override a tile configuration
     */
    public void addTileConfig(int tileType, TileType textureType, boolean walkable, boolean transparent) {
        tileConfigs.put(tileType, new TileConfig(textureType, walkable, transparent));
    }

    /**
     * Load a map from a file
     * @param levelPath Path to the level file (e.g., "levels/level1.txt")
     * @return A fully configured TileMap
     */
    public TileMap loadMap(String levelPath) {
        Gdx.app.log("MapLoader", "Loading map from: " + levelPath);

        try {
            FileHandle file = Gdx.files.internal(levelPath);
            if (!file.exists()) {
                Gdx.app.error("MapLoader", "Map file not found: " + levelPath);
                return createEmptyMap(10, 10);
            }

            String content = file.readString();
            return parseMapData(content);

        } catch (Exception e) {
            Gdx.app.error("MapLoader", "Error loading map: " + levelPath, e);
            return createEmptyMap(10, 10);
        }
    }

    /**
     * Parse map data from string content
     */
    private TileMap parseMapData(String content) {
        String[] lines = content.split("\n");

        // Remove empty lines
        int validLineCount = 0;
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                validLineCount++;
            }
        }

        if (validLineCount == 0) {
            Gdx.app.error("MapLoader", "No valid lines in map data");
            return createEmptyMap(10, 10);
        }

        // Get dimensions from first line
        String[] firstLineTiles = lines[0].trim().split("\\s+");
        int width = firstLineTiles.length;
        int height = validLineCount;

        Gdx.app.log("MapLoader", String.format("Creating map of size %dx%d", width, height));

        TileMap tileMap = new TileMap(width, height);

        // Parse each line and create tiles
        int currentY = height - 1; // Start from top (flip Y for proper rendering)
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;

            String[] tileValues = line.split("\\s+");
            for (int x = 0; x < tileValues.length && x < width; x++) {
                try {
                    int tileType = Integer.parseInt(tileValues[x]);
                    Tile tile = createTile(x, currentY, tileType);
                    tileMap.setTile(x, currentY, tile);
                } catch (NumberFormatException e) {
                    Gdx.app.error("MapLoader", "Invalid tile value at (" + x + "," + currentY + "): " + tileValues[x]);
                }
            }
            currentY--;
        }

        Gdx.app.log("MapLoader", "Map loaded successfully");
        return tileMap;
    }

    /**
     * Create a tile with the given type
     */
    private Tile createTile(int x, int y, int tileType) {
        Tile tile = new Tile(x, y, tileType);

        // Get tile configuration
        TileConfig config = tileConfigs.get(tileType);
        if (config != null) {
            tile.setWalkable(config.walkable);
            tile.setTransparent(config.transparent);

            // Load texture from asset manager using TileType enum
            TextureRegion texture = assetManager.getTileSprite(config.tileType);
            if (texture != null) {
                tile.setTexture(texture);
            } else {
                Gdx.app.debug("MapLoader", "Texture not found for tile type " + tileType + ": " + config.tileType);
            }
        } else {
            Gdx.app.debug("MapLoader", "No configuration for tile type: " + tileType);
        }

        return tile;
    }

    /**
     * Create an empty map with default tiles
     */
    private TileMap createEmptyMap(int width, int height) {
        Gdx.app.log("MapLoader", "Creating empty map: " + width + "x" + height);
        TileMap tileMap = new TileMap(width, height);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Tile tile = createTile(x, y, 2); // Default to type 2 (ground)
                tileMap.setTile(x, y, tile);
            }
        }

        return tileMap;
    }

    /**
     * Inner class to hold tile configuration
     */
    private record TileConfig(TileType tileType, boolean walkable, boolean transparent) {
    }
}
