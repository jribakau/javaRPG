package com.mygdx.game.dev;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.RPG;

public class DeveloperMenuScreen implements Screen {
    private final RPG game;
    private Stage stage;

    public DeveloperMenuScreen(final RPG game) {
        this.game = game;
        initialize();
    }

    private void initialize() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        TextButton addButton = new TextButton("Add Entity", skin);
        addButton.setPosition(100, 200);
        addButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Add Entity");
                game.getGameManager().addNPC();
            }
        });

        TextButton removeButton = new TextButton("Remove Entity", skin);
        removeButton.setPosition(100, 150);
        removeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Remove Entity");
            }
        });

        TextButton changeButton = new TextButton("Change Entity", skin);
        changeButton.setPosition(100, 100);
        changeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Change Entity");
            }
        });

        TextButton listButton = new TextButton("List entities", skin);
        listButton.setPosition(100, 50);
        listButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("List entities");
                game.getGameManager().listEntities();
            }
        });

        stage.addActor(addButton);
        stage.addActor(removeButton);
        stage.addActor(changeButton);
        stage.addActor(listButton);
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
