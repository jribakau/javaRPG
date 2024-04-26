package com.mygdx.game.dev.buttons;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.commands.Command;

public interface Button<T extends Command>{
    TextButton createButton(Skin skin);
}
