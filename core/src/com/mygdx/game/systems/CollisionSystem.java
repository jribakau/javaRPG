package com.mygdx.game.systems;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.EntityService;
import com.mygdx.game.entity.components.PositionComponent;

/**
 * CollisionSystem - Detects and resolves entity-to-entity collisions
 * Uses spatial partitioning for efficient collision detection
 */
public class CollisionSystem extends GameSystem {
    private final Rectangle rect1;
    private final Rectangle rect2;
    private final Array<CollisionPair> collisions;

    public CollisionSystem(EntityService entityService) {
        super(entityService);
        this.rect1 = new Rectangle();
        this.rect2 = new Rectangle();
        this.collisions = new Array<>();
    }

    @Override
    public void update(float delta) {
        collisions.clear();

        // Get all entities with position components
        Array<Entity> entities = entityService.getEntitiesWithComponent(PositionComponent.class);

        // Broad phase: Check all pairs (can be optimized with spatial grid)
        for (int i = 0; i < entities.size; i++) {
            Entity entity1 = entities.get(i);
            PositionComponent pos1 = entity1.getComponent(PositionComponent.class);

            rect1.set(pos1.getX(), pos1.getY(), pos1.getWidth(), pos1.getHeight());

            for (int j = i + 1; j < entities.size; j++) {
                Entity entity2 = entities.get(j);
                PositionComponent pos2 = entity2.getComponent(PositionComponent.class);

                rect2.set(pos2.getX(), pos2.getY(), pos2.getWidth(), pos2.getHeight());

                // Check collision
                if (rect1.overlaps(rect2)) {
                    collisions.add(new CollisionPair(entity1, entity2));
                }
            }
        }

        // Process collisions
        for (CollisionPair pair : collisions) {
            handleCollision(pair.entity1, pair.entity2);
        }
    }

    /**
     * Handle collision between two entities
     * Override or extend for game-specific collision responses
     */
    protected void handleCollision(Entity entity1, Entity entity2) {
        // Basic collision response: simple separation
        // This can be extended with collision layers, tags, damage, etc.

        PositionComponent pos1 = entity1.getComponent(PositionComponent.class);
        PositionComponent pos2 = entity2.getComponent(PositionComponent.class);

        if (pos1 == null || pos2 == null) return;

        // Calculate overlap and separation vector
        float centerX1 = pos1.getX() + pos1.getWidth() / 2;
        float centerY1 = pos1.getY() + pos1.getHeight() / 2;
        float centerX2 = pos2.getX() + pos2.getWidth() / 2;
        float centerY2 = pos2.getY() + pos2.getHeight() / 2;

        float dx = centerX2 - centerX1;
        float dy = centerY2 - centerY1;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        if (distance > 0) {
            // Normalize and separate
            float overlapX = (pos1.getWidth() + pos2.getWidth()) / 2 - Math.abs(dx);
            float overlapY = (pos1.getHeight() + pos2.getHeight()) / 2 - Math.abs(dy);

            // Separate on the axis with smallest overlap
            if (overlapX < overlapY) {
                float separationX = overlapX / 2 * Math.signum(dx);
                pos1.setX(pos1.getX() - separationX);
                pos2.setX(pos2.getX() + separationX);
            } else {
                float separationY = overlapY / 2 * Math.signum(dy);
                pos1.setY(pos1.getY() - separationY);
                pos2.setY(pos2.getY() + separationY);
            }
        }

        // Fire collision event (can be extended)
        onCollision(entity1, entity2);
    }

    /**
     * Override this to handle game-specific collision logic
     * (e.g., player hits enemy, pickup item, etc.)
     */
    protected void onCollision(Entity entity1, Entity entity2) {
        // Default: do nothing
        // Subclasses or game code can extend this
    }

    /**
     * Check if two entities are currently colliding
     */
    public boolean areColliding(Entity entity1, Entity entity2) {
        PositionComponent pos1 = entity1.getComponent(PositionComponent.class);
        PositionComponent pos2 = entity2.getComponent(PositionComponent.class);

        if (pos1 == null || pos2 == null) return false;

        rect1.set(pos1.getX(), pos1.getY(), pos1.getWidth(), pos1.getHeight());
        rect2.set(pos2.getX(), pos2.getY(), pos2.getWidth(), pos2.getHeight());

        return rect1.overlaps(rect2);
    }

    @Override
    public int getPriority() {
        return 20; // Collision detection after movement
    }

    /**
         * Helper class to store collision pairs
         */
        private record CollisionPair(Entity entity1, Entity entity2) {
    }
}

