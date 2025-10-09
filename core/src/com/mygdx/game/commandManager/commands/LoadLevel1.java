package com.mygdx.game.commandManager.commands;

import com.mygdx.game.commandManager.Command;
import com.mygdx.game.enums.CommandEnum;
import com.mygdx.game.events.level.LoadLevelEvent;

public class LoadLevel1 extends Command {

    public LoadLevel1() {
        super(CommandEnum.LOAD_LEVEL_1);
    }

    @Override
    public void execute() {
        eventBus.publish(new LoadLevelEvent(0));
    }
}
