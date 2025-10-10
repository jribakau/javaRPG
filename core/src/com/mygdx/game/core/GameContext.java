package com.mygdx.game.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.assets.AssetManager;
import com.mygdx.game.entity.EntityFactory;
import com.mygdx.game.entity.EntityManager;
import lombok.Getter;

/**
 * GameContext - Holds all shared game state and core services
 * This is the central hub that all systems can access
 */
@Getter
public class GameContext {
    // Constants
    public static final int WORLD_WIDTH = 800;
    public static final int WORLD_HEIGHT = 600;

    // Core references
    private final Game game;
    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private final Viewport viewport;

    // Game systems
    private final EntityManager entityManager;
    private final AssetManager assetManager;
    private final EntityFactory entityFactory;

    public GameContext(Game game, SpriteBatch batch) {
        this.game = game;
        this.batch = batch;

        // Initialize camera and viewport
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        this.camera.position.set(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2f, 0);
        this.camera.update();

        // Initialize asset manager
        this.assetManager = new AssetManager();
        this.assetManager.loadAssets();

        // Initialize game systems
        this.entityManager = new EntityManager();
        this.entityFactory = new EntityFactory(assetManager);
    }

    public void dispose() {
        // Dispose any resources held by context
        // SpriteBatch is disposed by RPG class
        entityManager.clear();
        assetManager.dispose();
    }
}
