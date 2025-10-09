package com.mygdx.game.commandManager.commands;

import com.mygdx.game.commandManager.Command;
import com.mygdx.game.enums.CommandEnum;

/**
 * Command to print event statistics to the console
 */
public class PrintEventStats extends Command {

    public PrintEventStats() {
        super(CommandEnum.PRINT_EVENT_STATS);
    }

    @Override
    public void execute() {
        eventBus.getEventLogger().printStatistics();
    }
}