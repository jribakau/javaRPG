package com.mygdx.game.cameraManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.entity.Player;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CameraManager {
    private final OrthographicCamera camera;

    public CameraManager() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void updateCameraPosition(Player pc) {
        float cameraX = camera.position.x + (pc.getPosition().getX() - camera.position.x);
        float cameraY = camera.position.y + (pc.getPosition().getY() - camera.position.y);

        camera.position.set(cameraX, cameraY, 0);
        camera.update();
    }

}