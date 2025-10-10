package com.mygdx.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.core.ServiceLocator;
import com.mygdx.game.core.services.CameraService;
import com.mygdx.game.core.services.RenderService;
import com.mygdx.game.input.InputAction;

/**
 * PauseState - Pause menu overlay
 * Displayed over the playing state when game is paused
 */
public class PauseState extends GameState {
    private CameraService cameraService;
    private RenderService renderService;
    private BitmapFont font;
    private BitmapFont titleFont;

    public PauseState(GameStateManager stateManager) {
        super(stateManager);
    }

    @Override
    protected void onCreate() {
        cameraService = ServiceLocator.get(CameraService.class);
        renderService = ServiceLocator.get(RenderService.class);

        font = new BitmapFont();
        font.getData().setScale(1.5f);

        titleFont = new BitmapFont();
        titleFont.getData().setScale(2.5f);
        titleFont.setColor(Color.YELLOW);

        Gdx.app.log("PauseState", "Pause state created");
    }

    @Override
    public void onEnter() {
        Gdx.app.log("PauseState", "Game paused");
    }

    @Override
    public void onExit() {
        Gdx.app.log("PauseState", "Game unpaused");
    }

    @Override
    public void update(float delta) {
        // Update input service
        inputService.update();

        // Check for resume using InputService
        if (inputService.isActionJustPressed(InputAction.PAUSE) ||
            inputService.isActionJustPressed(InputAction.CANCEL)) {
            stateManager.popState();
            return;
        }

        // Check for quit to menu (Q key - could be mapped to an InputAction in the future)
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.Q)) {
            stateManager.changeState(new MenuState(stateManager));
        }
    }

    @Override
    public void render(float delta) {
        // Render semi-transparent overlay
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        renderService.beginShapes(cameraService, ShapeRenderer.ShapeType.Filled);
        renderService.getShapeRenderer().setColor(0, 0, 0, 0.7f);
        renderService.getShapeRenderer().rect(0, 0, 800, 600);
        renderService.endShapes();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        // Render pause menu
        renderService.begin(cameraService);

        titleFont.draw(renderService.getBatch(), "PAUSED", 320, 400);

        font.draw(renderService.getBatch(), "ESC - Resume", 300, 320);
        font.draw(renderService.getBatch(), "Q - Quit to Menu", 300, 280);

        renderService.end();
    }

    @Override
    public boolean blocksUpdate() {
        return true; // Don't update playing state while paused
    }

    @Override
    public boolean blocksRender() {
        return false; // Still render playing state below (for background)
    }

    @Override
    public void dispose() {
        if (font != null) {
            font.dispose();
        }
        if (titleFont != null) {
            titleFont.dispose();
        }
    }
}
