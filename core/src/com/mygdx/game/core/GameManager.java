package com.mygdx.game.core;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.RPG;
import com.mygdx.game.camera.CameraManager;
import com.mygdx.game.dev.QuickMenu;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.input.InputManager;
import com.mygdx.game.level.Level;
import com.mygdx.game.ui.UIGameInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameManager implements Screen {
	private final RPG game;
	private Level level;
	private UIGameInfo uiGameInfo;

	private CameraManager cameraManager;
	private InputManager inputManager;

	QuickMenu devMenu;
	private boolean isDebug = false;
	private boolean isEntityDebug = false;

    public GameManager(final RPG game) {
		this.game = game;
		cameraManager = new CameraManager();
		level = new Level();
		uiGameInfo = new UIGameInfo(game.batch, game.font, this);
		inputManager = new InputManager(this);
		devMenu = new QuickMenu(this);
	}

	@Override
	public void render(float delta) {
		GameManagerUtils.updateEntitiesVisibility(level.getNpcList(), cameraManager);

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
				for (Entity entity : level.getNpcList()) {
					entity.getShapeRenderer().setProjectionMatrix(cameraManager.getCamera().combined);
					entity.drawDebug();
				}
			}
			devMenu.render(delta);
		}
	}

	private void collisionAndInteractionDetection() {
		for (Entity entity : level.getNpcList()) {
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