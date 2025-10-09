package com.mygdx.game.commandManager.commands;

import com.mygdx.game.commandManager.Command;
import com.mygdx.game.enums.CommandEnum;

/**
 * Command to clear the event log
 */
public class ClearEventLog extends Command {

    public ClearEventLog() {
        super(CommandEnum.CLEAR_EVENT_LOG);
    }

    @Override
    public void execute() {
        eventBus.getEventLogger().clear();
    }
}

