package com.mygdx.game.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Base class for all UI components
 * UI components are positioned in screen space (pixels from top-left)
 */
public abstract class UIComponent {
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected boolean visible = true;
    protected boolean enabled = true;
    protected UIAnchor anchor = UIAnchor.TOP_LEFT;
    protected float alpha = 1.0f;

    public UIComponent(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Update the component (for animations, etc.)
     */
    public void update(float delta) {
        // Override if needed
    }

    /**
     * Render the component
     */
    public abstract void render(SpriteBatch batch, ShapeRenderer shapeRenderer);

    /**
     * Check if a point is within this component
     */
    public boolean contains(float px, float py) {
        return px >= x && px <= x + width && py >= y && py <= y + height;
    }

    // Getters and Setters
    public float getX() { return x; }
    public float getY() { return y; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public boolean isVisible() { return visible; }
    public void setVisible(boolean visible) { this.visible = visible; }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public UIAnchor getAnchor() { return anchor; }
    public void setAnchor(UIAnchor anchor) { this.anchor = anchor; }

    public float getAlpha() { return alpha; }
    public void setAlpha(float alpha) { this.alpha = Math.max(0, Math.min(1, alpha)); }

    /**
     * Dispose resources
     */
    public void dispose() {
        // Override if needed
    }
}