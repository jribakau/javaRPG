package com.mygdx.game.commandManager.commands;

import com.mygdx.game.commandManager.Command;
import com.mygdx.game.enums.CommandEnum;
import com.mygdx.game.events.entity.RemoveLastEntityEvent;

public class RemoveLastEntity extends Command {

    public RemoveLastEntity() {
        super(CommandEnum.REMOVE_LAST_ENTITY);
    }

    @Override
    public void execute() {
        eventBus.publish(new RemoveLastEntityEvent());
    }
}