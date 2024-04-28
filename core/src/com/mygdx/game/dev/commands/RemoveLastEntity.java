package com.mygdx.game.dev.commands;

import com.mygdx.game.core.GameManager;

public class RemoveLastEntity implements Command {
    private final GameManager gameManager;

    public RemoveLastEntity(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void execute() {
        if (!gameManager.getLevel().getNpcList().isEmpty()) {
            gameManager.getLevel().getNpcList().remove(gameManager.getLevel().getNpcList().size() - 1);
        }
    }
}