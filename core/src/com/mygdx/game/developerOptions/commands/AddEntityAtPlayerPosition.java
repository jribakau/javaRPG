package com.mygdx.game.developerOptions.commands;

import com.mygdx.game.entity.Character;
import com.mygdx.game.gameManager.GameManager;

public class AddEntityAtPlayerPosition implements Command {
    private final GameManager gameManager;

    public AddEntityAtPlayerPosition(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void execute() {
        gameManager.getLevel().getCharacterList().add(new Character(gameManager.getLevel().getPlayer().getPosition().getX(), gameManager.getLevel().getPlayer().getPosition().getY()
                , gameManager.getAssetManager().getRandomRogueTexture()));
    }
}
