package com.mygdx.game.developerOptions.buttons;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.developerOptions.commands.Command;
import com.mygdx.game.developerOptions.commands.RemoveLastEntity;
import com.mygdx.game.gameManager.GameManager;

public class RemoveEntityButton implements Button {
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
