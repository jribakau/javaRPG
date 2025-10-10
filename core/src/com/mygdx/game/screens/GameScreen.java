package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.game.assets.types.AnimalType;
import com.mygdx.game.assets.types.CharacterType;
import com.mygdx.game.assets.types.MonsterType;
import com.mygdx.game.core.ServiceLocator;
import com.mygdx.game.core.services.CameraService;
import com.mygdx.game.core.services.RenderService;
import com.mygdx.game.core.services.WorldService;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.EntityFactory;
import com.mygdx.game.entity.EntityService;
import com.mygdx.game.entity.Player;
import com.mygdx.game.entity.components.PositionComponent;
import com.mygdx.game.entity.components.StatsComponent;
import com.mygdx.game.input.InputService;
import com.mygdx.game.systems.RenderSystem;
import com.mygdx.game.systems.SystemManager;
import com.mygdx.game.world.World;

/**
 * GameScreen - Main gameplay screen
 * Now uses System architecture for clean separation of concerns
 */
public class GameScreen implements Screen {
    // Services
    private final CameraService cameraService;
    private final RenderService renderService;
    private final WorldService worldService;
    private final EntityService entityService;
    private final SystemManager systemManager;
    private final RenderSystem renderSystem;

    // UI
    private final BitmapFont font;

    // Game state
    private Player player;

    public GameScreen() {
        // Get services from ServiceLocator
        this.cameraService = ServiceLocator.get(CameraService.class);
        this.renderService = ServiceLocator.get(RenderService.class);
        this.worldService = ServiceLocator.get(WorldService.class);
        this.entityService = ServiceLocator.get(EntityService.class);
        this.systemManager = ServiceLocator.get(SystemManager.class);
        this.renderSystem = systemManager.getSystem(RenderSystem.class);

        // Initialize UI
        this.font = new BitmapFont();

        // Load the world/map
        loadWorld();

        // Create demo entities
        createDemoEntities();

        Gdx.app.log("GameScreen", "Screen created with world and demo entities");
    }

    private void loadWorld() {
        // Load level1 from the assets
        worldService.loadWorld("levels/level1.txt");
        Gdx.app.log("GameScreen", "World loaded: " + worldService.getCurrentWorld());
    }

    private void createDemoEntities() {
        EntityFactory entityFactory = ServiceLocator.get(EntityFactory.class);

        // Create player using the new Player class
        player = entityFactory.createPlayer("Hero", 400, 300, CharacterType.MALE_KNIGHT);
        entityService.addEntity(player);
        Gdx.app.log("GameScreen", "Player created with name: " + player.getPlayerComponent().getName());

        // Create some monsters
        Entity goblin = entityFactory.createMonster(200, 400, MonsterType.GOBLIN, 30, 1);
        entityService.addEntity(goblin);

        Entity skeleton = entityFactory.createMonster(600, 400, MonsterType.SKELETON, 40, 2);
        entityService.addEntity(skeleton);

        Entity slime = entityFactory.createMonster(500, 150, MonsterType.BIG_SLIME, 20, 1);
        entityService.addEntity(slime);

        // Create some NPCs
        Entity priest = entityFactory.createNPC(100, 200, CharacterType.PRIEST, "Father Marcus");
        entityService.addEntity(priest);

        Entity wizard = entityFactory.createNPC(700, 200, CharacterType.MALE_WIZARD, "Gandor");
        entityService.addEntity(wizard);

        // Create some animals
        Entity wolf = entityFactory.createAnimal(300, 500, AnimalType.COW);
        entityService.addEntity(wolf);

        Entity rabbit = entityFactory.createAnimal(650, 100, AnimalType.RABBIT);
        entityService.addEntity(rabbit);

        Gdx.app.log("GameScreen", "Demo entities created: " + entityService.getEntityCount() + " total");
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
        cameraService.update();

        // Render game (delegated to RenderSystem)
        renderSystem.renderWorld();
        renderSystem.renderEntities();

        // Render UI (still handled by screen for now)
        renderUI();
    }

    private void update(float delta) {
        // Update input service first (polls input state)
        InputService inputService = ServiceLocator.get(InputService.class);
        inputService.update();

        // Check for benchmark screen shortcut
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.B)) {
            ServiceLocator.get(com.mygdx.game.core.GameContext.class)
                .setScreen(new BenchmarkScreen());
            return;
        }

        // Update world
        worldService.update(delta);

        // Update all entities via InputComponent (for player input)
        entityService.update(delta);

        // Update all game systems (Movement, Collision, Combat)
        systemManager.update(delta);
    }

    private void renderUI() {
        renderService.begin(cameraService);

        font.draw(renderService.getBatch(), "RPG Game - System Architecture Demo", 10, 590);
        font.draw(renderService.getBatch(), "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 570);
        font.draw(renderService.getBatch(), "Entities: " + entityService.getEntityCount(), 10, 550);
        font.draw(renderService.getBatch(), "Systems: " + systemManager.getSystemCount() + " active", 10, 530);
        font.draw(renderService.getBatch(), "Use WASD to move, Press B for Benchmarks", 10, 510);

        // Show world info
        World world = worldService.getCurrentWorld();
        if (world != null) {
            font.draw(renderService.getBatch(), "World: " + world.getWorldName(), 10, 480);
            font.draw(renderService.getBatch(), "Map Size: " + world.getTileMap().getWidth() + "x" + world.getTileMap().getHeight(), 10, 460);
        }

        // Show player stats
        if (player != null) {
            StatsComponent stats = player.getStatsComponent();
            PositionComponent pos = player.getPositionComponent();

            if (stats != null) {
                font.draw(renderService.getBatch(), "Player: " + player.getPlayerComponent().getName(), 10, 440);
                font.draw(renderService.getBatch(), "HP: " + stats.getHealth() + "/" + stats.getMaxHealth(), 10, 420);
                font.draw(renderService.getBatch(), "Level: " + stats.getLevel() + " (XP: " + stats.getExperience() + ")", 10, 400);
                font.draw(renderService.getBatch(), "Gold: " + player.getPlayerComponent().getGold(), 10, 380);
            }

            if (pos != null) {
                font.draw(renderService.getBatch(), "Position: (" + (int)pos.getX() + ", " + (int)pos.getY() + ")", 10, 360);
            }
        }

        renderService.end();
    }

    @Override
    public void resize(int width, int height) {
        cameraService.resize(width, height);
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
        if (font != null) {
            font.dispose();
        }
    }
}
