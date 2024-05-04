package com.mygdx.game.commandManager.commands;

import com.mygdx.game.commandManager.Command;
import com.mygdx.game.enums.CommandEnum;
import com.mygdx.game.gameManager.GameManager;

public class GenerateRandomTiles extends Command {
    private final GameManager gameManager;

    public GenerateRandomTiles(GameManager gameManager) {
        super(gameManager, CommandEnum.GENERATE_RANDOM_TILES);
        this.gameManager = gameManager;
    }

    @Override
    public void execute() {
        gameManager.getLevel().getTileList().clear();
        gameManager.getLevel().generateLevel();
    }
}
