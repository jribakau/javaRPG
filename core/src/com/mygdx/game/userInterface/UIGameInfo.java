package com.mygdx.game.userInterface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.gameManager.GameManager;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UIGameInfo {
    private final List<UIElement> elements;
    private final GameManager gameManager;
    private final OrthographicCamera uiCamera;
    private float nextElementY;

    public UIGameInfo(SpriteBatch batch, BitmapFont font, GameManager gameManager) {
        this.elements = new ArrayList<>();
        this.gameManager = gameManager;
        this.uiCamera = new OrthographicCamera();
        uiCamera.setToOrtho(false);
        this.nextElementY = Gdx.graphics.getHeight() - 20;
        addElement(new UILabel(batch, font, 20, nextElementY, "Velocity: " + gameManager.getLevel().getPlayer().getVelocity()));
    }

    public void draw() {
        uiCamera.update();
        updateLabels();
        for (UIElement element : elements) {
            element.draw(uiCamera);
        }
    }

    public void addElement(UIElement element) {
        elements.add(element);
        nextElementY -= 20;
    }

    public void updateLabels() {
        for (UIElement element : elements) {
            if (element instanceof UILabel) {
                UILabel label = (UILabel) element;
                if (label.getText().contains("Velocity")) {
                    label.setText("Velocity: " + gameManager.getLevel().getPlayer().getVelocity());
                }
            }
        }
    }
}