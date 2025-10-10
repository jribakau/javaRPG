package com.mygdx.game.core;

import com.badlogic.gdx.Gdx;

/**
 * ServiceLocator - Central registry for all game services
 * This pattern allows easy access to services without tight coupling
 */
public class ServiceLocator {
    private static GameContext gameContext;

    public static void initialize() {
        Gdx.app.log("ServiceLocator", "Initializing...");
        // Services will be registered after initialization
    }

    public static void registerGameContext(GameContext context) {
        gameContext = context;
        Gdx.app.log("ServiceLocator", "GameContext registered");
    }

    public static GameContext getGameContext() {
        if (gameContext == null) {
            throw new IllegalStateException("GameContext not registered. Call registerGameContext first.");
        }
        return gameContext;
    }

    public static void dispose() {
        Gdx.app.log("ServiceLocator", "Disposing services...");
        if (gameContext != null) {
            gameContext.dispose();
            gameContext = null;
        }
    }
}