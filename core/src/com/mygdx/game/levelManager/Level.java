package com.mygdx.game.levelManager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.assetManager.AssetManager;
import com.mygdx.game.entity.Character;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.Player;
import com.mygdx.game.entity.Tile;
import com.mygdx.game.enums.RogueTypeEnum;
import com.mygdx.game.utils.Constants;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Level {
    private List<Character> characterList;
    private Player player;
    private List<Tile> tileList;
    private AssetManager assetManager;

    public Level(AssetManager assetManager) {
        this.player = new Player(0, 0, assetManager.getRogueTextureByIndex(RogueTypeEnum.FEMALE_WIZARD.name()));
        this.characterList = new ArrayList<>();
        this.tileList = new ArrayList<>();
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

    public void draw(SpriteBatch batch) {
        for (Entity entity : tileList) {
            if (entity.getIsVisible()) {
                entity.draw(batch);
            }
        }
        for (Entity entity : characterList) {
            entity.getEntityBehavior().update(entity);
            if (entity.getIsVisible()) {
                entity.draw(batch);
            }
        }
        if (player.getIsVisible()) {
            player.draw(batch);
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
                Tile tile = new Tile(i * Constants.TILE_WIDTH, j * Constants.TILE_HEIGHT, assetManager.getRandomTileTexture());
                tileList.add(tile);
            }
        }
    }

    public void clearTileList() {
        tileList.clear();
    }
}
