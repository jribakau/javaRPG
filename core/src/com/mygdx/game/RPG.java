package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.assetManager.AssetManager;
import com.mygdx.game.cameraManager.CameraManager;
import com.mygdx.game.commandManager.CommandManager;
import com.mygdx.game.gameManager.GameManager;
import com.mygdx.game.inputManager.InputManager;
import com.mygdx.game.screenManager.MainMenuScreen;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RPG extends Game {
    public SpriteBatch batch;
    public BitmapFont font;

    private GameManager gameManager;
    private AssetManager assetManager;
    private CommandManager commandManager;
    private CameraManager cameraManager;
    private InputManager inputManager;

    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();

        assetManager = new AssetManager();
        commandManager = new CommandManager();
        cameraManager = new CameraManager();
        inputManager = new InputManager();

        gameManager = new GameManager(this, assetManager, commandManager, cameraManager, inputManager);

        this.setScreen(new MainMenuScreen(this));
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
