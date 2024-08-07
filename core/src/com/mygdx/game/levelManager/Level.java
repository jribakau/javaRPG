package com.mygdx.game.levelManager;

import com.mygdx.game.assetManager.AssetManager;
import com.mygdx.game.entity.Character;
import com.mygdx.game.entity.*;
import com.mygdx.game.enums.RogueTypeEnum;
import com.mygdx.game.enums.TextureTypeEnum;
import com.mygdx.game.utils.Constants;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Level {
    private Player player;
    private List<Character> characterList;
    private List<Tile> tileList;
    private List<Item> itemList;

    private AssetManager assetManager;

    public Level(AssetManager assetManager) {
        this.player = new Player(0, 0, assetManager.getTextureByIndex(TextureTypeEnum.ROGUE, RogueTypeEnum.FEMALE_WIZARD.name()));
        this.characterList = new ArrayList<>();
        this.tileList = new ArrayList<>();
        this.itemList = new ArrayList<>();
        this.assetManager = assetManager;
    }

    public void dispose() {
        for (Entity entity : characterList) {
            entity.dispose();
        }
        if (player != null) {
            player.dispose();
        }
    }

    public List<Entity> getEntitiesInView() {
        List<Entity> entitiesInView = new ArrayList<>();
        for (Entity entity : this.getCharacterList()) {
            if (entity.getIsVisible()) {
                entitiesInView.add(entity);
            }
        }
        return entitiesInView;
    }

    public void generateLevel() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Tile tile = new Tile(i * Constants.TILE_WIDTH, j * Constants.TILE_HEIGHT, assetManager.getRandomTexture(TextureTypeEnum.TILE));
                tileList.add(tile);
            }
        }
    }

    public void clearTileList() {
        tileList.clear();
    }
}
