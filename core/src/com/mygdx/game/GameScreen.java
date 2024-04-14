package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.awt.*;

public class GameScreen implements Screen {
	final Drop game;

	Texture dropImage;
	Texture bucketImage;
	Sound dropSound;
	Music rainMusic;
	OrthographicCamera camera;
	Rectangle bucket;
	Array<Rectangle> raindrops;
	long lastDropTime;
	int dropsGathered;

	float playerSpeed = 200 * Gdx.graphics.getDeltaTime();

	public GameScreen(final Drop game) {
		this.game = game;

		dropImage = new Texture(Gdx.files.internal("droplet.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));

		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
		rainMusic.setLooping(true);

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		bucket = new Rectangle();
		bucket.x = 800 / 2 - 64 / 2;
		bucket.y = 20;
		bucket.width = 64;
		bucket.height = 64;

		spawnRaindrop();

	}

	private void spawnRaindrop() {
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, 800 - 64);
		raindrop.y = 480;
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);

		camera.update();

		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.font.draw(game.batch, "Drops Collected: " + dropsGathered, 0, 480);
		game.batch.draw(bucketImage, bucket.x, bucket.y, bucket.width, bucket.height);
		for (Rectangle raindrop : raindrops) {
			game.batch.draw(dropImage, raindrop.x, raindrop.y);
		}
		game.batch.end();

		playerInput();
		restrictWithinScreenBounds();
	}

	private void restrictWithinScreenBounds() {
		if (bucket.x < 0) {
			bucket.x = 0;
		} else if (bucket.x > 800 - 64) {
			bucket.x = 800 - 64;
		}
		if (bucket.y < 0) {
			bucket.y = 0;
		} else if (bucket.y > 480 - 64) {
			bucket.y = 480 - 64;
		}
	}

	private void playerInput() {
		float dx = 0, dy = 0;

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) dx -= playerSpeed;
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) dx += playerSpeed;
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) dy += playerSpeed;
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) dy -= playerSpeed;

		if (dx != 0 || dy != 0) {
			float length = (float) Math.sqrt(dx * dx + dy * dy);
			dx /= length;
			dy /= length;
		}

		bucket.x += (int) (dx * playerSpeed);
		bucket.y += (int) (dy * playerSpeed);
	}


	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		// start the playback of the background music
		// when the screen is shown
		rainMusic.play();
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		dropImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
	}
}
