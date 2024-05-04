package com.mygdx.game.commandManager.commands;

import com.mygdx.game.commandManager.Command;
import com.mygdx.game.enums.CommandEnum;
import com.mygdx.game.gameManager.GameManager;

public class RemoveLastEntity extends Command {
    private final GameManager gameManager;

    public RemoveLastEntity(GameManager gameManager) {
        super(gameManager, CommandEnum.REMOVE_LAST_ENTITY);
        this.gameManager = gameManager;
    }

    @Override
    public void execute() {
        if (!gameManager.getLevel().getCharacterList().isEmpty()) {
            gameManager.getLevel().getCharacterList().remove(gameManager.getLevel().getCharacterList().size() - 1);
        }
    }
}