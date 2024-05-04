package com.mygdx.game.screenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.RPG;
import com.mygdx.game.levelManager.LevelEditor;

public class LevelEditorScreen implements Screen {
    private final RPG game;
    private final LevelEditor levelCreator;

    public LevelEditorScreen(RPG game) {
        this.game = game;
        levelCreator = new LevelEditor(game.getGameManager().getAssetManager());
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        levelCreator.draw(game.getBatch());
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // Handle screen resizing here
    }

    @Override
    public void pause() {
        // Handle game pause here
    }

    @Override
    public void resume() {
        // Handle game resume here
    }

    @Override
    public void hide() {
        // Handle when this screen is no longer the active screen here
    }

    @Override
    public void dispose() {
        // Dispose of your LevelEditor instance here
    }
}