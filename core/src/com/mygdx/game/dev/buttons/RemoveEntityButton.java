package com.mygdx.game.dev.buttons;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.core.GameManager;
import com.mygdx.game.dev.commands.Command;
import com.mygdx.game.dev.commands.RemoveLastEntity;

public class RemoveEntityButton implements Button<RemoveLastEntity> {
    private final Command command;

    public RemoveEntityButton(GameManager gameManager) {
        this.command = new RemoveLastEntity(gameManager);
    }

    @Override
    public TextButton createButton(Skin skin) {
        TextButton addButton = new TextButton("Remove Entity", skin);
        addButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                command.execute();
            }
        });
        return addButton;
    }
}
