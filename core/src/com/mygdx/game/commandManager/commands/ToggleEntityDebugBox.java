package com.mygdx.game.commandManager.commands;

import com.mygdx.game.commandManager.Command;
import com.mygdx.game.enums.CommandEnum;
import com.mygdx.game.gameManager.GameManager;

public class ToggleEntityDebugBox extends Command {
    private final GameManager gameManager;

    public ToggleEntityDebugBox(GameManager gameManager) {
        super(gameManager, CommandEnum.TOGGLE_ENTITY_DEBUG_BOX);
        this.gameManager = gameManager;
    }

    @Override
    public void execute() {
        gameManager.setEntityDebug(!gameManager.isEntityDebug());
    }
}
