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
 * Level2State - A more complex dungeon level
 * Demonstrates a multi-room dungeon with varied tile types and more challenging encounters
 */
public class Level2State extends GameState {
    private CameraService cameraService;
    private WorldService worldService;
    private EntityService entityService;
    private SystemManager systemManager;
    private RenderSystem renderSystem;
    private UIService uiService;
    private GameHUD gameHUD;
    private Player player;

    public Level2State(GameStateManager stateManager) {
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

        // Load the new dungeon level
        loadWorld();

        // Populate with entities
        createEntities();

        Gdx.app.log("Level2State", "Dungeon level created with " + entityService.getEntityCount() + " entities");
    }

    private void initializeUI() {
        // Clear any existing UI
        uiService.clear();

        // Create the game HUD using the modern UI system
        gameHUD = new GameHUD(uiService);

        Gdx.app.log("Level2State", "Modern UI system initialized");
    }

    private void loadWorld() {
        worldService.loadWorld("levels/level2.txt");
        Gdx.app.log("Level2State", "Loaded dungeon: " + worldService.getCurrentWorld().getWorldName());
    }

    private void createEntities() {
        EntityFactory entityFactory = ServiceLocator.get(EntityFactory.class);

        // Create player in the starting room (top-left area)
        player = entityFactory.createPlayer("Hero", 150, 580, CharacterType.MALE_KNIGHT);
        entityService.addEntity(player);

        // TOP-LEFT ROOM (Stone floor) - Starting area with weaker enemies
        entityService.addEntity(entityFactory.createMonster(200, 550, MonsterType.GOBLIN, 30, 1));
        entityService.addEntity(entityFactory.createNPC(250, 500, CharacterType.PRIEST, "Brother Aldwin"));

        // TOP-MIDDLE ROOM (Red brick floor with skull tiles) - Undead area
        entityService.addEntity(entityFactory.createMonster(420, 580, MonsterType.SKELETON, 40, 2));
        entityService.addEntity(entityFactory.createMonster(480, 530, MonsterType.SKELETON, 40, 2));
        entityService.addEntity(entityFactory.createMonster(450, 560, MonsterType.DEATH_KNIGHT, 60, 4));

        // TOP-RIGHT ROOM (Ice floor) - Ice/frozen enemies
        entityService.addEntity(entityFactory.createMonster(750, 580, MonsterType.ZOMBIE, 45, 3));
        entityService.addEntity(entityFactory.createMonster(800, 530, MonsterType.GHOUL, 35, 2));

        // CENTRAL CORRIDOR - Patrolling guards
        entityService.addEntity(entityFactory.createMonster(450, 400, MonsterType.ORC, 50, 3));

        // BOTTOM-LEFT ROOM (Purple cave floor) - Cave creatures
        entityService.addEntity(entityFactory.createMonster(150, 280, MonsterType.BIG_SLIME, 25, 2));
        entityService.addEntity(entityFactory.createMonster(200, 250, MonsterType.SMALL_SLIME, 15, 1));
        entityService.addEntity(entityFactory.createMonster(250, 200, MonsterType.GIANT_BAT, 20, 2));

        // LARGE CENTRAL CAVE ROOM (Maroon floor) - Boss area with multiple enemies
        entityService.addEntity(entityFactory.createMonster(500, 300, MonsterType.ORC_WIZARD, 70, 5));
        entityService.addEntity(entityFactory.createMonster(550, 250, MonsterType.GOBLIN, 30, 2));
        entityService.addEntity(entityFactory.createMonster(650, 300, MonsterType.SKELETON, 40, 3));
        entityService.addEntity(entityFactory.createMonster(600, 200, MonsterType.LICH, 55, 4));

        // NPCs scattered through the dungeon
        entityService.addEntity(entityFactory.createNPC(850, 250, CharacterType.MALE_WIZARD, "Trapped Mage"));
        entityService.addEntity(entityFactory.createNPC(100, 100, CharacterType.FEMALE_WIZARD, "Scholar Elena"));

        // Ambient creatures
        entityService.addEntity(entityFactory.createAnimal(350, 150, AnimalType.SNAKE));
        entityService.addEntity(entityFactory.createAnimal(700, 150, AnimalType.COBRA));

        Gdx.app.log("Level2State", "Dungeon populated with " + entityService.getEntityCount() + " entities");
    }

    @Override
    public void onEnter() {
        Gdx.app.log("Level2State", "Entered the dungeon - beware of dangers!");
    }

    @Override
    public void onExit() {
        Gdx.app.log("Level2State", "Exited the dungeon");
    }

    @Override
    public void onPause() {
        Gdx.app.log("Level2State", "Dungeon paused");
    }

    @Override
    public void onResume() {
        Gdx.app.log("Level2State", "Dungeon resumed");
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
        // Clear screen with darker background for dungeon atmosphere
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render game world and entities
        renderSystem.renderWorld();
        renderSystem.renderEntities();

        // Render UI
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
