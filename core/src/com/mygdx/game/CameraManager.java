package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraManager {
    private final OrthographicCamera camera;

    public CameraManager(int screenWidth, int screenHeight) {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);
    }

    public void updateCameraPosition(PC pc) {
        float targetX = pc.getX() + pc.getWidth() / 2;
        float targetY = pc.getY() + pc.getHeight() / 2;

        float lerpAlpha = 0.1f;
        float cameraX = camera.position.x + (targetX - camera.position.x) * lerpAlpha;
        float cameraY = camera.position.y + (targetY - camera.position.y) * lerpAlpha;

        camera.position.set(cameraX, cameraY, 0);
        camera.update();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}