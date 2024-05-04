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

    public static void detectCollisionsAndInteractions(Level level) {
        for (Entity entity : level.getCharacterList()) {
            if (entity != level.getPlayer()) {
                detectCollisionWithPlayer(entity, level);
                detectInteractionWithPlayer(entity, level);
            }
        }
    }

    private static void detectCollisionWithPlayer(Entity entity, Level level) {
        if (level.getPlayer().collidesWith(entity)) {
            // handle collision
        }
    }

    private static void detectInteractionWithPlayer(Entity entity, Level level) {
        if (level.getPlayer().interactsWith(entity)) {
            // handle interaction
        }
    }
}
