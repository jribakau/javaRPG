package com.mygdx.game.developerOptions.buttons;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.gameManager.GameManager;
import com.mygdx.game.developerOptions.commands.Command;
import com.mygdx.game.developerOptions.commands.LoadLevel_1;

public class LoadLevel_1Button implements Button{
    private final Command command;

    public LoadLevel_1Button(GameManager gameManager) {
        this.command = new LoadLevel_1(gameManager);
    }

    @Override
    public TextButton createButton(Skin skin) {
        TextButton addButton = new TextButton("Load Level 1", skin);
        addButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                command.execute();
            }
        });
        return addButton;
    }
}
