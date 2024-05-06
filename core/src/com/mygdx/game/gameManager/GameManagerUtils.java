package com.mygdx.game.gameManager;

import com.badlogic.gdx.math.Rectangle;
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
        for (int i = 0; i < level.getCharacterList().size(); i++) {
            Entity entity1 = level.getCharacterList().get(i);
            for (int j = i + 1; j < level.getCharacterList().size(); j++) {
                Entity entity2 = level.getCharacterList().get(j);
                if (entity1.collidesWith(entity2)) {
                     resolveCollision(entity1, entity2);
                }
            }
        }

        Entity player = level.getPlayer();
        for (Entity entity : level.getCharacterList()) {
            if (player.collidesWith(entity)) {
                resolveCollision(player, entity);
            }
        }

        for (Entity entity : level.getCharacterList()) {
            detectInteractionWithPlayer(entity, level);
        }
    }

    private static void detectInteractionWithPlayer(Entity entity, Level level) {
        if (level.getPlayer().interactsWith(entity)) {
            // handle interaction
        }
    }

    private static void resolveCollision(Entity entity1, Entity entity2) {
        Rectangle collisionBox1 = entity1.getCollisionBox();
        Rectangle collisionBox2 = entity2.getCollisionBox();

        if (collisionBox1.overlaps(collisionBox2)) {
            float overlapX = Math.min(collisionBox1.x + collisionBox1.width, collisionBox2.x + collisionBox2.width) - Math.max(collisionBox1.x, collisionBox2.x);
            float overlapY = Math.min(collisionBox1.y + collisionBox1.height, collisionBox2.y + collisionBox2.height) - Math.max(collisionBox1.y, collisionBox2.y);

            if (overlapX < overlapY) {
                if (collisionBox1.x < collisionBox2.x) {
                    entity1.getPosition().x -= overlapX;
                } else {
                    entity1.getPosition().x += overlapX;
                }
            } else {
                if (collisionBox1.y < collisionBox2.y) {
                    entity1.getPosition().y -= overlapY;
                } else {
                    entity1.getPosition().y += overlapY;
                }
            }

            entity1.updateCollisionAndInteractionBoxes();
            entity2.updateCollisionAndInteractionBoxes();
        }
    }
}
