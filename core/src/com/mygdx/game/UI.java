package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;

public class UI {
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final PC pc;
    private final NPC npc;

    public UI(SpriteBatch batch, BitmapFont font, PC pc, NPC npc) {
        this.batch = batch;
        this.font = font;
        this.pc = pc;
        this.npc = npc;
    }

    public void draw(OrthographicCamera camera) {
        batch.setProjectionMatrix(camera.combined);
        String playerStatus = pc.isAlive() ? "Alive" : "Dead";
        float uiX = pc.getPosition().x - (float) Constants.SCREEN_WIDTH / 2 + 50;
        float uiY = pc.getPosition().y + (float) Constants.SCREEN_HEIGHT / 2;
        font.draw(batch, playerStatus, uiX, uiY - 10);
        font.draw(batch, "Health: " + pc.getHealth(), uiX, uiY - 30);
        font.draw(batch, "Stamina: " + pc.getStamina(), uiX, uiY - 50);
        font.draw(batch, "Experience: " + pc.getExperience(), uiX, uiY - 70);
        font.draw(batch, "Velocity: " + pc.getVelocity(), uiX + camera.viewportWidth - 100, uiY - 10);
    }
}