package com.mygdx.game.commandManager;

import com.mygdx.game.enums.CommandEnum;
import com.mygdx.game.events.EventBus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Command {
    protected final EventBus eventBus;
    private final CommandEnum commandName;

    public Command(CommandEnum commandName) {
        this.eventBus = EventBus.getInstance();
        this.commandName = commandName;
    }

    public abstract void execute();
}
