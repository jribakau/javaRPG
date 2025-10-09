package com.mygdx.game.events.entity;

import com.mygdx.game.events.Event;
import lombok.Getter;

/**
 * Event triggered when an entity is clicked
 */
@Getter
public class EntityClickEvent extends Event {
    private final float worldX;
    private final float worldY;

    public EntityClickEvent(float worldX, float worldY) {
        this.worldX = worldX;
        this.worldY = worldY;
    }
}

