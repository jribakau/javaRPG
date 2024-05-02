package com.mygdx.game.gameManager;

import com.mygdx.game.camera.CameraManager;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.level.Level;
public class GameManagerUtils {

    public static void updateEntitiesVisibility(Level level, CameraManager cameraManager){
        for (Entity entity : level.getVirtualCharacterList()) {
            entity.setIsVisible(calculateVisibility(entity, cameraManager));
        }
        for (Entity entity : level.getTileList()) {
            entity.setIsVisible(calculateVisibility(entity, cameraManager));
        }
    }

    private static boolean calculateVisibility(Entity entity, CameraManager cameraManager) {
        float offset = 30.0f;
        return cameraManager.getCamera().frustum.pointInFrustum(entity.getX() + offset, entity.getY() + offset, 0);
    }
}
