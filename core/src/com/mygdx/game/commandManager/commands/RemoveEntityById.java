package com.mygdx.game.commandManager.commands;

import com.mygdx.game.commandManager.Command;
import com.mygdx.game.enums.CommandEnum;
import com.mygdx.game.gameManager.GameManager;

public class RemoveEntityById extends Command {
    private final GameManager gameManager;

    public RemoveEntityById(GameManager gameManager) {
        super(gameManager, CommandEnum.REMOVE_ENTITY_BY_ID);
        this.gameManager = gameManager;
    }

    @Override
    public void execute() {

    }
}
