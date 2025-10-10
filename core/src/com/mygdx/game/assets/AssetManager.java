package com.mygdx.game.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.Map;

/**
 * AssetManager - Centralized asset loading and management
 * Handles textures, texture atlases, and provides easy access to sprites
 */
public class AssetManager implements Disposable {
    private final Map<String, Texture> textures;
    private final Map<String, TextureAtlas> atlases;
    private final Map<String, TextureRegion> cachedRegions;

    private boolean loaded = false;

    public AssetManager() {
        this.textures = new HashMap<>();
        this.atlases = new HashMap<>();
        this.cachedRegions = new HashMap<>();

        Gdx.app.log("AssetManager", "Asset Manager created");
    }

    /**
     * Load all game assets
     * Call this during game initialization
     */
    public void loadAssets() {
        if (loaded) {
            Gdx.app.log("AssetManager", "Assets already loaded");
            return;
        }

        Gdx.app.log("AssetManager", "Loading assets...");

        try {
            // Load texture atlases
            loadTextureAtlas("rogues", "textures/rogues.png");
            loadTextureAtlas("monsters", "textures/monsters.png");
            loadTextureAtlas("animals", "textures/animals.png");
            loadTextureAtlas("items", "textures/items.png");
            loadTextureAtlas("tiles", "textures/tiles.png");

            loaded = true;
            Gdx.app.log("AssetManager", "All assets loaded successfully");
        } catch (Exception e) {
            Gdx.app.error("AssetManager", "Error loading assets: " + e.getMessage());
            throw new RuntimeException("Failed to load assets", e);
        }
    }

    /**
     * Load a texture atlas
     */
    private void loadTextureAtlas(String name, String path) {
        if (!Gdx.files.internal(path).exists()) {
            Gdx.app.error("AssetManager", "Texture file not found: " + path);
            return;
        }

        Texture texture = new Texture(Gdx.files.internal(path));
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        textures.put(name, texture);

        Gdx.app.log("AssetManager", "Loaded texture: " + name);
    }

    /**
     * Get a sprite region from a texture atlas
     * @param atlasName The name of the atlas (e.g., "rogues", "monsters")
     * @param x X position in the sprite sheet (in tiles)
     * @param y Y position in the sprite sheet (in tiles)
     * @param tileSize Size of each tile in pixels (usually 16 or 32)
     * @return TextureRegion for the sprite
     */
    public TextureRegion getSprite(String atlasName, int x, int y, int tileSize) {
        String cacheKey = atlasName + "_" + x + "_" + y + "_" + tileSize;

        if (cachedRegions.containsKey(cacheKey)) {
            return cachedRegions.get(cacheKey);
        }

        Texture texture = textures.get(atlasName);
        if (texture == null) {
            Gdx.app.error("AssetManager", "Texture atlas not found: " + atlasName);
            return null;
        }

        TextureRegion region = new TextureRegion(texture, x * tileSize, y * tileSize, tileSize, tileSize);
        cachedRegions.put(cacheKey, region);

        return region;
    }

    /**
     * Get a sprite by character type
     */
    public TextureRegion getCharacterSprite(CharacterType type) {
        return getSprite("rogues", type.getX(), type.getY(), 32);
    }

    /**
     * Get a sprite by monster type
     */
    public TextureRegion getMonsterSprite(MonsterType type) {
        return getSprite("monsters", type.getX(), type.getY(), 32);
    }

    /**
     * Get a sprite by animal type
     */
    public TextureRegion getAnimalSprite(AnimalType type) {
        return getSprite("animals", type.getX(), type.getY(), 32);
    }

    /**
     * Get a sprite by item type
     */
    public TextureRegion getItemSprite(ItemType type) {
        return getSprite("items", type.getX(), type.getY(), 32);
    }

    /**
     * Get a tile sprite
     */
    public TextureRegion getTileSprite(TileType type) {
        return getSprite("tiles", type.getX(), type.getY(), 32);
    }

    /**
     * Get raw texture by name
     */
    public Texture getTexture(String name) {
        return textures.get(name);
    }

    /**
     * Get a texture region by atlas name and region name
     * This method looks up textures by name (e.g., "tiles" atlas with "DIRT_BRICK_FLOOR" name)
     */
    public TextureRegion getTextureRegion(String atlasName, String regionName) {
        String cacheKey = atlasName + "_" + regionName;

        if (cachedRegions.containsKey(cacheKey)) {
            return cachedRegions.get(cacheKey);
        }

        // Try to find the tile type by name
        if ("tiles".equals(atlasName)) {
            try {
                TileType tileType = TileType.valueOf(regionName);
                TextureRegion region = getTileSprite(tileType);
                if (region != null) {
                    cachedRegions.put(cacheKey, region);
                }
                return region;
            } catch (IllegalArgumentException e) {
                Gdx.app.debug("AssetManager", "Tile type not found: " + regionName);
            }
        }

        return null;
    }


    @Override
    public void dispose() {
        Gdx.app.log("AssetManager", "Disposing assets...");

        for (Texture texture : textures.values()) {
            texture.dispose();
        }
        textures.clear();

        for (TextureAtlas atlas : atlases.values()) {
            atlas.dispose();
        }
        atlases.clear();

        cachedRegions.clear();
        loaded = false;

        Gdx.app.log("AssetManager", "Assets disposed");
    }
}