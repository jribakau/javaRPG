package com.mygdx.game.screenManager;

import com.badlogic.gdx.Screen;
import com.mygdx.game.RPG;
import com.mygdx.game.gameManager.GameManagerUtils;

public class GameScreen implements Screen {
    private final RPG game;

    public GameScreen(final RPG game) {
        this.game = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        game.getGameManager().updateEntities();
        game.getGameManager().renderGame(delta);
        GameManagerUtils.detectCollisionsAndInteractions(game.getGameManager().getLevel());
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

    @Override
    public void dispose() {
        game.batch.dispose();
        game.font.dispose();
        game.getGameManager().getLevel().dispose();
    }
}
