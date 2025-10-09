package com.mygdx.game.events.ui;

import com.badlogic.gdx.Screen;
import com.mygdx.game.events.Event;
import lombok.Getter;

/**
 * Event triggered when the screen should change
 */
@Getter
public class ScreenChangeEvent extends Event {
    private final Screen newScreen;

    public ScreenChangeEvent(Screen newScreen) {
        this.newScreen = newScreen;
    }
}

