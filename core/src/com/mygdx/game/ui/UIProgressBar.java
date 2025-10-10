package com.mygdx.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * UIProgressBar - Displays a progress bar (health, mana, experience, etc.)
 */
public class UIProgressBar extends UIComponent {
    private float value;
    private float maxValue;
    private Color fillColor;
    private Color backgroundColor;
    private Color borderColor;
    private boolean showText = false;
    private BitmapFont font;
    private String prefix = "";
    private boolean animate = true;
    private float currentDisplayValue;
    private float animationSpeed = 5.0f;

    public UIProgressBar(float x, float y, float width, float height, float maxValue) {
        super(x, y, width, height);
        this.value = maxValue;
        this.maxValue = maxValue;
        this.currentDisplayValue = maxValue;
        this.fillColor = Color.GREEN.cpy();
        this.backgroundColor = new Color(0.3f, 0.3f, 0.3f, 0.8f);
        this.borderColor = Color.BLACK.cpy();
    }

    @Override
    public void update(float delta) {
        if (!animate) {
            currentDisplayValue = value;
            return;
        }

        // Smoothly animate to target value
        if (Math.abs(currentDisplayValue - value) > 0.1f) {
            float diff = value - currentDisplayValue;
            currentDisplayValue += diff * animationSpeed * delta;
        } else {
            currentDisplayValue = value;
        }
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        if (!visible) return;

        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Draw background
        shapeRenderer.setColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, backgroundColor.a * alpha);
        shapeRenderer.rect(x, y, width, height);

        // Draw fill
        float fillWidth = (currentDisplayValue / maxValue) * width;
        shapeRenderer.setColor(fillColor.r, fillColor.g, fillColor.b, fillColor.a * alpha);
        shapeRenderer.rect(x, y, fillWidth, height);

        shapeRenderer.end();

        // Draw border
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(borderColor.r, borderColor.g, borderColor.b, borderColor.a * alpha);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();

        batch.begin();

        // Draw text
        if (showText && font != null) {
            String text = prefix + (int) value + "/" + (int) maxValue;
            com.badlogic.gdx.graphics.g2d.GlyphLayout layout =
                new com.badlogic.gdx.graphics.g2d.GlyphLayout(font, text);
            float textX = x + (width - layout.width) / 2;
            float textY = y + (height + layout.height) / 2;

            // Draw shadow
            font.setColor(0, 0, 0, 0.7f * alpha);
            font.draw(batch, text, textX + 1, textY - 1);

            // Draw text
            font.setColor(1, 1, 1, 1 * alpha);
            font.draw(batch, text, textX, textY);
        }
    }

    public void setValue(float value) {
        this.value = Math.max(0, Math.min(maxValue, value));
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
        this.value = Math.min(this.value, maxValue);
    }

    public float getValue() {
        return value;
    }

    public float getMaxValue() {
        return maxValue;
    }

    public float getPercentage() {
        return maxValue > 0 ? value / maxValue : 0;
    }

    // Builder-style setters
    public UIProgressBar withFillColor(Color color) {
        this.fillColor = color.cpy();
        return this;
    }

    public UIProgressBar withBackgroundColor(Color color) {
        this.backgroundColor = color.cpy();
        return this;
    }

    public UIProgressBar withBorderColor(Color color) {
        this.borderColor = color.cpy();
        return this;
    }

    public UIProgressBar withText(BitmapFont font, String prefix) {
        this.showText = true;
        this.font = font;
        this.prefix = prefix;
        return this;
    }

    public UIProgressBar withAnimation(boolean animate, float speed) {
        this.animate = animate;
        this.animationSpeed = speed;
        return this;
    }

    public void setShowText(boolean showText) {
        this.showText = showText;
    }
}

