package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraManager {
    private final OrthographicCamera camera;

    public CameraManager(int screenWidth, int screenHeight) {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);
    }

    public void updateCameraPosition(PC pc) {
        camera.position.set(pc.getPosition().x + pc.getPosition().width / 2, pc.getPosition().y + pc.getPosition().height / 2, 0);
        camera.update();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}