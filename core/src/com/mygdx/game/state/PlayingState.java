package com.mygdx.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.game.assets.types.AnimalType;
import com.mygdx.game.assets.types.CharacterType;
import com.mygdx.game.assets.types.MonsterType;
import com.mygdx.game.core.ServiceLocator;
import com.mygdx.game.core.services.CameraService;
import com.mygdx.game.core.services.RenderService;
import com.mygdx.game.core.services.WorldService;
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
 * PlayingState - Main gameplay state
 * Active when player is playing the game (exploring, fighting, etc.)
 */
public class PlayingState extends GameState {
    private CameraService cameraService;
    private RenderService renderService;
    private WorldService worldService;
    private EntityService entityService;
    private SystemManager systemManager;
    private RenderSystem renderSystem;
    private BitmapFont font;
    private Player player;

    public PlayingState(GameStateManager stateManager) {
        super(stateManager);
    }

    @Override
    protected void onCreate() {
        // Get services
        cameraService = ServiceLocator.get(CameraService.class);
        renderService = ServiceLocator.get(RenderService.class);
        worldService = ServiceLocator.get(WorldService.class);
        entityService = ServiceLocator.get(EntityService.class);
        systemManager = ServiceLocator.get(SystemManager.class);
        renderSystem = systemManager.getSystem(RenderSystem.class);

        // Initialize UI
        font = new BitmapFont();

        // Load world and create entities
        loadWorld();
        createEntities();

        Gdx.app.log("PlayingState", "State created");
    }

    private void loadWorld() {
        worldService.loadWorld("levels/level1.txt");
        Gdx.app.log("PlayingState", "World loaded");
    }

    private void createEntities() {
        EntityFactory entityFactory = ServiceLocator.get(EntityFactory.class);

        // Create player
        player = entityFactory.createPlayer("Hero", 400, 300, CharacterType.MALE_KNIGHT);
        entityService.addEntity(player);

        // Create monsters
        entityService.addEntity(entityFactory.createMonster(200, 400, MonsterType.GOBLIN, 30, 1));
        entityService.addEntity(entityFactory.createMonster(600, 400, MonsterType.SKELETON, 40, 2));
        entityService.addEntity(entityFactory.createMonster(500, 150, MonsterType.BIG_SLIME, 20, 1));

        // Create NPCs
        entityService.addEntity(entityFactory.createNPC(100, 200, CharacterType.PRIEST, "Father Marcus"));
        entityService.addEntity(entityFactory.createNPC(700, 200, CharacterType.MALE_WIZARD, "Gandor"));

        // Create animals
        entityService.addEntity(entityFactory.createAnimal(300, 500, AnimalType.COW));
        entityService.addEntity(entityFactory.createAnimal(650, 100, AnimalType.RABBIT));

        Gdx.app.log("PlayingState", "Entities created: " + entityService.getEntityCount());
    }

    @Override
    public void onEnter() {
        Gdx.app.log("PlayingState", "Entered playing state");
    }

    @Override
    public void onExit() {
        Gdx.app.log("PlayingState", "Exited playing state");
    }

    @Override
    public void onPause() {
        Gdx.app.log("PlayingState", "Game paused");
    }

    @Override
    public void onResume() {
        Gdx.app.log("PlayingState", "Game resumed");
    }

    @Override
    public void update(float delta) {
        // Update input service
        InputService inputService = ServiceLocator.get(InputService.class);
        inputService.update();

        // Check for state transitions
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            stateManager.pushState(new PauseState(stateManager));
            return;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            stateManager.pushState(new InventoryState(stateManager, player));
            return;
        }

        // Update world and entities
        worldService.update(delta);
        entityService.update(delta);
        systemManager.update(delta);

        // Update camera
        cameraService.update();
    }

    @Override
    public void render(float delta) {
        // Clear screen
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render game
        renderSystem.renderWorld();
        renderSystem.renderEntities();

        // Render UI
        renderUI();
    }

    private void renderUI() {
        renderService.begin(cameraService);

        font.draw(renderService.getBatch(), "RPG Game - Playing", 10, 590);
        font.draw(renderService.getBatch(), "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 570);
        font.draw(renderService.getBatch(), "Entities: " + entityService.getEntityCount(), 10, 550);
        font.draw(renderService.getBatch(), "WASD: Move | I: Inventory | ESC: Pause", 10, 530);

        // World info
        World world = worldService.getCurrentWorld();
        if (world != null) {
            font.draw(renderService.getBatch(), "World: " + world.getWorldName(), 10, 500);
        }

        // Player stats
        if (player != null) {
            StatsComponent stats = player.getStatsComponent();
            PositionComponent pos = player.getPositionComponent();

            if (stats != null) {
                font.draw(renderService.getBatch(), "HP: " + stats.getHealth() + "/" + stats.getMaxHealth(), 10, 470);
                font.draw(renderService.getBatch(), "Level: " + stats.getLevel(), 10, 450);
                font.draw(renderService.getBatch(), "Gold: " + player.getPlayerComponent().getGold(), 10, 430);
            }

            if (pos != null) {
                font.draw(renderService.getBatch(), "Pos: (" + (int) pos.getX() + ", " + (int) pos.getY() + ")", 10, 410);
            }
        }

        renderService.end();
    }

    @Override
    public void resize(int width, int height) {
        cameraService.resize(width, height);
    }

    @Override
    public void dispose() {
        if (font != null) {
            font.dispose();
        }
    }
}

