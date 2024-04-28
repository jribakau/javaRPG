package com.mygdx.game.commands;

import com.mygdx.game.core.GameManager;

public class RemoveEntityCommand implements Command {
    private final GameManager gameManager;

    public RemoveEntityCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void execute() {
        gameManager.getEntities().remove(gameManager.getEntities().size() - 1);
    }
}