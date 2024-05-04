package com.mygdx.game.aiManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.entity.Entity;

public class RandomWalkBehavior implements EntityBehavior {
    private Vector2 target;
    private long waitUntil;

    public RandomWalkBehavior() {
        this.target = new Vector2();
        this.waitUntil = 0;
    }

    @Override
    public void update(Entity entity) {
        long currentTime = TimeUtils.millis();

        if (currentTime < waitUntil) {
            return;
        }

        if (target.dst(entity.getX(), entity.getY()) < 1) {
            waitUntil = currentTime + 5000;
            target.set((float) (entity.getX() + Math.random() * 100 - 10), (float) (entity.getY() + Math.random() * 100 - 10));
        } else {
            float dx = target.x - entity.getX();
            float dy = target.y - entity.getY();
            entity.calculateMovement(dx, dy);
        }
    }
}
