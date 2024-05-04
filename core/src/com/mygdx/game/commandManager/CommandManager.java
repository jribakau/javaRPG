package com.mygdx.game.commandManager;

import com.mygdx.game.commandManager.commands.*;
import com.mygdx.game.gameManager.GameManager;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class CommandManager {
    private final ArrayList<Command> commandList;

    public CommandManager(GameManager gameManager) {
        commandList = new ArrayList<>();
        commandList.add(new RemoveEntityById(gameManager));
        commandList.add(new LoadLevel1(gameManager));
        commandList.add(new GenerateRandomTiles(gameManager));
        commandList.add(new ToggleEntityDebugBox(gameManager));
        commandList.add(new RemoveLastEntity(gameManager));
        commandList.add(new AddEntityAtPlayerPosition(gameManager));
    }
}