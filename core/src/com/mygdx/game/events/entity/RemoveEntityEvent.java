package com.mygdx.game.events.entity;

import com.mygdx.game.events.Event;
import lombok.Getter;

/**
 * Event to remove an entity by ID
 */
@Getter
public class RemoveEntityEvent extends Event {
    private final String entityId;

    public RemoveEntityEvent(String entityId) {
        this.entityId = entityId;
    }
}

