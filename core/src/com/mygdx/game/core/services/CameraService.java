package com.mygdx.game.core.services;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import lombok.Getter;

/**
 * CameraService - Manages the game camera and viewport
 * Single Responsibility: Camera positioning, zoom, viewport management
 */
@Getter
public class CameraService {
    public static final int WORLD_WIDTH = 800;
    public static final int WORLD_HEIGHT = 600;

    private final OrthographicCamera camera;
    private final Viewport viewport;

    public CameraService() {
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        // Set initial camera position to center of world
        camera.position.set(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2f, 0);
        camera.update();
    }

    /**
     * Update camera position to follow a target (e.g., player)
     */
    public void followTarget(float x, float y) {
        camera.position.set(x, y, 0);
        camera.update();
    }

    /**
     * Set camera position
     */
    public void setPosition(float x, float y) {
        camera.position.set(x, y, 0);
        camera.update();
    }

    /**
     * Update camera (should be called each frame if camera moves)
     */
    public void update() {
        camera.update();
    }

    /**
     * Handle window resize
     */
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    /**
     * Set camera zoom level
     */
    public void setZoom(float zoom) {
        camera.zoom = zoom;
        camera.update();
    }

    /**
     * Get current zoom level
     */
    public float getZoom() {
        return camera.zoom;
    }
}