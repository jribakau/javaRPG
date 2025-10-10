package com.mygdx.game.entity.components;

import com.mygdx.game.entity.Component;
import lombok.Getter;
import lombok.Setter;

/**
 * PlayerComponent - Marks an entity as the player and holds player-specific data
 */
@Getter
@Setter
public class PlayerComponent extends Component {
    private String name;
    private int gold;
    private int score;

    public PlayerComponent(String name) {
        this.name = name;
        this.gold = 0;
        this.score = 0;
    }

    public void addGold(int amount) {
        this.gold += amount;
    }

    public boolean spendGold(int amount) {
        if (gold >= amount) {
            gold -= amount;
            return true;
        }
        return false;
    }

    public void addScore(int points) {
        this.score += points;
    }
}