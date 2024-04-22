package com.mygdx.game.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.*;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.NPC;
import com.mygdx.game.entities.Player;
import com.mygdx.game.ui.UI;

import java.util.ArrayList;
import java.util.List;

public class GameManager implements Screen {
	private static final String BUCKET_IMAGE_PATH = "bucket.png";
	private static final String MAP_PATH = "map.jpg";

	private final List<Entity> entities = new ArrayList<Entity>();
	private final RPG game;
	private Player player;
	private NPC npc;
	private UI ui;

	private Texture bucketTexture;
	private Texture mapTexture;

	private CameraManager cameraManager;
	private InputManager inputManager;

	private boolean debugPlayer = true;

	ShapeRenderer entityCollisionBoxRenderer = new ShapeRenderer();
	ShapeRenderer entityInteractionBoxRenderer = new ShapeRenderer();

    public GameManager(final RPG game) {
		this.game = game;
		initialize();
	}

	private void initialize() {
		loadAssets();
		cameraManager = new CameraManager();
		npc = new NPC(200, 200);
		player = new Player(0, 0);
		entities.add(player);
		entities.add(npc);
		ui = new UI(game.batch, game.font, player);
		inputManager = new InputManager();
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
		this.inputManager.movement(player);
		cameraManager.updateCameraPosition(player);

		ScreenUtils.clear(0, 0, 0.2f, 1);

		game.batch.setProjectionMatrix(cameraManager.getCamera().combined);
		game.batch.begin();
		game.batch.draw(mapTexture, 0, 0);

		ui.draw(cameraManager.getCamera());

		game.batch.draw(bucketTexture, npc.getX(), npc.getY(), npc.getWidth(), npc.getHeight());
		game.batch.draw(bucketTexture, player.getX(), player.getY(), player.getWidth(), player.getHeight());
		game.batch.end();

		for (Entity entity : entities) {
			if (entity != player && player.collidesWith(entity)) {
				System.out.println("Collision detected!");
			}
			if (entity != player && player.interactsWith(entity)) {
				System.out.println("Interaction detected!");
			}
		}

		// DEBUG
		if (Gdx.input.isKeyJustPressed(Input.Keys.F1)) {
			toggleDebug();
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
}