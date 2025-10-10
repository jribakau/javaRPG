package com.mygdx.game.systems;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.core.ServiceLocator;
import com.mygdx.game.core.services.CameraService;
import com.mygdx.game.core.services.RenderService;
import com.mygdx.game.core.services.WorldService;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.EntityService;
import com.mygdx.game.entity.Player;
import com.mygdx.game.entity.components.PositionComponent;
import com.mygdx.game.entity.components.RenderComponent;
import com.mygdx.game.world.World;

/**
 * RenderSystem - Handles all game rendering
 * Renders world, entities, and provides rendering utilities
 *
 * NOTE: This system doesn't use update() for rendering, instead it has explicit render() methods
 * called by the GameScreen during its render phase
 */
public class RenderSystem extends GameSystem {
    private final CameraService cameraService;
    private final RenderService renderService;
    private final WorldService worldService;

    public RenderSystem(EntityService entityService) {
        super(entityService);
        this.cameraService = ServiceLocator.get(CameraService.class);
        this.renderService = ServiceLocator.get(RenderService.class);
        this.worldService = ServiceLocator.get(WorldService.class);
    }

    @Override
    public void update(float delta) {
        // RenderSystem doesn't use update() - it uses explicit render() methods
        // This is because rendering happens in the render() phase, not update()
    }

    /**
     * Render the game world (tiles, background, etc.)
     */
    public void renderWorld() {
        World world = worldService.getCurrentWorld();
        if (world != null) {
            SpriteBatch batch = renderService.getBatch();
            renderService.begin(cameraService);
            world.render(batch);
            renderService.end();
        }
    }

    /**
     * Render all entities with sprites
     */
    public void renderEntities() {
        renderService.begin(cameraService);

        // Get all entities with render components
        Array<Entity> renderableEntities = entityService.getEntitiesWithComponent(RenderComponent.class);

        // Sort entities by Y position for proper depth (optional - can be enabled)
        // renderableEntities.sort((e1, e2) -> {
        //     PositionComponent p1 = e1.getComponent(PositionComponent.class);
        //     PositionComponent p2 = e2.getComponent(PositionComponent.class);
        //     return Float.compare(p2.getY(), p1.getY()); // Higher Y = render first (behind)
        // });

        SpriteBatch batch = renderService.getBatch();

        for (Entity entity : renderableEntities) {
            PositionComponent pos = entity.getComponent(PositionComponent.class);
            RenderComponent render = entity.getComponent(RenderComponent.class);

            if (pos == null || !render.isVisible()) continue;

            if (render.getTextureRegion() != null) {
                // Draw sprite
                batch.draw(
                    render.getTextureRegion(),
                    pos.getX() + render.getOffsetX(),
                    pos.getY() + render.getOffsetY(),
                    pos.getWidth(),
                    pos.getHeight()
                );
            }
        }

        renderService.end();
    }

    /**
     * Render entity debug shapes (for debugging)
     */
    public void renderDebugShapes() {
        Array<Entity> entities = entityService.getEntitiesWithComponent(PositionComponent.class);

        renderService.beginShapes(cameraService, ShapeRenderer.ShapeType.Line);
        ShapeRenderer shapeRenderer = renderService.getShapeRenderer();

        for (Entity entity : entities) {
            PositionComponent pos = entity.getComponent(PositionComponent.class);
            RenderComponent render = entity.getComponent(RenderComponent.class);

            if (render != null && render.isVisible()) {
                // Color code: Green for player, Yellow for others
                if (entity instanceof Player) {
                    shapeRenderer.setColor(Color.GREEN);
                } else {
                    shapeRenderer.setColor(Color.YELLOW);
                }

                // Draw bounding box
                shapeRenderer.rect(pos.getX(), pos.getY(), pos.getWidth(), pos.getHeight());
            }
        }

        renderService.endShapes();
    }

    /**
     * Render entities without sprites as colored rectangles (fallback)
     */
    public void renderFallbackShapes() {
        Array<Entity> entities = entityService.getEntitiesWithComponent(PositionComponent.class);

        boolean shapesStarted = false;

        for (Entity entity : entities) {
            PositionComponent pos = entity.getComponent(PositionComponent.class);
            RenderComponent render = entity.getComponent(RenderComponent.class);

            // Only render if visible but has no sprite
            if (render != null && render.isVisible() && render.getTextureRegion() == null) {
                if (!shapesStarted) {
                    renderService.beginShapes(cameraService, ShapeRenderer.ShapeType.Filled);
                    shapesStarted = true;
                }

                ShapeRenderer shapeRenderer = renderService.getShapeRenderer();

                // Color code entities
                if (entity instanceof Player) {
                    shapeRenderer.setColor(Color.GREEN);
                } else {
                    shapeRenderer.setColor(Color.WHITE);
                }

                shapeRenderer.rect(pos.getX(), pos.getY(), pos.getWidth(), pos.getHeight());
            }
        }

        if (shapesStarted) {
            renderService.endShapes();
        }
    }

    @Override
    public int getPriority() {
        return 100; // Rendering happens last (though we use explicit render() calls)
    }
}

