package com.mygdx.game.developerOptions.commands;

import com.mygdx.game.gameManager.GameManager;

public class GenerateLevel implements Command {
    private final GameManager gameManager;

    public GenerateLevel(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void execute() {
        gameManager.getLevel().getTileList().clear();
        gameManager.getLevel().generateLevel();
    }
}
