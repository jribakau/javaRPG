package com.mygdx.game.events.level;

import com.mygdx.game.events.Event;
import lombok.Getter;

/**
 * Event to load a specific level
 */
@Getter
public class LoadLevelEvent extends Event {
    private final int levelId;

    public LoadLevelEvent(int levelId) {
        this.levelId = levelId;
    }
}

