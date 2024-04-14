package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class GameScreen implements Screen {
	private static final int BUCKET_WIDTH = 64;
	private static final int BUCKET_HEIGHT = 64;
	private static final String BUCKET_IMAGE_PATH = "bucket.png";
	private static final String RAIN_MUSIC_PATH = "rain.mp3";

	private final RPG game;
	private Player player;
	private ArrayList<Obstacle> obstacles;

	private Texture bucketImage;
	private Texture obstacleTexture;
	private Music rainMusic;
	private OrthographicCamera camera;

	public GameScreen(final RPG game) {
		this.game = game;
		initialize();
	}

	private void initialize() {
		loadAssets();
		setupCamera();
		player = new Player("Player1", Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, BUCKET_WIDTH, BUCKET_HEIGHT);
		obstacles = new ArrayList<Obstacle>();
		generateObstacles();
		createObstacleTexture();
	}

	private void loadAssets() {
		bucketImage = new Texture(Gdx.files.internal(BUCKET_IMAGE_PATH));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal(RAIN_MUSIC_PATH));
		rainMusic.setLooping(true);
	}

	private void setupCamera() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
	}

	private void createObstacleTexture() {
		Pixmap pixmap = new Pixmap(BUCKET_WIDTH, BUCKET_HEIGHT, Pixmap.Format.RGBA8888);
		pixmap.setColor(1, 0, 0, 1);
		pixmap.fill();
		obstacleTexture = new Texture(pixmap);
		pixmap.dispose();
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();

		for (Obstacle obstacle : obstacles) {
			game.batch.draw(obstacleTexture, obstacle.getBounds().x, obstacle.getBounds().y, BUCKET_WIDTH, BUCKET_HEIGHT);
		}

		String playerStatus = player.isAlive() ? "Alive" : "Dead";
		game.font.draw(game.batch, playerStatus, 0, Constants.SCREEN_HEIGHT);

		game.batch.draw(bucketImage, player.getBucket().x, player.getBucket().y, BUCKET_WIDTH, BUCKET_HEIGHT);
		game.batch.end();

		player.handleInput();
		checkCollisions();
	}

	private void generateObstacles() {
		for (int i = 0; i < 10; i++) {
			int x = MathUtils.random(0, Constants.SCREEN_HEIGHT - BUCKET_WIDTH);
			int y = MathUtils.random(0, Constants.SCREEN_HEIGHT - BUCKET_HEIGHT);
			obstacles.add(new Obstacle(x, y, BUCKET_WIDTH, BUCKET_HEIGHT));
		}
	}

	private void checkCollisions() {
		for (Obstacle obstacle : obstacles) {
			if (obstacle.collidesWith(player.getBucket())) {
				player.dealDamage(player.getHealth());
				break;
			}
		}
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