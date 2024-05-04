package com.mygdx.game.aiManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.entity.Entity;

import java.util.Random;

public class RandomWalkBehavior implements EntityBehavior {
    private final Vector2 target;
    private long waitUntil;
    private final Random random;

    public RandomWalkBehavior(Entity entity) {
        this.target = new Vector2();
        this.waitUntil = 0;
        this.random = new Random();
        this.target.set(entity.getPosition().getX() + random.nextFloat() * 640 - 320, entity.getPosition().getY() + random.nextFloat() * 640 - 320);
    }

    @Override
    public void update(Entity entity) {
        long currentTime = TimeUtils.millis();

        if (currentTime < waitUntil) {
            return;
        }

        if (target.dst(entity.getPosition().getX(), entity.getPosition().getY()) < 1) {
            waitUntil = currentTime + 5000;
            target.set(entity.getPosition().getX() + random.nextFloat() * 640 - 320, entity.getPosition().getY() + random.nextFloat() * 640 - 320);
        } else {
            float dx = target.x - entity.getPosition().getX();
            float dy = target.y - entity.getPosition().getX();
            entity.calculateMovement(dx, dy);
        }
    }
}