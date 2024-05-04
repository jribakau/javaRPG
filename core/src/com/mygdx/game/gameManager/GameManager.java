package com.mygdx.game.gameManager;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.RPG;
import com.mygdx.game.assetManager.AssetManager;
import com.mygdx.game.cameraManager.CameraManager;
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
	private AssetManager assetManager;
	private final RPG game;
	private Level level;
	private UIGameInfo uiGameInfo;

	private CameraManager cameraManager;
	private InputManager inputManager;

	QuickMenu devMenu;
	private boolean isDebug = false;
	private boolean isEntityDebug = false;

    public GameManager(final RPG game) {
		assetManager = new AssetManager();
		this.game = game;
		cameraManager = new CameraManager();
		level = new Level(assetManager);
		level.setTileList(getAssetManager().getLevels().get(0));
		uiGameInfo = new UIGameInfo(game.batch, game.font, this);
		inputManager = new InputManager(this);
		devMenu = new QuickMenu(this);
	}

	@Override
	public void render(float delta) {
		GameManagerUtils.updateEntitiesVisibility(level, cameraManager);

		ScreenUtils.clear(0, 0, 0.2f, 1);

		this.inputManager.update();
		cameraManager.updateCameraPosition(level.getPlayer());

		game.batch.setProjectionMatrix(cameraManager.getCamera().combined);
		game.batch.begin();

		draw();
		uiGameInfo.draw();
		game.batch.end();

		collisionAndInteractionDetection();

		// DEBUG
		if (isDebug) {
			if (isEntityDebug) {
				level.getPlayer().getShapeRenderer().setProjectionMatrix(cameraManager.getCamera().combined);
				level.getPlayer().drawDebug();
				for (Entity entity : level.getCharacterList()) {
					entity.getShapeRenderer().setProjectionMatrix(cameraManager.getCamera().combined);
					entity.drawDebug();
				}
			}
			devMenu.render(delta);
		}

		for (Entity entity : level.getEntitiesInView()) {
			if (entity.isHighlight()) {
				entity.getShapeRenderer().setProjectionMatrix(cameraManager.getCamera().combined);
				entity.drawHighlight();
			}
		}
	}

	private void collisionAndInteractionDetection() {
		for (Entity entity : level.getCharacterList()) {
			if (entity != level.getPlayer() && level.getPlayer().collidesWith(entity)) {

			}
			if (entity != level.getPlayer() && level.getPlayer().interactsWith(entity)) {

			}
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