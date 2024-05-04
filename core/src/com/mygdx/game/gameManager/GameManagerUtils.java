package com.mygdx.game.gameManager;

import com.mygdx.game.cameraManager.CameraManager;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.levelManager.Level;
public class GameManagerUtils {

    public static void updateEntitiesVisibility(Level level, CameraManager cameraManager){
        for (Entity entity : level.getCharacterList()) {
            entity.setIsVisible(calculateVisibility(entity, cameraManager));
        }
        for (Entity entity : level.getTileList()) {
            entity.setIsVisible(calculateVisibility(entity, cameraManager));
        }
    }

    private static boolean calculateVisibility(Entity entity, CameraManager cameraManager) {
        float offset = 30.0f;
        return cameraManager.getCamera().frustum.pointInFrustum(entity.getPosition().getX() + offset, entity.getPosition().getY() + offset, 0);
    }
}
