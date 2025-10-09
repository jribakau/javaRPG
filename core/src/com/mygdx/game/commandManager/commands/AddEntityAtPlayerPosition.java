package com.mygdx.game.commandManager.commands;

import com.mygdx.game.commandManager.Command;
import com.mygdx.game.enums.CommandEnum;
import com.mygdx.game.events.entity.AddEntityAtPlayerEvent;

public class AddEntityAtPlayerPosition extends Command {

    public AddEntityAtPlayerPosition() {
        super(CommandEnum.ADD_ENTITY_AT_PLAYER_POSITION);
    }

    @Override
    public void execute() {
        eventBus.publish(new AddEntityAtPlayerEvent());
    }
}
