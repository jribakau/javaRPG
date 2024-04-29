package com.mygdx.game.level;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.NPC;
import com.mygdx.game.entities.Player;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Level {
    private List<NPC> npcList;
    private Texture mapTexture;
    private Player player;

    public Level() {
        this.player = new Player(0, 0);
        this.npcList = new ArrayList<>();
        this.mapTexture = new Texture("map.jpg");
    }

    public void dispose() {
        for (Entity entity : npcList) {
            entity.dispose();
        }
        if (player != null) {
            player.dispose();
        }
        mapTexture.dispose();
    }

    public void draw(SpriteBatch batch) {
        batch.draw(mapTexture, 0, 0);
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
}
