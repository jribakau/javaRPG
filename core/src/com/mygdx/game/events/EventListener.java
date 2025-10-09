package com.mygdx.game.events;

/**
 * Interface for objects that want to listen to events.
 */
@FunctionalInterface
public interface EventListener<T extends Event> {
    void onEvent(T event);
}

