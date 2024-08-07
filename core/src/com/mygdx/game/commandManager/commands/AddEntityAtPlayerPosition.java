package com.mygdx.game.commandManager.commands;

import com.mygdx.game.commandManager.Command;
import com.mygdx.game.entity.Character;
import com.mygdx.game.enums.CommandEnum;
import com.mygdx.game.enums.TextureTypeEnum;
import com.mygdx.game.gameManager.GameManager;

public class AddEntityAtPlayerPosition extends Command {
    private final GameManager gameManager;

    public AddEntityAtPlayerPosition(GameManager gameManager) {
        super(gameManager, CommandEnum.ADD_ENTITY_AT_PLAYER_POSITION);
        this.gameManager = gameManager;
    }

    @Override
    public void execute() {
        gameManager.getLevel().getCharacterList().add(new Character(gameManager.getLevel().getPlayer().getPosition().getX(), gameManager.getLevel().getPlayer().getPosition().getY()
                , gameManager.getAssetManager().getRandomTexture(TextureTypeEnum.ROGUE)));
    }
}
