package com.mygdx.game.commandManager.commands;

import com.mygdx.game.commandManager.Command;
import com.mygdx.game.enums.CommandEnum;

/**
 * Command to toggle event logging on/off
 */
public class ToggleEventLogging extends Command {

    public ToggleEventLogging() {
        super(CommandEnum.TOGGLE_EVENT_LOGGING);
    }

    @Override
    public void execute() {
        eventBus.getEventLogger().setEnabled(!eventBus.getEventLogger().isEnabled());
    }
}

