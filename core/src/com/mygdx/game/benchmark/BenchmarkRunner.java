package com.mygdx.game.benchmark;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import lombok.Getter;

/**
 * BenchmarkRunner - Framework for running and comparing benchmarks
 *
 * Usage:
 * BenchmarkRunner runner = new BenchmarkRunner();
 * BenchmarkResult result = runner.run("My Test", 100, () -> {
 *     // Code to benchmark
 * });
 */
@Getter
public class BenchmarkRunner {
    private static final int WARMUP_ITERATIONS = 10;
    /**
     * -- GETTER --
     *  Get all benchmark results
     */
    private final Array<BenchmarkResult> results;

    public BenchmarkRunner() {
        this.results = new Array<>();
    }

    /**
     * Run a benchmark with automatic warmup
     *
     * @param name Benchmark name
     * @param iterations Number of times to run
     * @param benchmark The code to benchmark
     * @return BenchmarkResult with timing and stats
     */
    public BenchmarkResult run(String name, int iterations, Benchmark benchmark) {
        Gdx.app.log("Benchmark", "Running: " + name);

        // Warmup phase (JIT compilation, cache warmup)
        Gdx.app.log("Benchmark", "  Warming up (" + WARMUP_ITERATIONS + " iterations)...");
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            try {
                benchmark.run();
            } catch (Exception e) {
                Gdx.app.error("Benchmark", "Warmup failed", e);
            }
        }

        // Force GC before benchmark
        System.gc();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // Ignore
        }

        long memoryBefore = getUsedMemory();

        // Actual benchmark
        Gdx.app.log("Benchmark", "  Running benchmark (" + iterations + " iterations)...");
        Array<Long> individualTimes = new Array<>(iterations);
        long totalTime = 0;

        for (int i = 0; i < iterations; i++) {
            long startTime = TimeUtils.nanoTime();

            try {
                benchmark.run();
            } catch (Exception e) {
                Gdx.app.error("Benchmark", "Benchmark failed at iteration " + i, e);
            }

            long endTime = TimeUtils.nanoTime();
            long elapsed = (endTime - startTime) / 1_000_000; // Convert to milliseconds
            individualTimes.add(elapsed);
            totalTime += elapsed;
        }

        long memoryAfter = getUsedMemory();
        long memoryUsed = Math.max(0, memoryAfter - memoryBefore);

        BenchmarkResult result = new BenchmarkResult(name, iterations, totalTime, individualTimes, memoryUsed);
        results.add(result);

        Gdx.app.log("Benchmark", "  " + result.toString());

        return result;
    }

    /**
     * Run a quick benchmark (fewer iterations, good for fast tests)
     */
    public BenchmarkResult runQuick(String name, Benchmark benchmark) {
        return run(name, 50, benchmark);
    }

    /**
     * Run a standard benchmark (medium iterations)
     */
    public BenchmarkResult runStandard(String name, Benchmark benchmark) {
        return run(name, 100, benchmark);
    }

    /**
     * Run a thorough benchmark (many iterations for accurate results)
     */
    public BenchmarkResult runThorough(String name, Benchmark benchmark) {
        return run(name, 500, benchmark);
    }

    /**
     * Compare two benchmarks and print the speedup
     */
    public void compare(BenchmarkResult baseline, BenchmarkResult optimized) {
        double speedup = optimized.getSpeedupVs(baseline);
        double percentFaster = (speedup - 1.0) * 100.0;

        Gdx.app.log("Benchmark", "");
        Gdx.app.log("Benchmark", "=== COMPARISON ===");
        Gdx.app.log("Benchmark", "Baseline:  " + baseline.getBenchmarkName());
        Gdx.app.log("Benchmark", "           " + String.format("%.3fms avg", baseline.getAverageTimeMs()));
        Gdx.app.log("Benchmark", "Optimized: " + optimized.getBenchmarkName());
        Gdx.app.log("Benchmark", "           " + String.format("%.3fms avg", optimized.getAverageTimeMs()));
        Gdx.app.log("Benchmark", "");

        // Handle cases where timing is too fast to measure
        if (optimized.getAverageTimeMs() <= 0.0 && baseline.getAverageTimeMs() > 0.0) {
            Gdx.app.log("Benchmark", "⚡ Optimized is TOO FAST TO MEASURE! (baseline: " +
                String.format("%.3fms", baseline.getAverageTimeMs()) + ")");
        } else if (baseline.getAverageTimeMs() <= 0.0 && optimized.getAverageTimeMs() <= 0.0) {
            Gdx.app.log("Benchmark", "⚠ Both too fast to measure accurately (< 1ms)");
        } else if (speedup > 1.0) {
            Gdx.app.log("Benchmark", String.format("⚡ Optimized is %.2fx FASTER (%.1f%% improvement)",
                speedup, percentFaster));
        } else if (speedup < 1.0 && speedup > 0) {
            Gdx.app.log("Benchmark", String.format("⚠ Optimized is %.2fx SLOWER", 1.0 / speedup));
        } else {
            Gdx.app.log("Benchmark", "No measurable difference");
        }

        // Memory comparison
        long memoryDiff = baseline.getMemoryUsedBytes() - optimized.getMemoryUsedBytes();
        if (Math.abs(memoryDiff) > 1024 * 1024) { // More than 1MB difference
            if (memoryDiff > 0) {
                Gdx.app.log("Benchmark", String.format("💾 Memory saved: %.2fMB",
                    memoryDiff / (1024.0 * 1024.0)));
            } else {
                Gdx.app.log("Benchmark", String.format("💾 Memory used: +%.2fMB (worth it for speed!)",
                    Math.abs(memoryDiff) / (1024.0 * 1024.0)));
            }
        }

        Gdx.app.log("Benchmark", "==================");
        Gdx.app.log("Benchmark", "");
    }

    /**
     * Print all results
     */
    public void printAllResults() {
        Gdx.app.log("Benchmark", "");
        Gdx.app.log("Benchmark", "=== ALL RESULTS ===");
        for (BenchmarkResult result : results) {
            Gdx.app.log("Benchmark", result.toString());
        }
        Gdx.app.log("Benchmark", "===================");
        Gdx.app.log("Benchmark", "");
    }

    /**
     * Clear all results
     */
    public void clear() {
        results.clear();
    }

    /**
     * Get current memory usage
     */
    private long getUsedMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
}