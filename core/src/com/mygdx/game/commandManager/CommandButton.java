package com.mygdx.game.commandManager;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.developerOptions.Button;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommandButton implements Button {
    private final Command command;
    private final String name;

    public CommandButton(Command command, String name) {
        this.command = command;
        this.name = name;
    }

    @Override
    public TextButton createButton(Skin skin) {
        TextButton commandButton = new TextButton(name, skin);
        commandButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                command.execute();
            }
        });
        return commandButton;
    }
}
