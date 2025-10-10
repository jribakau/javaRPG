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
 * Processes entities with both PositionComponent and MovementComponent
 */
public class MovementSystem extends GameSystem {
    private final WorldService worldService;
    private final Rectangle entityBounds;
    private final Rectangle tileBounds;

    public MovementSystem(EntityService entityService) {
        super(entityService);
        this.worldService = ServiceLocator.get(WorldService.class);
        this.entityBounds = new Rectangle();
        this.tileBounds = new Rectangle();
    }

    @Override
    public void update(float delta) {
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
                Vector2 validPosition = resolveWorldCollision(
                    position.getX(), position.getY(),
                    newX, newY,
                    position.getWidth(), position.getHeight()
                );
                newX = validPosition.x;
                newY = validPosition.y;
            }

            // Apply movement
            position.setPosition(newX, newY);

            // Apply friction/deceleration if enabled
            if (movement.getFriction() > 0) {
                velocity.scl(1f - movement.getFriction() * delta);

                // Stop if velocity is very small
                if (velocity.len2() < 0.1f) {
                    velocity.set(0, 0);
                }
            }
        }
    }

    /**
     * Check collision with world tiles and resolve
     * @return Valid position after collision resolution
     */
    private Vector2 resolveWorldCollision(float oldX, float oldY, float newX, float newY,
                                         float width, float height) {
        World world = worldService.getCurrentWorld();
        if (world == null) return new Vector2(newX, newY);

        TileMap tileMap = world.getTileMap();
        if (tileMap == null) return new Vector2(newX, newY);

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

        return new Vector2(newX, newY);
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

