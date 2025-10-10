//package com.mygdx.game.old_code_base.commandManager.commands;
//
//import com.mygdx.game.old_code_base.commandManager.Command;
//import com.mygdx.game.old_code_base.enums.CommandEnum;
//
///**
// * Command to print event statistics to the console
// */
//public class PrintEventStats extends Command {
//
//    public PrintEventStats() {
//        super(CommandEnum.PRINT_EVENT_STATS);
//    }
//
//    @Override
//    public void execute() {
//        eventBus.getEventLogger().printStatistics();
//    }
//}