package com.mygdx.game.gameManager;

import com.mygdx.game.RPG;
import com.mygdx.game.assetManager.AssetManager;
import com.mygdx.game.cameraManager.CameraManager;
import com.mygdx.game.commandManager.CommandManager;
import com.mygdx.game.developerOptions.QuickMenu;
import com.mygdx.game.entity.Entity;
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

@Getter
@Setter
public class GameManager {
    private final RPG game;
    private AssetManager assetManager;
    private CommandManager commandManager;
    private CameraManager cameraManager;
    private InputManager inputManager;
    private final EventBus eventBus;

    private Level level;
    private UIGameInfo uiGameInfo;

    QuickMenu devMenu;
    private boolean isDevMenuOpen = false;
    private boolean isEntityDebug = false;

    public GameManager(final RPG game, AssetManager assetManager, CommandManager commandManager, CameraManager cameraManager, InputManager inputManager) {
        this.game = game;
        this.assetManager = assetManager;
        this.commandManager = commandManager;
        this.commandManager.init();
        this.cameraManager = cameraManager;
        this.inputManager = inputManager;

        // Remove circular dependencies - set dependencies without GameManager reference
        this.inputManager.setLevel(null); // Will be set after level creation
        this.inputManager.setCameraManager(cameraManager);
        this.inputManager.setGame(game);

        level = new Level(assetManager);
        level.setTileList(getAssetManager().getLevels().get(0));
        this.inputManager.setLevel(level);

        uiGameInfo = new UIGameInfo(game.batch, game.font, this);
        devMenu = new QuickMenu(this);

        // Initialize EventBus and subscribe to events
        this.eventBus = EventBus.getInstance();
        subscribeToEvents();
    }

    /**
     * Subscribe to all events this manager needs to handle
     */
    private void subscribeToEvents() {
        // Player movement events
        eventBus.subscribe(PlayerMoveEvent.class, this::onPlayerMove);
        eventBus.subscribe(PlayerStopEvent.class, this::onPlayerStop);

        // UI/Debug events
        eventBus.subscribe(ToggleDebugEvent.class, this::onToggleDebug);
        eventBus.subscribe(ToggleEntityDebugEvent.class, this::onToggleEntityDebug);

        // Screen change events
        eventBus.subscribe(ScreenChangeEvent.class, this::onScreenChange);

        // Entity interaction events
        eventBus.subscribe(EntityHighlightEvent.class, this::onEntityHighlight);
        eventBus.subscribe(EntityClickEvent.class, this::onEntityClick);

        // Command events
        eventBus.subscribe(RemoveEntityEvent.class, this::onRemoveEntity);
        eventBus.subscribe(RemoveLastEntityEvent.class, this::onRemoveLastEntity);
        eventBus.subscribe(LoadLevelEvent.class, this::onLoadLevel);
        eventBus.subscribe(GenerateRandomTilesEvent.class, this::onGenerateRandomTiles);
        eventBus.subscribe(AddEntityAtPlayerEvent.class, this::onAddEntityAtPlayer);
    }

    // Event handlers
    private void onPlayerMove(PlayerMoveEvent event) {
        level.getPlayer().move(event.getDx(), event.getDy());
    }

    private void onPlayerStop(PlayerStopEvent event) {
        level.getPlayer().stop();
    }

    private void onToggleDebug(ToggleDebugEvent event) {
        isDevMenuOpen = !isDevMenuOpen;
    }

    private void onToggleEntityDebug(ToggleEntityDebugEvent event) {
        isEntityDebug = !isEntityDebug;
    }

    private void onScreenChange(ScreenChangeEvent event) {
        game.setScreen(event.getNewScreen());
    }

    private void onEntityHighlight(EntityHighlightEvent event) {
        for (Entity entity : level.getEntitiesInView()) {
            entity.setHighlight(entity.containsPoint(event.getWorldX(), event.getWorldY()));
        }
    }

    private void onEntityClick(EntityClickEvent event) {
        // Handle entity clicks (can be extended in the future)
    }

    private void onRemoveEntity(RemoveEntityEvent event) {
        level.getCharacterList().removeIf(entity -> entity.getId().toString().equals(event.getEntityId()));
    }

    private void onRemoveLastEntity(RemoveLastEntityEvent event) {
        if (!level.getCharacterList().isEmpty()) {
            level.getCharacterList().removeLast();
        }
    }

    private void onLoadLevel(LoadLevelEvent event) {
        level.clearTileList();
        level.setTileList(assetManager.getLevels().get(event.getLevelId()));
    }

    private void onGenerateRandomTiles(GenerateRandomTilesEvent event) {
        level.getTileList().clear();
        level.generateLevel();
    }

    private void onAddEntityAtPlayer(AddEntityAtPlayerEvent event) {
        level.getCharacterList().add(new com.mygdx.game.entity.Character(level.getPlayer().getPosition().getX(), level.getPlayer().getPosition().getY(), assetManager.getRandomTexture(com.mygdx.game.enums.TextureTypeEnum.ROGUE)));
    }

    public void updateEntities() {
        GameManagerUtils.updateEntitiesVisibility(level, cameraManager);
        this.inputManager.update();
        cameraManager.updateCameraPosition(level.getPlayer());

        // Process any queued events
        eventBus.processQueue();
    }

    public void renderGame(float delta) {
        RenderingManager.renderGame(getGame().getBatch(), level, uiGameInfo, isEntityDebug, getCameraManager().getCamera());
        if (isDevMenuOpen) {
            renderDevMenu(delta);
        }
    }

    public void renderDevMenu(float delta) {
        devMenu.render(delta);
    }

    public void dispose() {
        // Clean up event subscriptions
        eventBus.clear();
    }
}
