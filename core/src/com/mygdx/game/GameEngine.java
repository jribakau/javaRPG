package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class GameEngine implements Screen {
	private static final int BUCKET_WIDTH = 64;
	private static final int BUCKET_HEIGHT = 64;
	private static final String BUCKET_IMAGE_PATH = "bucket.png";
	private static final String RAIN_MUSIC_PATH = "rain.mp3";
	private static final String MAP_PATH = "map.jpg";
	private static final Boolean DRAW_OBSTACLES = false;

	private final RPG game;
	private PC pc;
	private NPC npc;
	private UI ui;
	private ArrayList<Obstacle> obstacles;

	private Texture bucketTexture;
	private Texture obstacleTexture;
	private Texture mapTexture;
	private Music rainMusic;
	private CameraManager cameraManager;

	private boolean debugInteractionAndCollisionBoxes = true;

    public GameEngine(final RPG game) {
		this.game = game;
		initialize();
	}

	private void initialize() {
		loadAssets();
		cameraManager = new CameraManager(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		createPlayerCharacter();
		createObstacles();
		createNonPlayerCharacter();
		ui = new UI(game.batch, game.font, pc, npc);
	}

	private void loadAssets() {
		bucketTexture = new Texture(Gdx.files.internal(BUCKET_IMAGE_PATH));
		mapTexture = new Texture(Gdx.files.internal(MAP_PATH));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal(RAIN_MUSIC_PATH));
		rainMusic.setLooping(true);
	}

	private void createPlayerCharacter() {
		pc = new PC("Player1", Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, BUCKET_WIDTH, BUCKET_HEIGHT);
	}

	private void createObstacles() {
		obstacles = new ArrayList<Obstacle>();
		if (DRAW_OBSTACLES) {
			generateObstacles();
		}
		createObstacleTexture();
	}

	private void createNonPlayerCharacter() {
		int npcWidth = 64;
		int npcHeight = 64;
		int npcX = (Constants.SCREEN_WIDTH - npcWidth) / 2;
		int npcY = (Constants.SCREEN_HEIGHT - npcHeight) / 2;
		npc = new NPC("NPC1", 1, 100, new Rectangle(npcX, npcY, npcWidth, npcHeight), "Hello, Player!");
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
		cameraManager.updateCameraPosition(pc);

		game.batch.setProjectionMatrix(cameraManager.getCamera().combined);
		game.batch.begin();
		game.batch.draw(mapTexture, 0, 0);

		boolean showTooltip = pc.getInteractionRange().overlaps(npc.getInteractionRange());

		if (showTooltip) {
			game.font.draw(game.batch, "E", npc.getPosition().x, npc.getPosition().y + npc.getPosition().height + 20);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.E) && showTooltip) {
			game.font.draw(game.batch, npc.getDialogue(), 0, Constants.SCREEN_HEIGHT - 80);
		}

		ui.draw(cameraManager.getCamera());

		if (DRAW_OBSTACLES) {
			drawObstacles();
		}

		game.batch.draw(bucketTexture, pc.getPosition().x, pc.getPosition().y, BUCKET_WIDTH, BUCKET_HEIGHT);
		game.batch.draw(bucketTexture, npc.getPosition().x, npc.getPosition().y, BUCKET_WIDTH, BUCKET_HEIGHT);
		game.batch.end();

		pc.handleInput();
		handlePlayerCollision();

		// DEBUG
		if (Gdx.input.isKeyJustPressed(Input.Keys.F1)) {
			toggleDebug();
		}
		if (debugInteractionAndCollisionBoxes) {
			pc.drawDebug();
			npc.drawDebug();
		}
	}

	public void handlePlayerCollision() {
		for (Obstacle obstacle : obstacles) {
			if (pc.getCollisionRange().overlaps(obstacle.getBounds())) {
				pc.dealDamage(10);
				break;
			}
		}

		if (pc.getCollisionRange().overlaps(npc.getCollisionRange())) {
			// TODO: Handle collision with NPC
		}
	}

	private void drawObstacles() {
		for (Obstacle obstacle : obstacles) {
			game.batch.draw(obstacleTexture, obstacle.getBounds().x, obstacle.getBounds().y, BUCKET_WIDTH, BUCKET_HEIGHT);
		}
	}

	private void generateObstacles() {
		for (int i = 0; i < 10; i++) {
			int x = MathUtils.random(0, Constants.SCREEN_HEIGHT - BUCKET_WIDTH);
			int y = MathUtils.random(0, Constants.SCREEN_HEIGHT - BUCKET_HEIGHT);
			obstacles.add(new Obstacle(x, y, BUCKET_WIDTH, BUCKET_HEIGHT));
		}
	}

	public void toggleDebug() {
		debugInteractionAndCollisionBoxes = !debugInteractionAndCollisionBoxes;
	}

	@Override
	public void show() {
		rainMusic.play();
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