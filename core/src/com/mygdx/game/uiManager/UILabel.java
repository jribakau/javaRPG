package com.mygdx.game.uiManager;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UILabel extends UIElement {
    private String text;

    public UILabel(SpriteBatch batch, BitmapFont font, float x, float y, String text) {
        super(batch, font, x, y);
        this.text = text;
    }

    @Override
    public void draw(OrthographicCamera camera) {
        batch.setProjectionMatrix(camera.combined);
        font.draw(batch, text, x, y);
    }
}
