package com.mygdx.game.commands;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.core.GameManager;
import com.mygdx.game.entities.Entity;

public class AddEntityCommand implements Command {
    private final GameManager gameManager;

    public AddEntityCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void execute() {
        gameManager.getEntities().add(new Entity(new Rectangle(gameManager.getPlayer().getX(), gameManager.getPlayer().getY(), 64 , 64)));
    }
}
