package com.mygdx.game.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

/**
 * GameContext - Lightweight context for core game reference
 * No longer a God Object - specific responsibilities delegated to services
 * <p>
 * Services are now accessed via ServiceLocator:
 * - CameraService: Camera and viewport management
 * - RenderService: Batch and rendering utilities
 * - WorldService: World/level management
 * - EntityService: Entity lifecycle
 * - AssetManager: Asset loading
 * - EntityFactory: Entity creation
 */
public record GameContext(Game game) {
    public GameContext(Game game) {
        this.game = game;
        Gdx.app.log("GameContext", "Lightweight context created");
    }

    /**
     * Switch to a different screen
     */
    public void setScreen(Screen screen) {
        game.setScreen(screen);
    }

    /**
     * Get current screen
     */
    public Screen getScreen() {
        return game.getScreen();
    }

    public void dispose() {
        Gdx.app.log("GameContext", "Context disposed");
        // Individual services handle their own disposal
    }
}
