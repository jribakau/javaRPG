package com.mygdx.game.developerOptions.commands;

import com.mygdx.game.gameManager.GameManager;

public class LoadLevel_1 implements Command {
    private final GameManager gameManager;

    public LoadLevel_1(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void execute() {
        gameManager.getLevel().clearTileList();
        gameManager.getLevel().setTileList(gameManager.getAssetManager().getLevels().get(0));
    }
}
