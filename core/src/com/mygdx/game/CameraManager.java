package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraManager {
    private final OrthographicCamera camera;

    public CameraManager() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void updateCameraPosition(Player pc) {
        float cameraX = camera.position.x + (pc.getX() - camera.position.x);
        float cameraY = camera.position.y + (pc.getY() - camera.position.y);

        camera.position.set(cameraX, cameraY, 0);
        camera.update();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}