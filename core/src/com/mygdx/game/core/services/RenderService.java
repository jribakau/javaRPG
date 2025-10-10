package com.mygdx.game.core.services;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.Getter;

/**
 * RenderService - Manages rendering resources
 * Single Responsibility: Batch management, rendering utilities
 */
@Getter
public class RenderService {
    private final SpriteBatch batch;
    private final ShapeRenderer shapeRenderer;

    public RenderService(SpriteBatch batch) {
        this.batch = batch;
        this.shapeRenderer = new ShapeRenderer();
    }

    /**
     * Begin batch rendering with camera projection
     */
    public void begin(CameraService cameraService) {
        batch.setProjectionMatrix(cameraService.getCamera().combined);
        batch.begin();
    }

    /**
     * End batch rendering
     */
    public void end() {
        batch.end();
    }

    /**
     * Begin shape rendering with camera projection
     */
    public void beginShapes(CameraService cameraService, ShapeRenderer.ShapeType shapeType) {
        shapeRenderer.setProjectionMatrix(cameraService.getCamera().combined);
        shapeRenderer.begin(shapeType);
    }

    /**
     * End shape rendering
     */
    public void endShapes() {
        shapeRenderer.end();
    }

    /**
     * Dispose rendering resources
     */
    public void dispose() {
        // Note: SpriteBatch is disposed by RPG class
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
    }
}

