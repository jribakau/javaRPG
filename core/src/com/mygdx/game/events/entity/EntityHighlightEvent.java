package com.mygdx.game.events.entity;

import com.mygdx.game.events.Event;
import lombok.Getter;

/**
 * Event triggered when mouse hovers over entities
 */
@Getter
public class EntityHighlightEvent extends Event {
    private final float worldX;
    private final float worldY;

    public EntityHighlightEvent(float worldX, float worldY) {
        this.worldX = worldX;
        this.worldY = worldY;
    }
}

