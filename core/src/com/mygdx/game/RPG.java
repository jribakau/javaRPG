package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.assets.managers.AssetManager;
import com.mygdx.game.core.GameContext;
import com.mygdx.game.core.ServiceLocator;
import com.mygdx.game.core.services.CameraService;
import com.mygdx.game.core.services.RenderService;
import com.mygdx.game.core.services.WorldService;
import com.mygdx.game.entity.EntityFactory;
import com.mygdx.game.entity.EntityService;
import com.mygdx.game.input.InputService;
import com.mygdx.game.state.GameStateManager;
import com.mygdx.game.state.MenuState;
import com.mygdx.game.systems.*;
import com.mygdx.game.world.MapLoader;

/**
 * Main Game Class - Entry point for the RPG game
 * Follows a clean architecture with ServiceLocator pattern for dependency management
 */
public class RPG extends Game {
    private SpriteBatch batch;
    private GameStateManager gameStateManager;

    @Override
    public void create() {
        Gdx.app.log("RPG", "Initializing game...");

        // Initialize core rendering components
        batch = new SpriteBatch();

        // Initialize service locator (dependency injection container)
        ServiceLocator.initialize();

        // Create lightweight game context
        GameContext gameContext = new GameContext(this);
        ServiceLocator.provide(GameContext.class, gameContext);

        // Initialize and register Input Service (first, before components need it)
        InputService inputService = new InputService();
        ServiceLocator.provide(InputService.class, inputService);

        // Initialize and register Camera Service
        CameraService cameraService = new CameraService();
        ServiceLocator.provide(CameraService.class, cameraService);

        // Initialize and register Render Service
        RenderService renderService = new RenderService(batch);
        ServiceLocator.provide(RenderService.class, renderService);

        // Initialize and register Asset Manager
        AssetManager assetManager = new AssetManager();
        assetManager.loadAssets();
        ServiceLocator.provide(AssetManager.class, assetManager);

        // Initialize and register Entity Service
        EntityService entityService = new EntityService(true, true);
        ServiceLocator.provide(EntityService.class, entityService);

        // Initialize and register Entity Factory (depends on AssetManager)
        EntityFactory entityFactory = new EntityFactory(assetManager);
        ServiceLocator.provide(EntityFactory.class, entityFactory);

        // Initialize and register Map Loader (depends on AssetManager)
        MapLoader mapLoader = new MapLoader(assetManager);
        ServiceLocator.provide(MapLoader.class, mapLoader);

        // Initialize and register World Service (depends on MapLoader)
        WorldService worldService = new WorldService(mapLoader);
        ServiceLocator.provide(WorldService.class, worldService);

        // Initialize System Manager and register game systems
        SystemManager systemManager = new SystemManager();
        systemManager.addSystem(new InputSystem(entityService));
        systemManager.addSystem(new MovementSystem(entityService));
        systemManager.addSystem(new CollisionSystem(entityService));
        systemManager.addSystem(new CombatSystem(entityService));
        systemManager.addSystem(new RenderSystem(entityService));
        ServiceLocator.provide(SystemManager.class, systemManager);

        // Initialize Game State Manager
        gameStateManager = new GameStateManager();
        ServiceLocator.provide(GameStateManager.class, gameStateManager);

        Gdx.app.log("RPG", "All services registered: " + ServiceLocator.getServiceCount() + " services");
        Gdx.app.log("RPG", "Systems initialized: " + systemManager.getSystemCount() + " systems");

        // Set initial state (Menu)
        gameStateManager.setInitialState(new MenuState(gameStateManager));

        Gdx.app.log("RPG", "Game initialized successfully");
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        // Update and render through state manager
        gameStateManager.update(delta);
        gameStateManager.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        // Notify camera service about resize
        if (ServiceLocator.has(CameraService.class)) {
            ServiceLocator.get(CameraService.class).resize(width, height);
        }

        // Notify state manager about resize
        if (gameStateManager != null) {
            gameStateManager.resize(width, height);
        }
    }

    @Override
    public void dispose() {
        Gdx.app.log("RPG", "Disposing game resources...");

        // Dispose state manager
        if (gameStateManager != null) {
            gameStateManager.dispose();
        }

        // Dispose system manager
        if (ServiceLocator.has(SystemManager.class)) {
            ServiceLocator.get(SystemManager.class).dispose();
        }

        // Dispose services that need cleanup
        if (ServiceLocator.has(RenderService.class)) {
            ServiceLocator.get(RenderService.class).dispose();
        }

        if (ServiceLocator.has(WorldService.class)) {
            ServiceLocator.get(WorldService.class).dispose();
        }

        if (ServiceLocator.has(AssetManager.class)) {
            ServiceLocator.get(AssetManager.class).dispose();
        }

        if (ServiceLocator.has(EntityService.class)) {
            ServiceLocator.get(EntityService.class).clear();
        }

        // Dispose core resources
        if (batch != null) {
            batch.dispose();
        }

        // Cleanup service locator
        ServiceLocator.dispose();

        Gdx.app.log("RPG", "Game disposed");
    }
}
