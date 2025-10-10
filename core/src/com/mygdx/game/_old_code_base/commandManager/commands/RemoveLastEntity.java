//package com.mygdx.game.old_code_base.commandManager.commands;
//
//import com.mygdx.game.old_code_base.commandManager.Command;
//import com.mygdx.game.old_code_base.enums.CommandEnum;
//import com.mygdx.game.old_code_base.events.entity.RemoveLastEntityEvent;
//
//public class RemoveLastEntity extends Command {
//
//    public RemoveLastEntity() {
//        super(CommandEnum.REMOVE_LAST_ENTITY);
//    }
//
//    @Override
//    public void execute() {
//        eventBus.publish(new RemoveLastEntityEvent());
//    }
//}