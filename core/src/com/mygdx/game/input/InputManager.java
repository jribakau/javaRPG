package com.mygdx.game.input;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.gameManager.GameManager;
import com.mygdx.game.entity.Player;
import com.mygdx.game.utils.Keybindings;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InputManager {
    private GameManager gameManager;

    public InputManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

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
    }

    private void toggleDebug() {
        gameManager.setDebug(!gameManager.isDebug());
    }
}
