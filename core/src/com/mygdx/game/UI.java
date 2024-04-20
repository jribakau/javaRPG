package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class UI {
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final Player pc;

    public UI(SpriteBatch batch, BitmapFont font, Player pc) {
        this.batch = batch;
        this.font = font;
        this.pc = pc;
    }

    public void draw(OrthographicCamera camera) {
        float offsetY = 20;
        float offsetX = 40;

        batch.setProjectionMatrix(camera.combined);
        float uiX = camera.position.x - (float) Gdx.graphics.getWidth() / 2 + offsetX;
        float uiY = camera.position.y + (float) Gdx.graphics.getHeight() / 2 - offsetY;
        font.draw(batch, "Velocity: " + pc.getVelocity(), uiX, uiY - 10);
    }
}