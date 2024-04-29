package com.mygdx.game.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.RPG;
import com.mygdx.game.camera.CameraManager;
import com.mygdx.game.dev.QuickMenu;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.input.InputManager;
import com.mygdx.game.level.Level;
import com.mygdx.game.ui.UI;
import com.mygdx.game.utils.Keybindings;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameManager implements Screen {
	private final RPG game;
	private Level level;
	private UI ui;

	private CameraManager cameraManager;
	private InputManager inputManager;

	QuickMenu devMenu;
	private boolean isDebug = false;
	private boolean isEntityDebug = false;

    public GameManager(final RPG game) {
		this.game = game;
		cameraManager = new CameraManager();
		level = new Level();
		ui = new UI(game.batch, game.font, level.getPlayer());
		inputManager = new InputManager();
		devMenu = new QuickMenu(this);
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);

		this.inputManager.movement(level.getPlayer());
		cameraManager.updateCameraPosition(level.getPlayer());

		game.batch.setProjectionMatrix(cameraManager.getCamera().combined);
		game.batch.begin();

		draw();
		ui.draw(cameraManager.getCamera());
		game.batch.end();

		collisionAndInteractionDetection();

		// DEBUG
		if (Gdx.input.isKeyJustPressed(Keybindings.DEBUG_KEY)) {
			toggleDebug();
		}

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

	public void toggleDebug() {
		isDebug = !isDebug;
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