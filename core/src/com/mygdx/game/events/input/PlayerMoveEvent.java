package com.mygdx.game.events.input;

import com.mygdx.game.events.Event;
import lombok.Getter;
import lombok.Setter;

/**
 * Event triggered when the player moves
 */
@Getter
@Setter
public class PlayerMoveEvent extends Event {
    private final float dx;
    private final float dy;

    public PlayerMoveEvent(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
    }
}