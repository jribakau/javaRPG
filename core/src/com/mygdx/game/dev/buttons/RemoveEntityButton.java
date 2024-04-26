package com.mygdx.game.dev.buttons;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.commands.Command;
import com.mygdx.game.commands.RemoveEntityCommand;
import com.mygdx.game.core.GameManager;

public class RemoveEntityButton implements Button<RemoveEntityCommand> {
    private final Command command;

    public RemoveEntityButton(GameManager gameManager) {
        this.command = new RemoveEntityCommand(gameManager);
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
