package com.mygdx.game.entity.components;

import com.mygdx.game.entity.Component;
import lombok.Getter;
import lombok.Setter;

/**
 * StatsComponent - Holds entity statistics (HP, MP, etc.)
 */
@Getter
@Setter
public class StatsComponent extends Component {
    private int health;
    private int maxHealth;
    private int mana;
    private int maxMana;
    private int level;
    private int experience;

    // Combat stats
    private int attack;
    private int defense;
    private int magic;

    public StatsComponent(int maxHealth, int maxMana) {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.maxMana = maxMana;
        this.mana = maxMana;
        this.level = 1;
        this.experience = 0;
        this.attack = 10;
        this.defense = 5;
        this.magic = 5;
    }

    public void damage(int amount) {
        health = Math.max(0, health - amount);
    }

    public void heal(int amount) {
        health = Math.min(maxHealth, health + amount);
    }

    public void useMana(int amount) {
        mana = Math.max(0, mana - amount);
    }

    public void restoreMana(int amount) {
        mana = Math.min(maxMana, mana + amount);
    }

    public boolean isAlive() {
        return health > 0;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public float getHealthPercent() {
        return (float) health / maxHealth;
    }

    public float getManaPercent() {
        return (float) mana / maxMana;
    }
}

