package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {
    private static final String WELCOME_MESSAGE = "Welcome to RPG!!! ";
    private static final String START_MESSAGE = "Tap anywhere to begin!";

    private final RPG game;
    private OrthographicCamera camera;

    public MainMenuScreen(final RPG game) {
        this.game = game;
        initialize();
    }

    private void initialize() {
        setupCamera();
    }

    private void setupCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
    }

    private void clearScreen() {
        ScreenUtils.clear(0, 0, 0.2f, 1);
    }

    private void drawMessages() {
        game.batch.begin();
        game.font.draw(game.batch, WELCOME_MESSAGE, 100, 150);
        game.font.draw(game.batch, START_MESSAGE, 100, 100);
        game.batch.end();
    }

    private void checkForStart() {
        if (Gdx.input.isTouched()) {
            game.setScreen(new GameEngine(game));
            dispose();
        }
    }

    @Override
    public void dispose() {
        // Dispose of resources here if any
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        clearScreen();
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        drawMessages();
        checkForStart();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
}