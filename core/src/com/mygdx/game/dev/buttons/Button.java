package com.mygdx.game.dev.buttons;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.dev.commands.Command;

public interface Button {
    TextButton createButton(Skin skin);
}
