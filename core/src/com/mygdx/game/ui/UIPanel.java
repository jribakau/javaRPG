package com.mygdx.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * UIPanel - Container for UI components
 * Panels can have backgrounds, borders, and contain multiple child components
 */
public class UIPanel extends UIComponent {
    private final List<UIComponent> children = new ArrayList<>();
    private Color backgroundColor;
    private Color borderColor;
    private float borderWidth = 0;
    private float padding = 5;

    public UIPanel(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    /**
     * Add a child component to this panel
     */
    public UIPanel add(UIComponent component) {
        children.add(component);
        return this;
    }

    /**
     * Remove a child component
     */
    public void remove(UIComponent component) {
        children.remove(component);
    }

    /**
     * Clear all children
     */
    public void clear() {
        children.forEach(UIComponent::dispose);
        children.clear();
    }

    @Override
    public void update(float delta) {
        if (!visible) return;

        for (UIComponent child : children) {
            if (child.isVisible()) {
                child.update(delta);
            }
        }
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        if (!visible) return;

        // Draw background
        if (backgroundColor != null) {
            batch.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, backgroundColor.a * alpha);
            shapeRenderer.rect(x, y, width, height);
            shapeRenderer.end();
            batch.begin();
        }

        // Draw border
        if (borderColor != null && borderWidth > 0) {
            batch.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(borderColor.r, borderColor.g, borderColor.b, borderColor.a * alpha);
            for (int i = 0; i < borderWidth; i++) {
                shapeRenderer.rect(x + i, y + i, width - i * 2, height - i * 2);
            }
            shapeRenderer.end();
            batch.begin();
        }

        // Render children
        for (UIComponent child : children) {
            if (child.isVisible()) {
                child.render(batch, shapeRenderer);
            }
        }
    }

    @Override
    public void dispose() {
        children.forEach(UIComponent::dispose);
        children.clear();
    }

    // Builder-style setters
    public UIPanel withBackground(Color color) {
        this.backgroundColor = color;
        return this;
    }

    public UIPanel withBorder(Color color, float width) {
        this.borderColor = color;
        this.borderWidth = width;
        return this;
    }

    public UIPanel withPadding(float padding) {
        this.padding = padding;
        return this;
    }

    public float getPadding() {
        return padding;
    }

    public List<UIComponent> getChildren() {
        return new ArrayList<>(children);
    }
}

