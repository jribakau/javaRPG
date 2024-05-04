package com.mygdx.game.developerOptions.commands;

import com.mygdx.game.gameManager.GameManager;

public class RemoveLastEntity implements Command {
    private final GameManager gameManager;

    public RemoveLastEntity(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void execute() {
        if (!gameManager.getLevel().getCharacterList().isEmpty()) {
            gameManager.getLevel().getCharacterList().remove(gameManager.getLevel().getCharacterList().size() - 1);
        }
    }
}