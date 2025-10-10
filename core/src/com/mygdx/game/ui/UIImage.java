package com.mygdx.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * UIImage - Display a texture/sprite
 */
public class UIImage extends UIComponent {
    private TextureRegion texture;
    private Color tint = Color.WHITE.cpy();

    public UIImage(TextureRegion texture, float x, float y, float width, float height) {
        super(x, y, width, height);
        this.texture = texture;
    }

    public UIImage(TextureRegion texture, float x, float y) {
        super(x, y, texture.getRegionWidth(), texture.getRegionHeight());
        this.texture = texture;
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        if (!visible || texture == null) return;

        Color originalColor = batch.getColor().cpy();
        batch.setColor(tint.r, tint.g, tint.b, tint.a * alpha);
        batch.draw(texture, x, y, width, height);
        batch.setColor(originalColor);
    }

    public void setTexture(TextureRegion texture) {
        this.texture = texture;
    }

    public UIImage withTint(Color tint) {
        this.tint = tint.cpy();
        return this;
    }

    public void setTint(Color tint) {
        this.tint = tint.cpy();
    }
}

