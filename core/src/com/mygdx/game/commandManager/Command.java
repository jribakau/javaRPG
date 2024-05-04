package com.mygdx.game.commandManager;

import com.mygdx.game.enums.CommandEnum;
import com.mygdx.game.gameManager.GameManager;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Command {
    private final GameManager gameManager;
    private final CommandEnum commandName;

    public Command(GameManager gameManager, CommandEnum commandName) {
        this.gameManager = gameManager;
        this.commandName = commandName;
    }

    public abstract void execute();
}
