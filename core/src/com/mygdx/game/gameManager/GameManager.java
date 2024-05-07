package com.mygdx.game.gameManager;

import com.mygdx.game.RPG;
import com.mygdx.game.assetManager.AssetManager;
import com.mygdx.game.cameraManager.CameraManager;
import com.mygdx.game.commandManager.CommandManager;
import com.mygdx.game.developerOptions.QuickMenu;
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

	private Level level;
	private UIGameInfo uiGameInfo;

	QuickMenu devMenu;
	private boolean isDevMenuOpen = false;
	private boolean isEntityDebug = false;

    public GameManager(final RPG game, AssetManager assetManager, CommandManager commandManager, CameraManager cameraManager, InputManager inputManager) {
		this.game = game;
		this.assetManager = assetManager;
		this.commandManager = commandManager;
		this.commandManager.setGameManager(this);
		this.commandManager.init();
		this.cameraManager = cameraManager;
		this.inputManager = inputManager;
		this.inputManager.setGameManager(this);

		level = new Level(assetManager);
		level.setTileList(getAssetManager().getLevels().get(0));
		uiGameInfo = new UIGameInfo(game.batch, game.font, this);

		devMenu = new QuickMenu(this);
	}

	public void updateEntities() {
		GameManagerUtils.updateEntitiesVisibility(level, cameraManager);
		this.inputManager.update();
		cameraManager.updateCameraPosition(level.getPlayer());
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
}