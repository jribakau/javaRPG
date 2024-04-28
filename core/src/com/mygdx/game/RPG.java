package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.core.GameManager;
import com.mygdx.game.mainMenu.MainMenuScreen;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RPG extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    private GameManager gameManager;

    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        gameManager = new GameManager(this);
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
