package com.mygdx.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * UILabel - Simple text label
 */
public class UILabel extends UIComponent {
    private String text;
    private BitmapFont font;
    private Color color;
    private boolean shadow = false;
    private Color shadowColor = new Color(0, 0, 0, 0.5f);

    public UILabel(String text, float x, float y, BitmapFont font) {
        super(x, y, 0, 0);
        this.text = text;
        this.font = font;
        this.color = Color.WHITE.cpy();
        updateSize();
    }

    private void updateSize() {
        if (font != null && text != null) {
            com.badlogic.gdx.graphics.g2d.GlyphLayout layout =
                new com.badlogic.gdx.graphics.g2d.GlyphLayout(font, text);
            this.width = layout.width;
            this.height = layout.height;
        }
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        if (!visible || text == null || font == null) return;

        Color originalColor = font.getColor().cpy();

        // Draw shadow
        if (shadow) {
            font.setColor(shadowColor.r, shadowColor.g, shadowColor.b, shadowColor.a * alpha);
            font.draw(batch, text, x + 1, y - 1);
        }

        // Draw text
        font.setColor(color.r, color.g, color.b, color.a * alpha);
        font.draw(batch, text, x, y);

        font.setColor(originalColor);
    }

    public void setText(String text) {
        this.text = text;
        updateSize();
    }

    public String getText() {
        return text;
    }

    public UILabel withColor(Color color) {
        this.color = color.cpy();
        return this;
    }

    public UILabel withShadow(boolean shadow) {
        this.shadow = shadow;
        return this;
    }

    public UILabel withShadowColor(Color shadowColor) {
        this.shadowColor = shadowColor.cpy();
        return this;
    }

    public void setColor(Color color) {
        this.color = color.cpy();
    }

    public Color getColor() {
        return color;
    }
}

