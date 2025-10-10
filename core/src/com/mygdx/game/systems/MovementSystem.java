package com.mygdx.game.systems;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.core.ServiceLocator;
import com.mygdx.game.core.services.WorldService;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.EntityService;
import com.mygdx.game.entity.components.MovementComponent;
import com.mygdx.game.entity.components.PositionComponent;
import com.mygdx.game.world.Tile;
import com.mygdx.game.world.TileMap;
import com.mygdx.game.world.World;

/**
 * MovementSystem - Handles entity movement and collision with world tiles
 * OPTIMIZED: Reuses Vector2 objects to avoid garbage collection pressure
 */
public class MovementSystem extends GameSystem {
    private final WorldService worldService;
    private final Rectangle entityBounds;
    private final Rectangle tileBounds;

    // OPTIMIZATION: Reusable Vector2 to avoid allocations
    private final Vector2 tempPosition;

    public MovementSystem(EntityService entityService) {
        super(entityService);
        this.worldService = ServiceLocator.get(WorldService.class);
        this.entityBounds = new Rectangle();
        this.tileBounds = new Rectangle();
        this.tempPosition = new Vector2();
    }

    @Override
    public void update(float delta) {
        if (!enabled) return;

        // Get all entities that can move
        Array<Entity> movableEntities = entityService.getEntitiesWithComponent(MovementComponent.class);

        for (Entity entity : movableEntities) {
            PositionComponent position = entity.getComponent(PositionComponent.class);
            MovementComponent movement = entity.getComponent(MovementComponent.class);

            if (position == null) continue;

            Vector2 velocity = movement.getVelocity();
            if (velocity.x == 0 && velocity.y == 0) continue;

            // Calculate new position
            float newX = position.getX() + velocity.x * delta;
            float newY = position.getY() + velocity.y * delta;

            // Check collision with world tiles
            if (worldService.getCurrentWorld() != null) {
                resolveWorldCollision(
                    position.getX(), position.getY(),
                    newX, newY,
                    position.getWidth(), position.getHeight(),
                    tempPosition  // Reuse Vector2 instead of allocating new one!
                );
                newX = tempPosition.x;
                newY = tempPosition.y;
            }

            // Apply movement
            position.setPosition(newX, newY);

            // Apply friction/deceleration if enabled
            if (movement.getFriction() > 0) {
                velocity.scl(1f - movement.getFriction() * delta);

                // Stop if velocity is very small (use squared length to avoid sqrt)
                if (velocity.len2() < 0.01f) {  // 0.1 * 0.1
                    velocity.set(0, 0);
                }
            }
        }
    }

    /**
     * Check collision with world tiles and resolve
     * OPTIMIZED: Uses output parameter instead of returning new Vector2
     */
    private void resolveWorldCollision(float oldX, float oldY, float newX, float newY,
                                      float width, float height, Vector2 outPosition) {
        World world = worldService.getCurrentWorld();
        if (world == null) {
            outPosition.set(newX, newY);
            return;
        }

        TileMap tileMap = world.getTileMap();
        if (tileMap == null) {
            outPosition.set(newX, newY);
            return;
        }

        // Set entity bounds at new position
        entityBounds.set(newX, newY, width, height);

        // Check X-axis movement
        boolean xCollision = checkTileCollision(entityBounds, tileMap);
        if (xCollision) {
            newX = oldX; // Revert X movement
        }

        // Check Y-axis movement
        entityBounds.set(newX, newY, width, height);
        boolean yCollision = checkTileCollision(entityBounds, tileMap);
        if (yCollision) {
            newY = oldY; // Revert Y movement
        }

        outPosition.set(newX, newY);
    }

    /**
     * Check if entity bounds collide with any non-walkable tiles
     */
    private boolean checkTileCollision(Rectangle entityBounds, TileMap tileMap) {
        int tileSize = tileMap.getTileSize();

        // Get tile coordinates the entity overlaps
        int startX = (int) (entityBounds.x / tileSize);
        int startY = (int) (entityBounds.y / tileSize);
        int endX = (int) ((entityBounds.x + entityBounds.width) / tileSize);
        int endY = (int) ((entityBounds.y + entityBounds.height) / tileSize);

        // Check each tile
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                Tile tile = tileMap.getTile(x, y);
                if (tile != null && !tile.isWalkable()) {
                    // Tile is solid, check if entity actually overlaps it
                    tileBounds.set(x * tileSize, y * tileSize, tileSize, tileSize);
                    if (entityBounds.overlaps(tileBounds)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public int getPriority() {
        return 10; // Movement happens early
    }
}

