package com.mygdx.game.developerOptions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.commandManager.Command;
import com.mygdx.game.commandManager.CommandButton;
import com.mygdx.game.gameManager.GameManager;
import com.mygdx.game.utils.Files;

public class QuickMenu implements Screen {
    private final Stage stage;
    private final Skin skin;
    private final GameManager gameManager;

    public QuickMenu(GameManager gameManager) {
        this.gameManager = gameManager;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal(Files.SKIN_PATH));
        initialize();
    }

    private void initialize() {
        float currentY = 0;
        for (Command command : gameManager.getCommandManager().getCommandList()) {
            Button button = new CommandButton(command, command.getCommandName().name());
            TextButton textButton = button.createButton(skin);
            textButton.setY(currentY);
            stage.addActor(textButton);
            currentY += 40;
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}