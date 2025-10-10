package com.mygdx.game.benchmark;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import lombok.Getter;

/**
 * BenchmarkResult - Stores results from a single benchmark run
 */
@Getter
public class BenchmarkResult {
    private final String benchmarkName;
    private final int iterations;
    private final long totalTimeMs;
    private final double averageTimeMs;
    private final double operationsPerSecond;
    private final Array<Long> individualTimes;
    private final long memoryUsedBytes;

    public BenchmarkResult(String benchmarkName, int iterations, long totalTimeMs,
                           Array<Long> individualTimes, long memoryUsedBytes) {
        this.benchmarkName = benchmarkName;
        this.iterations = iterations;
        this.totalTimeMs = totalTimeMs;
        this.averageTimeMs = (double) totalTimeMs / iterations;
        this.operationsPerSecond = iterations > 0 ? (1000.0 / averageTimeMs) : 0;
        this.individualTimes = individualTimes;
        this.memoryUsedBytes = memoryUsedBytes;
    }

    /**
     * Get min execution time
     */
    public long getMinTimeMs() {
        if (individualTimes.size == 0) return 0;
        long min = Long.MAX_VALUE;
        for (long time : individualTimes) {
            if (time < min) min = time;
        }
        return min;
    }

    /**
     * Get max execution time
     */
    public long getMaxTimeMs() {
        if (individualTimes.size == 0) return 0;
        long max = 0;
        for (long time : individualTimes) {
            if (time > max) max = time;
        }
        return max;
    }

    /**
     * Get standard deviation
     */
    public double getStdDeviation() {
        if (individualTimes.size <= 1) return 0;

        double sum = 0;
        for (long time : individualTimes) {
            double diff = time - averageTimeMs;
            sum += diff * diff;
        }
        return Math.sqrt(sum / individualTimes.size);
    }

    /**
     * Format as readable string
     */
    @Override
    public String toString() {
        return String.format(
                "%s: %.3fms avg (min: %dms, max: %dms, stddev: %.3fms) - %.0f ops/sec - Memory: %.2fMB",
                benchmarkName, averageTimeMs, getMinTimeMs(), getMaxTimeMs(),
                getStdDeviation(), operationsPerSecond, memoryUsedBytes / (1024.0 * 1024.0)
        );
    }

    /**
     * Compare this result to another (for speedup calculations)
     */
    public double getSpeedupVs(BenchmarkResult other) {
        if (this.averageTimeMs == 0) return 0;
        return other.averageTimeMs / this.averageTimeMs;
    }
}