//package com.mygdx.game.old_code_base.commandManager;
//
//import com.mygdx.game.old_code_base.commandManager.commands.*;
//import com.mygdx.game.old_code_base.events.EventBus;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.ArrayList;
//
//@Getter
//@Setter
//public class CommandManager {
//    private final EventBus eventBus;
//    private final ArrayList<Command> commandList;
//
//    public CommandManager() {
//        this.eventBus = EventBus.getInstance();
//        commandList = new ArrayList<>();
//    }
//
//    public void init() {
//        commandList.add(new RemoveEntityById());
//        commandList.add(new LoadLevel1());
//        commandList.add(new GenerateRandomTiles());
//        commandList.add(new ToggleEntityDebugBox());
//        commandList.add(new RemoveLastEntity());
//        commandList.add(new AddEntityAtPlayerPosition());
//
//        commandList.add(new PrintEventStats());
//        commandList.add(new ClearEventLog());
//        commandList.add(new ToggleEventLogging());
//    }
//
//}