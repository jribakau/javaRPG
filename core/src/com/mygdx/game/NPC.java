package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;

public class NPC extends Entity {
    private String dialogue;

    public NPC(String name, int level, int health, Rectangle position, String dialogue) {
        super(name, level, health, position);
        this.dialogue = dialogue;
    }

    public String getDialogue() {
        return dialogue;
    }

    public void setDialogue(String dialogue) {
        this.dialogue = dialogue;
    }
}
