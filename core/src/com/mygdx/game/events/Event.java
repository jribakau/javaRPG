package com.mygdx.game.events;

import lombok.Getter;
import lombok.Setter;

/**
 * Base class for all events in the event-driven architecture.
 * Provides common functionality for event handling.
 */
@Getter
@Setter
public abstract class Event {
    /**
     * -- GETTER --
     *  Gets the timestamp when this event was created.
     *
     */
    private final long timestamp;
    /**
     * -- GETTER --
     *  Checks if this event has been cancelled.
     *
     */
    private boolean cancelled;

    public Event() {
        this.timestamp = System.currentTimeMillis();
        this.cancelled = false;
    }
}
