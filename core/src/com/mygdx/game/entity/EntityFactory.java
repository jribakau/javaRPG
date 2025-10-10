package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.assets.AnimalType;
import com.mygdx.game.assets.AssetManager;
import com.mygdx.game.assets.CharacterType;
import com.mygdx.game.assets.ItemType;
import com.mygdx.game.assets.MonsterType;
import com.mygdx.game.entity.components.MovementComponent;
import com.mygdx.game.entity.components.PositionComponent;
import com.mygdx.game.entity.components.RenderComponent;
import com.mygdx.game.entity.components.StatsComponent;

/**
 * EntityFactory - Convenient factory for creating pre-configured entities
 * Makes it easy to spawn players, monsters, NPCs, items, etc. with proper sprites
 */
public class EntityFactory {
    private final AssetManager assetManager;

    public EntityFactory(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    /**
     * Create a player entity with a specific character type
     */
    public Entity createPlayer(float x, float y, CharacterType characterType) {
        Entity player = new Entity();

        TextureRegion sprite = assetManager.getCharacterSprite(characterType);

        player.addComponent(new PositionComponent(x, y, 32, 32));
        player.addComponent(new RenderComponent(sprite));
        player.addComponent(new MovementComponent(200f));

        StatsComponent stats = new StatsComponent(100, 50);
        stats.setLevel(1);
        player.addComponent(stats);

        return player;
    }

    /**
     * Create a monster entity
     */
    public Entity createMonster(float x, float y, MonsterType monsterType, int health, int level) {
        Entity monster = new Entity();

        TextureRegion sprite = assetManager.getMonsterSprite(monsterType);

        monster.addComponent(new PositionComponent(x, y, 32, 32));
        monster.addComponent(new RenderComponent(sprite));
        monster.addComponent(new MovementComponent(100f));

        StatsComponent stats = new StatsComponent(health, 0);
        stats.setLevel(level);
        monster.addComponent(stats);

        return monster;
    }

    /**
     * Create an NPC entity
     */
    public Entity createNPC(float x, float y, CharacterType characterType, String name) {
        Entity npc = new Entity();

        TextureRegion sprite = assetManager.getCharacterSprite(characterType);

        npc.addComponent(new PositionComponent(x, y, 32, 32));
        npc.addComponent(new RenderComponent(sprite));

        StatsComponent stats = new StatsComponent(50, 0);
        stats.setLevel(1);
        npc.addComponent(stats);

        return npc;
    }

    /**
     * Create an animal entity
     */
    public Entity createAnimal(float x, float y, AnimalType animalType) {
        Entity animal = new Entity();

        TextureRegion sprite = assetManager.getAnimalSprite(animalType);

        animal.addComponent(new PositionComponent(x, y, 32, 32));
        animal.addComponent(new RenderComponent(sprite));
        animal.addComponent(new MovementComponent(80f));

        StatsComponent stats = new StatsComponent(20, 0);
        stats.setLevel(1);
        animal.addComponent(stats);

        return animal;
    }

    /**
     * Create an item entity
     */
    public Entity createItem(float x, float y, ItemType itemType) {
        Entity item = new Entity();

        TextureRegion sprite = assetManager.getItemSprite(itemType);

        item.addComponent(new PositionComponent(x, y, 24, 24));
        item.addComponent(new RenderComponent(sprite));

        return item;
    }

    /**
     * Create a custom entity with a specific sprite
     */
    public Entity createCustomEntity(float x, float y, float width, float height, TextureRegion sprite) {
        Entity entity = new Entity();

        entity.addComponent(new PositionComponent(x, y, width, height));
        entity.addComponent(new RenderComponent(sprite));

        return entity;
    }
}
