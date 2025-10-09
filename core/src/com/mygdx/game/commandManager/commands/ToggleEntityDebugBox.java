package com.mygdx.game.commandManager.commands;

import com.mygdx.game.commandManager.Command;
import com.mygdx.game.enums.CommandEnum;
import com.mygdx.game.events.ui.ToggleEntityDebugEvent;

public class ToggleEntityDebugBox extends Command {

    public ToggleEntityDebugBox() {
        super(CommandEnum.TOGGLE_ENTITY_DEBUG_BOX);
    }

    @Override
    public void execute() {
        eventBus.publish(new ToggleEntityDebugEvent());
    }
}
