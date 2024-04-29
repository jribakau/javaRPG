package com.mygdx.game.dev.commands;

import com.mygdx.game.core.GameManager;
import com.mygdx.game.entities.NPC;

public class AddEntityAtPlayerPosition implements Command {
    private final GameManager gameManager;

    public AddEntityAtPlayerPosition(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void execute() {
        gameManager.getLevel().getNpcList().add(new NPC(gameManager.getLevel().getPlayer().getX(), gameManager.getLevel().getPlayer().getY()
                , gameManager.getAssetManager().getRandomRogueTexture()));
    }
}
