package com.mygdx.game.dev;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.core.GameManager;
import com.mygdx.game.dev.buttons.*;
import com.mygdx.game.utils.Files;

import java.util.ArrayList;
import java.util.List;

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
        List<Button> buttons = new ArrayList<>();
        buttons.add(new AddEntityButton(gameManager));
        buttons.add(new RemoveEntityButton(gameManager));
        buttons.add(new TogglePlayerBoxVisibility(gameManager));
        buttons.add(new GenerateLevelButton(gameManager));
        buttons.add(new LoadLevel_1Button(gameManager));

        float currentY = 0;
        for (Button button : buttons) {
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