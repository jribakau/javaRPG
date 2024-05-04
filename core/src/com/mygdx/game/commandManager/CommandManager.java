package com.mygdx.game.commandManager;

import com.mygdx.game.commandManager.commands.*;
import com.mygdx.game.gameManager.GameManager;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class CommandManager {
    private GameManager gameManager;
    private final ArrayList<Command> commandList;

    public CommandManager() {
        commandList = new ArrayList<>();
    }

    public void init() {
        commandList.add(new RemoveEntityById(this.gameManager));
        commandList.add(new LoadLevel1(this.gameManager));
        commandList.add(new GenerateRandomTiles(this.gameManager));
        commandList.add(new ToggleEntityDebugBox(this.gameManager));
        commandList.add(new RemoveLastEntity(this.gameManager));
        commandList.add(new AddEntityAtPlayerPosition(this.gameManager));
    }
}