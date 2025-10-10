package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.core.GameContext;
import com.mygdx.game.core.ServiceLocator;
import com.mygdx.game.screens.GameScreen;

/**
 * Main Game Class - Entry point for the RPG game
 * Follows a clean architecture with ServiceLocator pattern for dependency management
 */
public class RPG extends Game {
    private SpriteBatch batch;
    private GameContext gameContext;

    @Override
    public void create() {
        Gdx.app.log("RPG", "Initializing game...");

        // Initialize core rendering components
        batch = new SpriteBatch();

        // Initialize service locator (dependency injection container)
        ServiceLocator.initialize();

        // Create game context (holds shared game state and services)
        gameContext = new GameContext(this, batch);

        // Register core services
        ServiceLocator.registerGameContext(gameContext);

        // Set initial screen
        setScreen(new GameScreen());

        Gdx.app.log("RPG", "Game initialized successfully");
    }

    @Override
    public void render() {
        super.render(); // Delegates to current screen's render method
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        Gdx.app.log("RPG", "Disposing game resources...");

        // Dispose current screen
        if (getScreen() != null) {
            getScreen().dispose();
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
