package com.mygdx.game.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.*;
import com.mygdx.game.dev.DeveloperMenuScreen;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.NPC;
import com.mygdx.game.entities.Player;
import com.mygdx.game.ui.UI;

import java.util.ArrayList;
import java.util.List;

public class GameManager implements Screen {
	private static final String BUCKET_IMAGE_PATH = "bucket.png";
	private static final String MAP_PATH = "map.jpg";

	private final RPG game;
	private UI ui;

	private final List<Entity> entities = new ArrayList<Entity>();
	private Player player;

	private Texture bucketTexture;
	private Texture mapTexture;

	private CameraManager cameraManager;
	private InputManager inputManager;

	ShapeRenderer entityCollisionBoxRenderer = new ShapeRenderer();
	ShapeRenderer entityInteractionBoxRenderer = new ShapeRenderer();

	DeveloperMenuScreen developerMenuScreen;
	private boolean isDeveloperMenuOpen = false;
	private boolean debugPlayer = false;

    public GameManager(final RPG game) {
		this.game = game;
		initialize();
	}

	private void initialize() {
		loadAssets();
		cameraManager = new CameraManager();
		player = new Player(0, 0);
		entities.add(player);
		ui = new UI(game.batch, game.font, player);
		inputManager = new InputManager();
		developerMenuScreen = new DeveloperMenuScreen(game);
	}

	private void loadAssets() {
		try {
			bucketTexture = new Texture(Gdx.files.internal(BUCKET_IMAGE_PATH));
			mapTexture = new Texture(Gdx.files.internal(MAP_PATH));
		} catch (Exception e) {
			Gdx.app.error("GameManager", "Error loading assets", e);
		}
	}
	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);

		this.inputManager.movement(player);
		cameraManager.updateCameraPosition(player);

		game.batch.setProjectionMatrix(cameraManager.getCamera().combined);
		game.batch.begin();
		game.batch.draw(mapTexture, 0, 0);

		ui.draw(cameraManager.getCamera());

		drawEntities();
		game.batch.end();

		collisionAndInteractionDetection();

		// DEBUG
		if (Gdx.input.isKeyJustPressed(Input.Keys.F1)) {
			toggleDebug();
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.F2)) {
			isDeveloperMenuOpen = !isDeveloperMenuOpen;
		}

		if (isDeveloperMenuOpen) {
			developerMenuScreen.render(delta);
		}
		if (debugPlayer) {
			entityCollisionBoxRenderer.setProjectionMatrix(cameraManager.getCamera().combined);
			entityInteractionBoxRenderer.setProjectionMatrix(cameraManager.getCamera().combined);
			for (Entity entity : entities) {
				entity.renderCollisionBox(entityCollisionBoxRenderer);
				entity.renderInteractionBox(entityInteractionBoxRenderer);
			}
		}
	}

	private void collisionAndInteractionDetection() {
		for (Entity entity : entities) {
			if (entity != player && player.collidesWith(entity)) {
				System.out.println("Collision detected!");
			}
			if (entity != player && player.interactsWith(entity)) {
				System.out.println("Interaction detected!");
			}
		}
	}

	public void addNPC() {
		NPC newNpc = new NPC(player.getX(), player.getY() + 100);
		entities.add(newNpc);
	}

	public void listEntities() {
		for (Entity entity : entities) {
			System.out.println(entity + " at " + entity.getPosition());
		}
	}

	public void drawEntities() {
		for (Entity entity : entities) {
			game.batch.draw(bucketTexture, entity.getX(), entity.getY(), entity.getWidth(), entity.getHeight());
		}
	}

	public void toggleDebug() {
		debugPlayer = !debugPlayer;
	}

	@Override
	public void show() {

	}

	@Override
	public void dispose() {
		bucketTexture.dispose();
		entityCollisionBoxRenderer.dispose();
		entityInteractionBoxRenderer.dispose();
		mapTexture.dispose();
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

	public RPG getGame() {
		return game;
	}
}