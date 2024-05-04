package com.mygdx.game.gameManager;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.RPG;
import com.mygdx.game.assetManager.AssetManager;
import com.mygdx.game.cameraManager.CameraManager;
import com.mygdx.game.commandManager.CommandManager;
import com.mygdx.game.developerOptions.QuickMenu;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.inputManager.InputManager;
import com.mygdx.game.levelManager.Level;
import com.mygdx.game.uiManager.UIGameInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameManager implements Screen {
	private final RPG game;
	private AssetManager assetManager;
	private CommandManager commandManager;
	private CameraManager cameraManager;
	private InputManager inputManager;

	private Level level;
	private UIGameInfo uiGameInfo;

	QuickMenu devMenu;
	private boolean isDebug = false;
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

	@Override
	public void render(float delta) {
		updateEntities();
		renderGame(delta);
		detectCollisionsAndInteractions();
	}

	private void updateEntities() {
		GameManagerUtils.updateEntitiesVisibility(level, cameraManager);
		this.inputManager.update();
		cameraManager.updateCameraPosition(level.getPlayer());
	}

	private void renderGame(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);
		game.batch.setProjectionMatrix(cameraManager.getCamera().combined);
		game.batch.begin();
		draw();
		uiGameInfo.draw();
		game.batch.end();

		if (isDebug) {
			renderDebug(delta);
		}

		renderEntityHighlights();
	}

	private void renderDebug(float delta) {
		if (isEntityDebug) {
			renderEntityDebug();
		}
		devMenu.render(delta);
	}

	private void renderEntityDebug() {
		level.getPlayer().getShapeRenderer().setProjectionMatrix(cameraManager.getCamera().combined);
		level.getPlayer().drawDebug();
		for (Entity entity : level.getCharacterList()) {
			entity.getShapeRenderer().setProjectionMatrix(cameraManager.getCamera().combined);
			entity.drawDebug();
		}
	}

	private void renderEntityHighlights() {
		for (Entity entity : level.getEntitiesInView()) {
			if (entity.isHighlight()) {
				entity.getShapeRenderer().setProjectionMatrix(cameraManager.getCamera().combined);
				entity.drawHighlight();
			}
		}
	}

	private void detectCollisionsAndInteractions() {
		for (Entity entity : level.getCharacterList()) {
			if (entity != level.getPlayer()) {
				detectCollisionWithPlayer(entity);
				detectInteractionWithPlayer(entity);
			}
		}
	}

	private void detectCollisionWithPlayer(Entity entity) {
		if (level.getPlayer().collidesWith(entity)) {
			// handle collision
		}
	}

	private void detectInteractionWithPlayer(Entity entity) {
		if (level.getPlayer().interactsWith(entity)) {
			// handle interaction
		}
	}

	public void draw() {
		level.draw(game.batch);
	}

	@Override
	public void show() {

	}

	@Override
	public void dispose() {
		game.batch.dispose();
		game.font.dispose();
		level.dispose();
	}

	@Override
	public void resize(int width, int height) {

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
}