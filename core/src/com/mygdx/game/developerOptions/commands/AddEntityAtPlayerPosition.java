package com.mygdx.game.developerOptions.commands;

import com.mygdx.game.gameManager.GameManager;
import com.mygdx.game.entity.VirtualCharacter;

public class AddEntityAtPlayerPosition implements Command {
    private final GameManager gameManager;

    public AddEntityAtPlayerPosition(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void execute() {
        gameManager.getLevel().getVirtualCharacterList().add(new VirtualCharacter(gameManager.getLevel().getPlayer().getX(), gameManager.getLevel().getPlayer().getY()
                , gameManager.getAssetManager().getRandomRogueTexture()));
    }
}
