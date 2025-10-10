package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.benchmark.EntityServiceBenchmarks;
import com.mygdx.game.core.ServiceLocator;

/**
 * BenchmarkScreen - Screen for running performance benchmarks
 * Press SPACE to run benchmarks
 */
public class BenchmarkScreen implements Screen {
    private final SpriteBatch batch;
    private final BitmapFont font;
    private boolean benchmarksRunning = false;
    private boolean benchmarksComplete = false;

    public BenchmarkScreen() {
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
    }

    @Override
    public void show() {
        Gdx.app.log("BenchmarkScreen", "Benchmark screen ready");
        Gdx.app.log("BenchmarkScreen", "Press SPACE to run EntityService benchmarks");
        Gdx.app.log("BenchmarkScreen", "Press ESC to return to game");
    }

    @Override
    public void render(float delta) {
        // Clear screen
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Check for input
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !benchmarksRunning) {
            runBenchmarks();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            // Return to game
            ServiceLocator.get(com.mygdx.game.core.GameContext.class)
                .setScreen(new GameScreen());
        }

        // Draw UI
        batch.begin();

        font.draw(batch, "BENCHMARK SCREEN", 10, 590);
        font.draw(batch, "Press SPACE to run EntityService benchmarks", 10, 560);
        font.draw(batch, "Press ESC to return to game", 10, 540);

        if (benchmarksRunning) {
            font.draw(batch, "Running benchmarks... check console", 10, 500);
        } else if (benchmarksComplete) {
            font.draw(batch, "Benchmarks complete! Check console for results.", 10, 500);
            font.draw(batch, "Press SPACE to run again", 10, 480);
        } else {
            font.draw(batch, "Ready to benchmark", 10, 500);
        }

        batch.end();
    }

    private void runBenchmarks() {
        benchmarksRunning = true;
        benchmarksComplete = false;

        // Run benchmarks in a separate thread to avoid blocking
        new Thread(() -> {
            try {
                EntityServiceBenchmarks.runAll();
                benchmarksComplete = true;
            } catch (Exception e) {
                Gdx.app.error("BenchmarkScreen", "Benchmark failed", e);
            } finally {
                benchmarksRunning = false;
            }
        }).start();
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

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}

