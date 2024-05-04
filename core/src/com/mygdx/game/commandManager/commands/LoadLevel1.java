package com.mygdx.game.commandManager.commands;

import com.mygdx.game.commandManager.Command;
import com.mygdx.game.enums.CommandEnum;
import com.mygdx.game.gameManager.GameManager;

public class LoadLevel1 extends Command {
    private final GameManager gameManager;

    public LoadLevel1(GameManager gameManager) {
        super(gameManager, CommandEnum.LOAD_LEVEL_1);
        this.gameManager = gameManager;
    }

    @Override
    public void execute() {
        gameManager.getLevel().clearTileList();
        gameManager.getLevel().setTileList(gameManager.getAssetManager().getLevels().get(0));
    }
}
