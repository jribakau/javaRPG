package com.mygdx.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.assets.types.AnimalType;
import com.mygdx.game.assets.types.CharacterType;
import com.mygdx.game.assets.types.MonsterType;
import com.mygdx.game.core.ServiceLocator;
import com.mygdx.game.core.services.CameraService;
import com.mygdx.game.core.services.WorldService;
import com.mygdx.game.entity.EntityFactory;
import com.mygdx.game.entity.EntityService;
import com.mygdx.game.entity.Player;
import com.mygdx.game.input.InputAction;
import com.mygdx.game.systems.RenderSystem;
import com.mygdx.game.systems.SystemManager;
import com.mygdx.game.ui.GameHUD;
import com.mygdx.game.ui.UIService;
import com.mygdx.game.world.World;

/**
 * PlayingState - Main gameplay state
 * Active when player is playing the game (exploring, fighting, etc.)
 * Uses modern UI system for clean HUD rendering
 */
public class PlayingState extends GameState {
    private CameraService cameraService;
    private WorldService worldService;
    private EntityService entityService;
    private SystemManager systemManager;
    private RenderSystem renderSystem;
    private UIService uiService;
    private GameHUD gameHUD;
    private Player player;

    public PlayingState(GameStateManager stateManager) {
        super(stateManager);
    }

    @Override
    protected void onCreate() {
        // Get services
        cameraService = ServiceLocator.get(CameraService.class);
        worldService = ServiceLocator.get(WorldService.class);
        entityService = ServiceLocator.get(EntityService.class);
        systemManager = ServiceLocator.get(SystemManager.class);
        renderSystem = systemManager.getSystem(RenderSystem.class);
        uiService = ServiceLocator.get(UIService.class);

        // Initialize UI - Create modern HUD
        initializeUI();

        // Load world and create entities
        loadWorld();
        createEntities();

        Gdx.app.log("PlayingState", "State created with modern UI system");
    }

    private void initializeUI() {
        // Clear any existing UI
        uiService.clear();

        // Create the game HUD using the modern UI system
        gameHUD = new GameHUD(uiService);

        Gdx.app.log("PlayingState", "Modern UI system initialized");
    }

    private void loadWorld() {
        worldService.loadWorld("levels/level1.txt");
        Gdx.app.log("PlayingState", "World loaded: " + worldService.getCurrentWorld().getWorldName());
    }

    private void createEntities() {
        EntityFactory entityFactory = ServiceLocator.get(EntityFactory.class);

        // Create player
        player = entityFactory.createPlayer("Hero", 400, 300, CharacterType.MALE_KNIGHT);
        entityService.addEntity(player);

        // Create monsters with varied types
        entityService.addEntity(entityFactory.createMonster(200, 400, MonsterType.GOBLIN, 30, 1));
        entityService.addEntity(entityFactory.createMonster(600, 400, MonsterType.SKELETON, 40, 2));
        entityService.addEntity(entityFactory.createMonster(500, 150, MonsterType.BIG_SLIME, 20, 1));
        entityService.addEntity(entityFactory.createMonster(350, 550, MonsterType.ORC, 50, 3));

        // Create NPCs
        entityService.addEntity(entityFactory.createNPC(100, 200, CharacterType.PRIEST, "Father Marcus"));
        entityService.addEntity(entityFactory.createNPC(700, 200, CharacterType.MALE_WIZARD, "Gandor the Wise"));
        entityService.addEntity(entityFactory.createNPC(400, 100, CharacterType.FEMALE_WIZARD, "Lady Elara"));

        // Create animals
        entityService.addEntity(entityFactory.createAnimal(300, 500, AnimalType.COW));
        entityService.addEntity(entityFactory.createAnimal(650, 100, AnimalType.RABBIT));
        entityService.addEntity(entityFactory.createAnimal(250, 250, AnimalType.CHICKEN));

        Gdx.app.log("PlayingState", "Entities created: " + entityService.getEntityCount() + " total");
    }

    @Override
    public void onEnter() {
        Gdx.app.log("PlayingState", "Entered playing state - Adventure begins!");
    }

    @Override
    public void onExit() {
        Gdx.app.log("PlayingState", "Exited playing state");
    }

    @Override
    public void onPause() {
        Gdx.app.log("PlayingState", "Game paused - updates blocked");
    }

    @Override
    public void onResume() {
        Gdx.app.log("PlayingState", "Game resumed - adventure continues!");
    }

    @Override
    public void update(float delta) {
        // Update input service
        inputService.update();

        // Check for state transitions using InputService
        if (inputService.isActionJustPressed(InputAction.PAUSE) ||
            inputService.isActionJustPressed(InputAction.MENU)) {
            stateManager.pushState(new PauseState(stateManager));
            return;
        }

        if (inputService.isActionJustPressed(InputAction.INVENTORY)) {
            stateManager.pushState(new InventoryState(stateManager, player));
            return;
        }

        // Update world and entities
        worldService.update(delta);
        entityService.update(delta);
        systemManager.update(delta);

        // Update camera to follow player
        cameraService.update();

        // Update UI with current game state
        World world = worldService.getCurrentWorld();
        gameHUD.update(
            Gdx.graphics.getFramesPerSecond(),
            entityService.getEntityCount(),
            systemManager.getSystemCount(),
            player,
            world
        );
        uiService.update(delta);
    }

    @Override
    public void render(float delta) {
        // Clear screen
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render game world and entities
        renderSystem.renderWorld();
        renderSystem.renderEntities();

        // Render UI (modern UI system handles all UI rendering)
        uiService.render();
    }

    @Override
    public void resize(int width, int height) {
        cameraService.resize(width, height);
        uiService.resize(width, height);
    }

    @Override
    public void dispose() {
        if (gameHUD != null) {
            gameHUD.dispose();
        }
    }
}
