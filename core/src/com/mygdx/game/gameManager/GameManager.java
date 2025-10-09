package com.mygdx.game.gameManager;

import com.mygdx.game.RPG;
import com.mygdx.game.assetManager.AssetManager;
import com.mygdx.game.cameraManager.CameraManager;
import com.mygdx.game.commandManager.CommandManager;
import com.mygdx.game.developerOptions.QuickMenu;
import com.mygdx.game.events.EventBus;
import com.mygdx.game.events.entity.*;
import com.mygdx.game.events.input.PlayerMoveEvent;
import com.mygdx.game.events.input.PlayerStopEvent;
import com.mygdx.game.events.level.GenerateRandomTilesEvent;
import com.mygdx.game.events.level.LoadLevelEvent;
import com.mygdx.game.events.ui.ScreenChangeEvent;
import com.mygdx.game.events.ui.ToggleDebugEvent;
import com.mygdx.game.events.ui.ToggleEntityDebugEvent;
import com.mygdx.game.inputManager.InputManager;
import com.mygdx.game.levelManager.Level;
import com.mygdx.game.renderingManager.RenderingManager;
import com.mygdx.game.uiManager.UIGameInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * Central game manager that coordinates all game systems.
 * Responsibilities:
 * - Initialize and coordinate game subsystems
 * - Manage game state (level, UI, debug modes)
 * - Handle event subscriptions and routing
 * - Orchestrate game loop (update/render)
 */
@Getter
@Setter
public class GameManager {
    // ========== Core Dependencies ==========
    private final RPG game;
    private final AssetManager assetManager;
    private final CommandManager commandManager;
    private final CameraManager cameraManager;
    private final InputManager inputManager;
    private final EventBus eventBus;

    // ========== Game State ==========
    private final Level level;
    private final UIGameInfo uiGameInfo;
    private final QuickMenu devMenu;
    private final GameEventHandler eventHandler;

    // ========== Debug Flags ==========
    private boolean isDevMenuOpen = false;
    private boolean isEntityDebug = false;

    public GameManager(RPG game, AssetManager assetManager, CommandManager commandManager, CameraManager cameraManager, InputManager inputManager) {
        // Initialize core dependencies
        this.game = game;
        this.assetManager = assetManager;
        this.commandManager = commandManager;
        this.cameraManager = cameraManager;
        this.inputManager = inputManager;
        this.eventBus = EventBus.getInstance();

        // Initialize game state
        this.level = initializeLevel();
        this.eventHandler = new GameEventHandler(level, assetManager);
        this.uiGameInfo = new UIGameInfo(game.batch, game.font, this);
        this.devMenu = new QuickMenu(this);

        // Configure subsystems
        configureInputManager();
        commandManager.init();

        // Register event listeners
        subscribeToEvents();
    }

    // ========== Initialization ==========

    private Level initializeLevel() {
        Level newLevel = new Level(assetManager);
        newLevel.setTileList(assetManager.getLevels().getFirst());
        return newLevel;
    }

    private void configureInputManager() {
        inputManager.setLevel(level);
        inputManager.setCameraManager(cameraManager);
        inputManager.setGame(game);
    }

    // ========== Event Subscription ==========

    /**
     * Subscribe to all events this manager needs to handle.
     * Events are organized by category for clarity.
     */
    private void subscribeToEvents() {
        subscribeToPlayerEvents();
        subscribeToUIEvents();
        subscribeToEntityEvents();
        subscribeToLevelEvents();
    }

    private void subscribeToPlayerEvents() {
        eventBus.subscribe(PlayerMoveEvent.class, eventHandler::onPlayerMove);
        eventBus.subscribe(PlayerStopEvent.class, eventHandler::onPlayerStop);
    }

    private void subscribeToUIEvents() {
        eventBus.subscribe(ToggleDebugEvent.class, this::onToggleDebug);
        eventBus.subscribe(ToggleEntityDebugEvent.class, this::onToggleEntityDebug);
        eventBus.subscribe(ScreenChangeEvent.class, this::onScreenChange);
    }

    private void subscribeToEntityEvents() {
        eventBus.subscribe(EntityHighlightEvent.class, eventHandler::onEntityHighlight);
        eventBus.subscribe(EntityClickEvent.class, eventHandler::onEntityClick);
        eventBus.subscribe(RemoveEntityEvent.class, eventHandler::onRemoveEntity);
        eventBus.subscribe(RemoveLastEntityEvent.class, eventHandler::onRemoveLastEntity);
        eventBus.subscribe(AddEntityAtPlayerEvent.class, eventHandler::onAddEntityAtPlayer);
    }

    private void subscribeToLevelEvents() {
        eventBus.subscribe(LoadLevelEvent.class, eventHandler::onLoadLevel);
        eventBus.subscribe(GenerateRandomTilesEvent.class, eventHandler::onGenerateRandomTiles);
    }

    // ========== Event Handlers (UI/Screen Only) ==========

    private void onToggleDebug(ToggleDebugEvent event) {
        isDevMenuOpen = !isDevMenuOpen;
    }

    private void onToggleEntityDebug(ToggleEntityDebugEvent event) {
        isEntityDebug = !isEntityDebug;
    }

    private void onScreenChange(ScreenChangeEvent event) {
        game.setScreen(event.getNewScreen());
    }

    // ========== Game Loop ==========

    /**
     * Update game state each frame.
     * Order matters: entities -> input -> camera -> events
     */
    public void updateEntities() {
        GameManagerUtils.updateEntitiesVisibility(level, cameraManager);
        inputManager.update();
        cameraManager.updateCameraPosition(level.getPlayer());
        eventBus.processQueue();
    }

    /**
     * Render the game scene and UI.
     */
    public void renderGame(float delta) {
        RenderingManager.renderGame(game.getBatch(), level, uiGameInfo, isEntityDebug, cameraManager.getCamera());

        if (isDevMenuOpen) {
            devMenu.render(delta);
        }
    }

    // ========== Cleanup ==========

    public void dispose() {
        eventBus.clear();
        level.dispose();
    }
}
