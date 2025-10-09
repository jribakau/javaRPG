package com.mygdx.game.events.command;

import com.mygdx.game.enums.CommandEnum;
import com.mygdx.game.events.Event;
import lombok.Getter;

/**
 * Event triggered when a command should be executed
 */
@Getter
public class CommandExecuteEvent extends Event {
    private final CommandEnum commandType;
    private final Object[] parameters;

    public CommandExecuteEvent(CommandEnum commandType, Object... parameters) {
        this.commandType = commandType;
        this.parameters = parameters;
    }
}

