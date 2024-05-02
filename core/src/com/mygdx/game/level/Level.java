package com.mygdx.game.level;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.assetManager.AssetManager;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.NPC;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.Tile;
import com.mygdx.game.enums.RogueTypeEnum;
import com.mygdx.game.utils.Constants;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Level {
    private List<NPC> npcList;
    private Player player;
    private List<Tile> tileList;
    private AssetManager assetManager;

    public Level(AssetManager assetManager) {
        this.player = new Player(0, 0, assetManager.getRogueTextureByIndex(RogueTypeEnum.FEMALE_WIZARD.name()));
        this.npcList = new ArrayList<>();
        this.tileList = new ArrayList<>();
        this.assetManager = assetManager;
    }

    public void dispose() {
        for (Entity entity : npcList) {
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
        for (Entity entity : npcList) {
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
        for (Entity entity : this.getNpcList()) {
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
