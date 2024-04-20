package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

public class GameManager implements Screen {
	private static final String BUCKET_IMAGE_PATH = "bucket.png";
	private static final String RAIN_MUSIC_PATH = "rain.mp3";
	private static final String MAP_PATH = "map.jpg";

	private final List<Entity> entities = new ArrayList<Entity>();
	private final RPG game;
	private Player player;
	private NPC npc;
	private UI ui;

	private Texture bucketTexture;
	private Texture mapTexture;
	private Music rainMusic;

	private CameraManager cameraManager;
	private InputManager inputManager;

	private boolean debugPlayer = true;

	ShapeRenderer shapeRenderer = new ShapeRenderer();

    public GameManager(final RPG game) {
		this.game = game;
		initialize();
	}

	private void initialize() {
		loadAssets();
		cameraManager = new CameraManager();
		npc = new NPC(100, 100);
		player = new Player(0, 0);
		entities.add(player);
		entities.add(npc);
		ui = new UI(game.batch, game.font, player);
		inputManager = new InputManager();
	}

	private void loadAssets() {
		bucketTexture = new Texture(Gdx.files.internal(BUCKET_IMAGE_PATH));
		mapTexture = new Texture(Gdx.files.internal(MAP_PATH));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal(RAIN_MUSIC_PATH));
		rainMusic.setLooping(true);
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

		game.batch.draw(bucketTexture, player.getX(), player.getY(), player.getWidth(), player.getHeight());
		game.batch.draw(bucketTexture, npc.getX(), npc.getY(), npc.getWidth(), npc.getHeight());
		game.batch.end();

		// DEBUG
		if (Gdx.input.isKeyJustPressed(Input.Keys.F1)) {
			toggleDebug();
		}
		if (debugPlayer) {
			for (Entity entity : entities) {
				entity.renderCollisionBox(shapeRenderer);
			}
		}
	}

	public void toggleDebug() {
		debugPlayer = !debugPlayer;
	}

	@Override
	public void show() {
//		rainMusic.play();
	}

	@Override
	public void dispose() {
		bucketTexture.dispose();
		rainMusic.dispose();
		shapeRenderer.dispose();
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