package com.mygdx.game.dev.buttons;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.core.GameManager;
import com.mygdx.game.dev.commands.Command;
import com.mygdx.game.dev.commands.ToggleEntityDebugBox;

public class TogglePlayerBoxVisibility implements Button {
    private final Command command;

    public TogglePlayerBoxVisibility(GameManager gameManager) {
        this.command = new ToggleEntityDebugBox(gameManager);
    }

    @Override
    public TextButton createButton(Skin skin) {
        TextButton addButton = new TextButton("Toggle collision/interaction vis", skin);
        addButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                command.execute();
            }
        });
        return addButton;
    }
}
