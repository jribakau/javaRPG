package com.mygdx.game.entity;

import com.mygdx.game.assets.types.CharacterType;
import com.mygdx.game.entity.components.*;
import lombok.Getter;

/**
 * Player - Represents the player character
 * This is a specialized Entity with player-specific functionality
 */
@Getter
public class Player extends Entity {
    private final PlayerComponent playerComponent;
    private final InputComponent inputComponent;
    private final StatsComponent statsComponent;
    private final MovementComponent movementComponent;
    private final PositionComponent positionComponent;
    private final RenderComponent renderComponent;
    private final InventoryComponent inventoryComponent;

    private Player(Builder builder) {
        super();

        // Add position and size
        this.positionComponent = new PositionComponent(
            builder.x,
            builder.y,
            builder.width,
            builder.height
        );
        addComponent(positionComponent);

        // Add render component with sprite
        this.renderComponent = new RenderComponent(builder.sprite);
        addComponent(renderComponent);

        // Add movement
        this.movementComponent = new MovementComponent(builder.moveSpeed);
        addComponent(movementComponent);

        // Add stats
        this.statsComponent = new StatsComponent(builder.maxHealth, builder.maxMana);
        statsComponent.setLevel(builder.level);
        statsComponent.setAttack(builder.attack);
        statsComponent.setDefense(builder.defense);
        statsComponent.setMagic(builder.magic);
        addComponent(statsComponent);

        // Add player-specific components
        this.playerComponent = new PlayerComponent(builder.name);
        playerComponent.setGold(builder.gold);
        addComponent(playerComponent);

        // Add input handling
        this.inputComponent = new InputComponent();
        addComponent(inputComponent);

        // Add inventory
        this.inventoryComponent = new InventoryComponent(builder.inventorySize);
        addComponent(inventoryComponent);
    }

    /**
     * Builder pattern for creating Player entities
     */
    public static class Builder {
        // Required parameters
        private final String name;
        private final float x;
        private final float y;

        // Optional parameters with defaults
        private com.badlogic.gdx.graphics.g2d.TextureRegion sprite;
        private float width = 32;
        private float height = 32;
        private float moveSpeed = 200f;
        private int maxHealth = 100;
        private int maxMana = 50;
        private int level = 1;
        private int attack = 10;
        private int defense = 5;
        private int magic = 5;
        private int gold = 0;
        private int inventorySize = 20;

        public Builder(String name, float x, float y) {
            this.name = name;
            this.x = x;
            this.y = y;
        }

        public Builder sprite(com.badlogic.gdx.graphics.g2d.TextureRegion sprite) {
            this.sprite = sprite;
            return this;
        }

        public Builder size(float width, float height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public Builder moveSpeed(float speed) {
            this.moveSpeed = speed;
            return this;
        }

        public Builder health(int maxHealth) {
            this.maxHealth = maxHealth;
            return this;
        }

        public Builder mana(int maxMana) {
            this.maxMana = maxMana;
            return this;
        }

        public Builder level(int level) {
            this.level = level;
            return this;
        }

        public Builder stats(int attack, int defense, int magic) {
            this.attack = attack;
            this.defense = defense;
            this.magic = magic;
            return this;
        }

        public Builder gold(int gold) {
            this.gold = gold;
            return this;
        }

        public Builder inventorySize(int size) {
            this.inventorySize = size;
            return this;
        }

        public Player build() {
            return new Player(this);
        }
    }

    // Convenience methods for common player operations

    public void takeDamage(int damage) {
        int actualDamage = Math.max(1, damage - statsComponent.getDefense());
        statsComponent.damage(actualDamage);
    }

    public void heal(int amount) {
        statsComponent.heal(amount);
    }

    public boolean isAlive() {
        return statsComponent.getHealth() > 0;
    }

    public boolean addItemToInventory(Entity item) {
        return inventoryComponent.addItem(item);
    }

    public boolean hasGold(int amount) {
        return playerComponent.getGold() >= amount;
    }

    public boolean spendGold(int amount) {
        return playerComponent.spendGold(amount);
    }

    public void addGold(int amount) {
        playerComponent.addGold(amount);
    }

    public void levelUp() {
        statsComponent.setLevel(statsComponent.getLevel() + 1);
        statsComponent.setMaxHealth(statsComponent.getMaxHealth() + 10);
        statsComponent.setMaxMana(statsComponent.getMaxMana() + 5);
        statsComponent.setAttack(statsComponent.getAttack() + 2);
        statsComponent.setDefense(statsComponent.getDefense() + 1);
        statsComponent.setMagic(statsComponent.getMagic() + 1);
        statsComponent.setHealth(statsComponent.getMaxHealth());
        statsComponent.setMana(statsComponent.getMaxMana());
    }

    public void gainExperience(int exp) {
        statsComponent.setExperience(statsComponent.getExperience() + exp);
        // Check for level up (simple example: 100 exp per level)
        int expForNextLevel = statsComponent.getLevel() * 100;
        if (statsComponent.getExperience() >= expForNextLevel) {
            statsComponent.setExperience(statsComponent.getExperience() - expForNextLevel);
            levelUp();
        }
    }
}