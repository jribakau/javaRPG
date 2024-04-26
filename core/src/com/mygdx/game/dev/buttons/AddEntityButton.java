package com.mygdx.game.dev.buttons;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.RPG;
import com.mygdx.game.commands.AddEntityCommand;
import com.mygdx.game.commands.Command;
import com.mygdx.game.core.GameManager;
import com.mygdx.game.entities.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddEntityButton implements Button<AddEntityCommand> {
    private final Command command;

    public AddEntityButton(GameManager gameManager) {
        this.command = new AddEntityCommand(gameManager);
    }

    @Override
    public TextButton createButton(Skin skin) {
        TextButton addButton = new TextButton("Add Entity", skin);
        addButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                command.execute();
            }
        });
        return addButton;
    }
}