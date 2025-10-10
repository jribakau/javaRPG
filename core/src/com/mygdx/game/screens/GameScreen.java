package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.assets.AnimalType;
import com.mygdx.game.assets.CharacterType;
import com.mygdx.game.assets.MonsterType;
import com.mygdx.game.core.GameContext;
import com.mygdx.game.core.ServiceLocator;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.EntityFactory;
import com.mygdx.game.entity.EntityManager;
import com.mygdx.game.entity.components.MovementComponent;
import com.mygdx.game.entity.components.PositionComponent;
import com.mygdx.game.entity.components.RenderComponent;
import com.mygdx.game.entity.components.StatsComponent;

/**
 * GameScreen - Main gameplay screen
 * This is where the actual game will run
 */
public class GameScreen implements Screen {
    private final GameContext context;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final EntityManager entityManager;
    private final EntityFactory entityFactory;
    private final ShapeRenderer shapeRenderer;

    private Entity player;

    public GameScreen() {
        this.context = ServiceLocator.getGameContext();
        this.batch = context.getBatch();
        this.font = new BitmapFont();
        this.entityManager = context.getEntityManager();
        this.entityFactory = context.getEntityFactory();
        this.shapeRenderer = new ShapeRenderer();

        // Create demo entities
        createDemoEntities();

        Gdx.app.log("GameScreen", "Screen created with demo entities");
    }

    private void createDemoEntities() {
        // Create player using EntityFactory
        player = entityFactory.createPlayer(400, 300, CharacterType.MALE_KNIGHT);
        entityManager.addEntity(player);
        Gdx.app.log("GameScreen", "Player created with sprite");

        // Create some monsters
        Entity goblin = entityFactory.createMonster(200, 400, MonsterType.GOBLIN, 30, 1);
        entityManager.addEntity(goblin);

        Entity skeleton = entityFactory.createMonster(600, 400, MonsterType.SKELETON, 40, 2);
        entityManager.addEntity(skeleton);

        Entity slime = entityFactory.createMonster(500, 150, MonsterType.BIG_SLIME, 20, 1);
        entityManager.addEntity(slime);

        // Create some NPCs
        Entity priest = entityFactory.createNPC(100, 200, CharacterType.PRIEST, "Father Marcus");
        entityManager.addEntity(priest);

        Entity wizard = entityFactory.createNPC(700, 200, CharacterType.MALE_WIZARD, "Gandor");
        entityManager.addEntity(wizard);

        // Create some animals
        Entity wolf = entityFactory.createAnimal(300, 500, AnimalType.COW);
        entityManager.addEntity(wolf);

        Entity rabbit = entityFactory.createAnimal(650, 100, AnimalType.RABBIT);
        entityManager.addEntity(rabbit);

        Gdx.app.log("GameScreen", "Demo entities created: " + entityManager.getEntityCount() + " total");
    }

    @Override
    public void show() {
        Gdx.app.log("GameScreen", "Screen shown");
    }

    @Override
    public void render(float delta) {
        // Update game logic
        update(delta);

        // Clear screen
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update camera
        context.getCamera().update();
        batch.setProjectionMatrix(context.getCamera().combined);
        shapeRenderer.setProjectionMatrix(context.getCamera().combined);

        // Render entities
        renderEntities();

        // Render UI
        renderUI();
    }

    private void update(float delta) {
        // Update all entities
        entityManager.update(delta);

        // Handle player input
        handleInput();
    }

    private void handleInput() {
        if (player == null) return;

        MovementComponent movement = player.getComponent(MovementComponent.class);
        if (movement == null) return;

        float dirX = 0;
        float dirY = 0;

        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.W)) dirY = 1;
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.S)) dirY = -1;
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.A)) dirX = -1;
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.D)) dirX = 1;

        if (dirX != 0 || dirY != 0) {
            movement.moveInDirection(dirX, dirY);
        } else {
            movement.stop();
        }
    }

    private void renderEntities() {
        batch.begin();

        // Render all entities with sprites
        for (Entity entity : entityManager.getEntitiesWithComponent(PositionComponent.class)) {
            PositionComponent pos = entity.getComponent(PositionComponent.class);
            RenderComponent render = entity.getComponent(RenderComponent.class);

            if (render != null && render.isVisible() && render.getTextureRegion() != null) {
                // Draw the sprite
                batch.draw(
                    render.getTextureRegion(),
                    pos.getX() + render.getOffsetX(),
                    pos.getY() + render.getOffsetY(),
                    pos.getWidth(),
                    pos.getHeight()
                );
            } else if (render != null && render.isVisible()) {
                // Fallback: render as colored rectangle if no sprite
                batch.end();
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                if (entity == player) {
                    shapeRenderer.setColor(Color.GREEN);
                } else {
                    shapeRenderer.setColor(Color.WHITE);
                }
                shapeRenderer.rect(pos.getX(), pos.getY(), pos.getWidth(), pos.getHeight());
                shapeRenderer.end();
                batch.begin();
            }
        }

        batch.end();
    }

    private void renderUI() {
        batch.begin();

        font.draw(batch, "RPG Game - Asset Management Demo", 10, 590);
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 570);
        font.draw(batch, "Entities: " + entityManager.getEntityCount(), 10, 550);
        font.draw(batch, "Use WASD to move the knight", 10, 530);

        // Show player stats
        if (player != null) {
            StatsComponent stats = player.getComponent(StatsComponent.class);
            PositionComponent pos = player.getComponent(PositionComponent.class);

            if (stats != null) {
                font.draw(batch, "HP: " + stats.getHealth() + "/" + stats.getMaxHealth(), 10, 510);
                font.draw(batch, "Level: " + stats.getLevel(), 10, 490);
            }

            if (pos != null) {
                font.draw(batch, "Position: (" + (int)pos.getX() + ", " + (int)pos.getY() + ")", 10, 470);
            }
        }

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        context.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        font.dispose();
        shapeRenderer.dispose();
        Gdx.app.log("GameScreen", "Screen disposed");
    }
}