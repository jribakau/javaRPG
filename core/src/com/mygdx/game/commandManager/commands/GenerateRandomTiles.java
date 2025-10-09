package com.mygdx.game.commandManager.commands;

import com.mygdx.game.commandManager.Command;
import com.mygdx.game.enums.CommandEnum;
import com.mygdx.game.events.level.GenerateRandomTilesEvent;

public class GenerateRandomTiles extends Command {

    public GenerateRandomTiles() {
        super(CommandEnum.GENERATE_RANDOM_TILES);
    }

    @Override
    public void execute() {
        eventBus.publish(new GenerateRandomTilesEvent());
    }
}
