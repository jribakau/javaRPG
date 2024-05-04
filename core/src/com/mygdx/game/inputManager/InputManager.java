package com.mygdx.game.inputManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.Player;
import com.mygdx.game.gameManager.GameManager;
import com.mygdx.game.levelManager.LevelEditorScreen;
import com.mygdx.game.mainMenuManager.MainMenuScreen;
import com.mygdx.game.utils.Keybindings;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InputManager extends InputAdapter {
    private GameManager gameManager;

    public void update() {
        Player player = gameManager.getLevel().getPlayer();
        float dx = 0, dy = 0;

        if (Gdx.input.isKeyPressed(Keybindings.LEFT_KEY)) {
            dx -= player.getAcceleration();
        }
        if (Gdx.input.isKeyPressed(Keybindings.RIGHT_KEY)) {
            dx += player.getAcceleration();
        }
        if (Gdx.input.isKeyPressed(Keybindings.UP_KEY)) {
            dy += player.getAcceleration();
        }
        if (Gdx.input.isKeyPressed(Keybindings.DOWN_KEY)) {
            dy -= player.getAcceleration();
        }

        if (dx != 0 || dy != 0) {
            player.move(dx, dy);
        } else {
            player.stop();
        }

        if (Gdx.input.isKeyJustPressed(Keybindings.DEBUG_KEY)) {
            toggleDebug();
        }
        if (Gdx.input.isKeyJustPressed(Keybindings.LEVEL_EDITOR_KEY)) {
            gameManager.getGame().setScreen(new LevelEditorScreen(gameManager.getGame()));
        }

        mouseOverEntity(Gdx.input.getX(), Gdx.input.getY());
        if (Gdx.input.isButtonPressed(Keybindings.LEFT_CLICK)) {
            leftClick(Gdx.input.getX(), Gdx.input.getY());
        }

        if (Gdx.input.isKeyPressed(Keybindings.ESCAPE_KEY)) {
            gameManager.getGame().setScreen(new MainMenuScreen(gameManager.getGame()));
        }
    }

    private void toggleDebug() {
        gameManager.setDebug(!gameManager.isDebug());
    }

    public void mouseOverEntity(int screenX, int screenY) {
        Vector3 worldCoordinates = gameManager.getCameraManager().getCamera().unproject(new Vector3(screenX, screenY, 0));
        for (Entity entity : gameManager.getLevel().getEntitiesInView()) {
            entity.setHighlight(entity.containsPoint(worldCoordinates.x, worldCoordinates.y));
        }
    }

    public void leftClick(int screenX, int screenY) {

    }
}
