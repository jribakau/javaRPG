package com.mygdx.game.core;

import com.mygdx.game.camera.CameraManager;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.NPC;

import java.util.List;
public class GameManagerUtils {
    public static void updateEntitiesVisibility(List<NPC> entitiesInView, CameraManager cameraManager) {
        float offset = 30.0f;
        for (Entity entity : entitiesInView) {
            entity.setIsVisible(cameraManager.getCamera().frustum.pointInFrustum(entity.getX() + offset, entity.getY() + offset, 0));
        }
    }
}
