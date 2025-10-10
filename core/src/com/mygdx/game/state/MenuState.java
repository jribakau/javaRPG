package com.mygdx.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.game.core.ServiceLocator;
import com.mygdx.game.core.services.CameraService;
import com.mygdx.game.core.services.RenderService;

/**
 * MenuState - Main menu state
 * Initial state when game starts, shows options to play, settings, quit, etc.
 */
public class MenuState extends GameState {
    private CameraService cameraService;
    private RenderService renderService;
    private BitmapFont font;
    private BitmapFont titleFont;
    private int selectedOption;
    private static final String[] MENU_OPTIONS = {"New Game", "Continue", "Settings", "Quit"};

    public MenuState(GameStateManager stateManager) {
        super(stateManager);
        this.selectedOption = 0;
    }

    @Override
    protected void onCreate() {
        cameraService = ServiceLocator.get(CameraService.class);
        renderService = ServiceLocator.get(RenderService.class);

        font = new BitmapFont();
        font.getData().setScale(2.0f);

        titleFont = new BitmapFont();
        titleFont.getData().setScale(3.5f);
        titleFont.setColor(Color.GOLD);

        Gdx.app.log("MenuState", "Menu state created");
    }

    @Override
    public void onEnter() {
        Gdx.app.log("MenuState", "Entered menu");
    }

    @Override
    public void onExit() {
        Gdx.app.log("MenuState", "Exited menu");
    }

    @Override
    public void update(float delta) {
        // Navigate menu
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            selectedOption = (selectedOption - 1 + MENU_OPTIONS.length) % MENU_OPTIONS.length;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            selectedOption = (selectedOption + 1) % MENU_OPTIONS.length;
        }

        // Select option
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            handleMenuSelection();
        }
    }

    private void handleMenuSelection() {
        switch (selectedOption) {
            case 0: // New Game
                Gdx.app.log("MenuState", "Starting new game...");
                stateManager.changeState(new PlayingState(stateManager));
                break;
            case 1: // Continue
                Gdx.app.log("MenuState", "Continue not yet implemented");
                // TODO: Implement save/load system
                break;
            case 2: // Settings
                Gdx.app.log("MenuState", "Settings not yet implemented");
                // TODO: Implement settings state
                break;
            case 3: // Quit
                Gdx.app.log("MenuState", "Quitting game...");
                Gdx.app.exit();
                break;
        }
    }

    @Override
    public void render(float delta) {
        // Clear screen
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderService.begin(cameraService);

        // Title
        titleFont.draw(renderService.getBatch(), "RPG GAME", 250, 500);

        // Menu options
        float y = 350;
        for (int i = 0; i < MENU_OPTIONS.length; i++) {
            if (i == selectedOption) {
                font.setColor(Color.YELLOW);
                font.draw(renderService.getBatch(), "> " + MENU_OPTIONS[i] + " <", 300, y);
            } else {
                font.setColor(Color.WHITE);
                font.draw(renderService.getBatch(), MENU_OPTIONS[i], 320, y);
            }
            y -= 60;
        }

        // Controls hint
        font.setColor(Color.GRAY);
        font.getData().setScale(1.0f);
        font.draw(renderService.getBatch(), "Use Arrow Keys or WASD to navigate", 200, 100);
        font.draw(renderService.getBatch(), "Press ENTER or SPACE to select", 220, 70);
        font.getData().setScale(2.0f);
        font.setColor(Color.WHITE);

        renderService.end();
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