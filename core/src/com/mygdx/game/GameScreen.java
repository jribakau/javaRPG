package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.*;

public class GameScreen implements Screen {
	private static final int SCREEN_WIDTH = 800;
	private static final int SCREEN_HEIGHT = 480;
	private static final int BUCKET_WIDTH = 64;
	private static final int BUCKET_HEIGHT = 64;
	private static final String BUCKET_IMAGE_PATH = "bucket.png";
	private static final String RAIN_MUSIC_PATH = "rain.mp3";

	private final RPG game;
	private Texture bucketImage;
	private Music rainMusic;
	private OrthographicCamera camera;
	private Rectangle bucket;
	private float playerSpeed;

	public GameScreen(final RPG game) {
		this.game = game;
		initialize();
	}

	private void initialize() {
		loadAssets();
		setupCamera();
		setupBucket();
		playerSpeed = 200 * Gdx.graphics.getDeltaTime();
	}

	private void loadAssets() {
		bucketImage = new Texture(Gdx.files.internal(BUCKET_IMAGE_PATH));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal(RAIN_MUSIC_PATH));
		rainMusic.setLooping(true);
	}

	private void setupCamera() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
	}

	private void setupBucket() {
		bucket = new Rectangle();
		bucket.x = SCREEN_WIDTH / 2 - BUCKET_WIDTH / 2;
		bucket.y = 20;
		bucket.width = BUCKET_WIDTH;
		bucket.height = BUCKET_HEIGHT;
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		game.font.draw(game.batch, "Hello there", 0, SCREEN_HEIGHT);
		game.batch.draw(bucketImage, bucket.x, bucket.y, bucket.width, bucket.height);
		game.batch.end();
		playerInput();
		restrictWithinScreenBounds();
	}

	private void restrictWithinScreenBounds() {
		bucket.x = Math.max(0, Math.min(SCREEN_WIDTH - BUCKET_WIDTH, bucket.x));
		bucket.y = Math.max(0, Math.min(SCREEN_HEIGHT - BUCKET_HEIGHT, bucket.y));
	}

	private void playerInput() {
		float dx = 0, dy = 0;
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) dx -= playerSpeed;
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) dx += playerSpeed;
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) dy += playerSpeed;
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) dy -= playerSpeed;
		normalizeAndMove(dx, dy);
	}

	private void normalizeAndMove(float dx, float dy) {
		if (dx != 0 || dy != 0) {
			float length = (float) Math.sqrt(dx * dx + dy * dy);
			dx /= length;
			dy /= length;
		}
		bucket.x += (int) (dx * playerSpeed);
		bucket.y += (int) (dy * playerSpeed);
	}

	@Override
	public void show() {
		rainMusic.play();
	}

	@Override
	public void dispose() {
		bucketImage.dispose();
		rainMusic.dispose();
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
