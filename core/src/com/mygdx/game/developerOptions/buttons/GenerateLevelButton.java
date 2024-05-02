package com.mygdx.game.developerOptions.buttons;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.gameManager.GameManager;
import com.mygdx.game.developerOptions.commands.Command;
import com.mygdx.game.developerOptions.commands.GenerateLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerateLevelButton implements Button{
    private final Command command;

    public GenerateLevelButton(GameManager gameManager) {
        this.command = new GenerateLevel(gameManager);
    }

    @Override
    public TextButton createButton(Skin skin) {
        TextButton addButton = new TextButton("Generate Level", skin);
        addButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                command.execute();
            }
        });
        return addButton;
    }
}
