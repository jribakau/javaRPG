package com.mygdx.game.benchmark;

/**
 * Benchmark - Functional interface for benchmark operations
 */
@FunctionalInterface
public interface Benchmark {
    /**
     * Execute the benchmark operation
     * @throws Exception if benchmark fails
     */
    void run() throws Exception;
}

