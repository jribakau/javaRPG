package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UIService - Manages all UI rendering and updates
 * Uses its own camera for screen-space UI
 */
public class UIService {
    private final SpriteBatch batch;
    private final ShapeRenderer shapeRenderer;
    private final Camera uiCamera;
    private final Viewport uiViewport;
    private final BitmapFont defaultFont;

    private final List<UIComponent> components = new ArrayList<>();
    private final Map<String, UIComponent> namedComponents = new HashMap<>();

    private int screenWidth;
    private int screenHeight;

    public UIService() {
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.screenWidth = Gdx.graphics.getWidth();
        this.screenHeight = Gdx.graphics.getHeight();

        // Create UI camera (fixed screen space)
        this.uiCamera = new OrthographicCamera(screenWidth, screenHeight);
        this.uiViewport = new FitViewport(screenWidth, screenHeight, uiCamera);
        this.uiCamera.position.set(screenWidth / 2f, screenHeight / 2f, 0);
        this.uiCamera.update();

        // Create default font
        this.defaultFont = new BitmapFont();

        Gdx.app.log("UIService", "UI Service initialized with resolution: " + screenWidth + "x" + screenHeight);
    }

    /**
     * Add a UI component
     */
    public void addComponent(UIComponent component) {
        if (!components.contains(component)) {
            components.add(component);
        }
    }

    /**
     * Add a named UI component for easy retrieval
     */
    public void addComponent(String name, UIComponent component) {
        addComponent(component);
        namedComponents.put(name, component);
    }

    /**
     * Get a named component
     */
    @SuppressWarnings("unchecked")
    public <T extends UIComponent> T getComponent(String name) {
        return (T) namedComponents.get(name);
    }

    /**
     * Remove a component
     */
    public void removeComponent(UIComponent component) {
        components.remove(component);
        namedComponents.values().remove(component);
    }

    /**
     * Remove a named component
     */
    public void removeComponent(String name) {
        UIComponent component = namedComponents.remove(name);
        if (component != null) {
            components.remove(component);
        }
    }

    /**
     * Clear all components
     */
    public void clear() {
        components.forEach(UIComponent::dispose);
        components.clear();
        namedComponents.clear();
    }

    /**
     * Update all UI components
     */
    public void update(float delta) {
        for (UIComponent component : components) {
            if (component.isVisible()) {
                component.update(delta);
            }
        }
    }

    /**
     * Render all UI components
     */
    public void render() {
        uiCamera.update();
        batch.setProjectionMatrix(uiCamera.combined);
        shapeRenderer.setProjectionMatrix(uiCamera.combined);

        batch.begin();
        for (UIComponent component : components) {
            if (component.isVisible()) {
                component.render(batch, shapeRenderer);
            }
        }
        batch.end();
    }

    /**
     * Handle screen resize
     */
    public void resize(int width, int height) {
        this.screenWidth = width;
        this.screenHeight = height;
        uiViewport.update(width, height, true);
        Gdx.app.log("UIService", "Resized to: " + width + "x" + height);
    }

    /**
     * Get the default font
     */
    public BitmapFont getDefaultFont() {
        return defaultFont;
    }

    /**
     * Get screen dimensions
     */
    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    /**
     * Convert world coordinates to screen coordinates (if needed)
     */
    public float worldToScreenX(float worldX, Camera worldCamera) {
        // This would convert world coordinates to screen space
        // Useful for health bars above entities
        return worldX; // Placeholder
    }

    public float worldToScreenY(float worldY, Camera worldCamera) {
        return worldY; // Placeholder
    }

    /**
     * Dispose resources
     */
    public void dispose() {
        components.forEach(UIComponent::dispose);
        components.clear();
        namedComponents.clear();
        batch.dispose();
        shapeRenderer.dispose();
        defaultFont.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public Camera getUICamera() {
        return uiCamera;
    }
}

