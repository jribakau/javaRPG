package com.mygdx.game.dev.commands;

import com.mygdx.game.core.GameManager;

public class ToggleEntityDebugBox implements Command {
    private final GameManager gameManager;

    public ToggleEntityDebugBox(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void execute() {
        gameManager.setEntityDebug(!gameManager.isEntityDebug());
    }
}
