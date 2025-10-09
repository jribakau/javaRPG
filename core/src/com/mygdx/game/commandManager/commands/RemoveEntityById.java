package com.mygdx.game.commandManager.commands;

import com.mygdx.game.commandManager.Command;
import com.mygdx.game.enums.CommandEnum;
import com.mygdx.game.events.entity.RemoveEntityEvent;

public class RemoveEntityById extends Command {

    public RemoveEntityById() {
        super(CommandEnum.REMOVE_ENTITY_BY_ID);
    }

    @Override
    public void execute() {
        // This would need an entity ID parameter in a real implementation
        // For now, it's a placeholder that can be extended
    }

    public void execute(String entityId) {
        eventBus.publish(new RemoveEntityEvent(entityId));
    }
}
