package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameEngine implements Screen {
	private static final String BUCKET_IMAGE_PATH = "bucket.png";
	private static final String RAIN_MUSIC_PATH = "rain.mp3";
	private static final String MAP_PATH = "map.jpg";

	private final RPG game;
	private PC pc;
	private NPC npc;
	private UI ui;

	private Texture bucketTexture;
	private Texture mapTexture;
	private Music rainMusic;
	private CameraManager cameraManager;

	private boolean debugPlayer = true;

    public GameEngine(final RPG game) {
		this.game = game;
		initialize();
	}

	private void initialize() {
		loadAssets();
		cameraManager = new CameraManager(Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
		npc = new NPC(0, 0);
		pc = new PC(0, 0);
		ui = new UI(game.batch, game.font, pc);
	}

	private void loadAssets() {
		bucketTexture = new Texture(Gdx.files.internal(BUCKET_IMAGE_PATH));
		mapTexture = new Texture(Gdx.files.internal(MAP_PATH));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal(RAIN_MUSIC_PATH));
		rainMusic.setLooping(true);
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);
		cameraManager.updateCameraPosition(pc);

		game.batch.setProjectionMatrix(cameraManager.getCamera().combined);
		game.batch.begin();
		game.batch.draw(mapTexture, 0, 0);

		ui.draw(cameraManager.getCamera());

		game.batch.draw(bucketTexture, pc.getX(), pc.getY(), pc.getWidth(), pc.getHeight());
		game.batch.end();

		pc.processInput();

		// DEBUG
		if (Gdx.input.isKeyJustPressed(Input.Keys.F1)) {
			toggleDebug();
		}
		if (debugPlayer) {
			pc.drawDebug();
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
		pc.getInteractionShape().dispose();
		pc.getCollisionShape().dispose();
		npc.getInteractionShape().dispose();
		npc.getCollisionShape().dispose();
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