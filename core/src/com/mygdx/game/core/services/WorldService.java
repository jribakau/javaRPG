package com.mygdx.game.core.services;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.world.MapLoader;
import com.mygdx.game.world.World;
import lombok.Getter;

/**
 * WorldService - Manages the current world/level
 * Single Responsibility: World/level management and transitions
 */
@Getter
public class WorldService {
    private final MapLoader mapLoader;
    private World currentWorld;

    public WorldService(MapLoader mapLoader) {
        this.mapLoader = mapLoader;
    }

    /**
     * Load a world from a level file
     */
    public void loadWorld(String levelPath) {
        if (currentWorld != null) {
            currentWorld.dispose();
        }

        currentWorld = new World(mapLoader.loadMap(levelPath), levelPath);
        Gdx.app.log("WorldService", "Loaded world: " + levelPath);
    }

    /**
     * Set the current world directly
     */
    public void setCurrentWorld(World world) {
        if (currentWorld != null) {
            currentWorld.dispose();
        }
        currentWorld = world;
    }

    /**
     * Update current world
     */
    public void update(float delta) {
        if (currentWorld != null) {
            currentWorld.update(delta);
        }
    }

    /**
     * Check if a world is loaded
     */
    public boolean hasWorld() {
        return currentWorld != null;
    }

    /**
     * Dispose world resources
     */
    public void dispose() {
        if (currentWorld != null) {
            currentWorld.dispose();
            currentWorld = null;
        }
    }
}