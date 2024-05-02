package com.mygdx.game.userInterface;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class UIElement {
    protected SpriteBatch batch;
    protected BitmapFont font;
    protected float x, y;

    public UIElement(SpriteBatch batch, BitmapFont font, float x, float y) {
        this.batch = batch;
        this.font = font;
        this.x = x;
        this.y = y;
    }

    public abstract void draw(OrthographicCamera camera);
}
