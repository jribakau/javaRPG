package com.mygdx.game.benchmark;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.EntityService;
import com.mygdx.game.entity.components.PositionComponent;
import com.mygdx.game.entity.components.RenderComponent;

/**
 * EntityServiceBenchmarks - Specific benchmarks for EntityService optimizations
 */
public class EntityServiceBenchmarks {

    /**
     * Run all EntityService benchmarks and print results
     */
    public static void runAll() {
        Gdx.app.log("EntityServiceBenchmarks", "");
        Gdx.app.log("EntityServiceBenchmarks", "╔═══════════════════════════════════════════════════╗");
        Gdx.app.log("EntityServiceBenchmarks", "║   ENTITY SERVICE PERFORMANCE BENCHMARKS          ║");
        Gdx.app.log("EntityServiceBenchmarks", "║   (STRESS TEST - INTENSIVE MODE)                 ║");
        Gdx.app.log("EntityServiceBenchmarks", "╚═══════════════════════════════════════════════════╝");
        Gdx.app.log("EntityServiceBenchmarks", "");

        // Run each benchmark suite
        benchmarkComponentQueries();
        benchmarkEntityPooling();
        benchmarkSpatialPartitioning();
        benchmarkMassiveStressTest();

        Gdx.app.log("EntityServiceBenchmarks", "");
        Gdx.app.log("EntityServiceBenchmarks", "All benchmarks complete!");
    }

    /**
     * Test 1: Component Query Caching
     * BEEFED UP: 5000 entities, 100 queries per iteration
     */
    public static void benchmarkComponentQueries() {
        Gdx.app.log("EntityServiceBenchmarks", "");
        Gdx.app.log("EntityServiceBenchmarks", "═══ TEST 1: COMPONENT QUERY CACHING ═══");

        BenchmarkRunner runner = new BenchmarkRunner();
        final int ENTITY_COUNT = 5000; // Was 500
        final int QUERIES_PER_ITERATION = 100; // Was 10

        // Setup: Create service and populate with entities
        EntityService serviceNoCaching = new EntityService();
        EntityService serviceCached = new EntityService();

        // Populate both services with same entities
        for (int i = 0; i < ENTITY_COUNT; i++) {
            Entity e1 = new Entity();
            e1.addComponent(new PositionComponent(i * 10, i * 10));
            e1.addComponent(new RenderComponent());
            serviceNoCaching.addEntity(e1);

            Entity e2 = new Entity();
            e2.addComponent(new PositionComponent(i * 10, i * 10));
            e2.addComponent(new RenderComponent());
            serviceCached.addEntity(e2);
        }

        // Process entities (trigger cache)
        serviceNoCaching.update(0);
        serviceCached.update(0);

        Gdx.app.log("EntityServiceBenchmarks", "Setup: " + ENTITY_COUNT + " entities, " + QUERIES_PER_ITERATION + " queries per iteration");

        // Benchmark: Without caching (simulate by invalidating cache each time)
        BenchmarkResult noCaching = runner.runStandard("Without Caching", () -> {
            serviceNoCaching.invalidateCache(); // Force rebuild every time
            for (int i = 0; i < QUERIES_PER_ITERATION; i++) {
                Array<Entity> entities = serviceNoCaching.getEntitiesWithComponent(PositionComponent.class);
                // Actually iterate to force work
                int count = 0;
                for (Entity e : entities) {
                    if (e.isActive()) count++;
                }
            }
        });

        // Benchmark: With caching (normal operation)
        BenchmarkResult withCaching = runner.runStandard("With Caching", () -> {
            for (int i = 0; i < QUERIES_PER_ITERATION; i++) {
                Array<Entity> entities = serviceCached.getEntitiesWithComponent(PositionComponent.class);
                // Actually iterate to force work
                int count = 0;
                for (Entity e : entities) {
                    if (e.isActive()) count++;
                }
            }
        });

        runner.compare(noCaching, withCaching);
    }

    /**
     * Test 2: Entity Pooling
     * BEEFED UP: 500 entities per iteration, more iterations
     */
    public static void benchmarkEntityPooling() {
        Gdx.app.log("EntityServiceBenchmarks", "");
        Gdx.app.log("EntityServiceBenchmarks", "═══ TEST 2: ENTITY POOLING ═══");

        BenchmarkRunner runner = new BenchmarkRunner();
        final int ENTITIES_PER_ITERATION = 500; // Was 100

        EntityService serviceNoPooling = new EntityService(false);
        EntityService serviceWithPooling = new EntityService(true);

        Gdx.app.log("EntityServiceBenchmarks", "Setup: Creating and destroying " + ENTITIES_PER_ITERATION + " entities per iteration");

        // Benchmark: Without pooling
        BenchmarkResult noPooling = runner.run("Without Pooling", 200, () -> { // More iterations
            Array<Entity> tempEntities = new Array<>();

            // Create entities
            for (int i = 0; i < ENTITIES_PER_ITERATION; i++) {
                Entity e = new Entity();
                e.addComponent(new PositionComponent(i, i));
                serviceNoPooling.addEntity(e);
                tempEntities.add(e);
            }

            serviceNoPooling.update(0);

            // Remove entities
            for (Entity e : tempEntities) {
                serviceNoPooling.removeEntity(e);
            }

            serviceNoPooling.update(0);
        });

        // Benchmark: With pooling
        BenchmarkResult withPooling = runner.run("With Pooling", 200, () -> {
            Array<Entity> tempEntities = new Array<>();

            // Create entities from pool
            for (int i = 0; i < ENTITIES_PER_ITERATION; i++) {
                Entity e = serviceWithPooling.createEntity();
                e.addComponent(new PositionComponent(i, i));
                serviceWithPooling.addEntity(e);
                tempEntities.add(e);
            }

            serviceWithPooling.update(0);

            // Remove entities (returns to pool)
            for (Entity e : tempEntities) {
                serviceWithPooling.removeEntity(e);
            }

            serviceWithPooling.update(0);
        });

        runner.compare(noPooling, withPooling);
    }

    /**
     * Test 3: Spatial Partitioning
     * BEEFED UP: 10,000 entities, 50 queries per iteration
     */
    public static void benchmarkSpatialPartitioning() {
        Gdx.app.log("EntityServiceBenchmarks", "");
        Gdx.app.log("EntityServiceBenchmarks", "═══ TEST 3: SPATIAL PARTITIONING ═══");

        BenchmarkRunner runner = new BenchmarkRunner();
        final int ENTITY_COUNT = 10000; // Was 1000
        final int QUERIES_PER_ITERATION = 50; // Was 20
        final float QUERY_RADIUS = 100f;

        EntityService serviceNoSpatial = new EntityService(false, false);
        EntityService serviceWithSpatial = new EntityService(false, true);

        // Populate both services with entities spread across a large area
        for (int i = 0; i < ENTITY_COUNT; i++) {
            float x = (i % 100) * 100f; // Spread across larger area
            float y = (i / 100) * 100f;

            Entity e1 = new Entity();
            e1.addComponent(new PositionComponent(x, y));
            serviceNoSpatial.addEntity(e1);

            Entity e2 = new Entity();
            e2.addComponent(new PositionComponent(x, y));
            serviceWithSpatial.addEntity(e2);
        }

        serviceNoSpatial.update(0);
        serviceWithSpatial.update(0);

        Gdx.app.log("EntityServiceBenchmarks", "Setup: " + ENTITY_COUNT + " entities, " + QUERIES_PER_ITERATION + " proximity queries per iteration");
        Gdx.app.log("EntityServiceBenchmarks", "Query radius: " + QUERY_RADIUS + "px");

        // Benchmark: Brute force proximity search
        BenchmarkResult bruteForce = runner.run("Brute Force Search", 200, () -> { // More iterations
            for (int i = 0; i < QUERIES_PER_ITERATION; i++) {
                float queryX = (i * 200f) % 10000f;
                float queryY = (i * 150f) % 10000f;

                Array<Entity> nearby = serviceNoSpatial.getEntitiesNear(queryX, queryY, QUERY_RADIUS);
                // Actually process results
                int count = 0;
                for (Entity e : nearby) {
                    if (e.isActive()) count++;
                }
            }
        });

        // Benchmark: Spatial grid search
        BenchmarkResult spatialGrid = runner.run("Spatial Grid Search", 200, () -> {
            for (int i = 0; i < QUERIES_PER_ITERATION; i++) {
                float queryX = (i * 200f) % 10000f;
                float queryY = (i * 150f) % 10000f;

                Array<Entity> nearby = serviceWithSpatial.getEntitiesNear(queryX, queryY, QUERY_RADIUS);
                // Actually process results
                int count = 0;
                for (Entity e : nearby) {
                    if (e.isActive()) count++;
                }
            }
        });

        runner.compare(bruteForce, spatialGrid);

        // Also test rectangular bounds queries
        Gdx.app.log("EntityServiceBenchmarks", "");
        Gdx.app.log("EntityServiceBenchmarks", "--- Rectangular Bounds Queries ---");

        Rectangle queryBounds = new Rectangle(500, 500, 800, 600);

        BenchmarkResult boundsNormal = runner.run("Bounds (No Spatial)", 100, () -> {
            for (int i = 0; i < 20; i++) { // Multiple queries per iteration
                Array<Entity> inBounds = serviceNoSpatial.getEntitiesInBounds(queryBounds);
                int count = inBounds.size;
            }
        });

        BenchmarkResult boundsSpatial = runner.run("Bounds (With Spatial)", 100, () -> {
            for (int i = 0; i < 20; i++) { // Multiple queries per iteration
                Array<Entity> inBounds = serviceWithSpatial.getEntitiesInBounds(queryBounds);
                int count = inBounds.size;
            }
        });

        runner.compare(boundsNormal, boundsSpatial);
    }

    /**
     * NEW: Massive stress test - Combined scenario
     */
    public static void benchmarkMassiveStressTest() {
        Gdx.app.log("EntityServiceBenchmarks", "");
        Gdx.app.log("EntityServiceBenchmarks", "═══ MASSIVE STRESS TEST ═══");
        Gdx.app.log("EntityServiceBenchmarks", "Simulating extreme game scenario");

        BenchmarkRunner runner = new BenchmarkRunner();
        final int ENTITY_COUNT = 2000;

        EntityService baseline = new EntityService(false, false);
        EntityService optimized = new EntityService(true, true);

        // Pre-populate with entities
        for (int i = 0; i < ENTITY_COUNT; i++) {
            Entity e1 = new Entity();
            e1.addComponent(new PositionComponent(i * 50, i * 50));
            e1.addComponent(new RenderComponent());
            baseline.addEntity(e1);

            Entity e2 = new Entity();
            e2.addComponent(new PositionComponent(i * 50, i * 50));
            e2.addComponent(new RenderComponent());
            optimized.addEntity(e2);
        }

        baseline.update(0);
        optimized.update(0);

        // Benchmark baseline
        BenchmarkResult baselineResult = runner.run("Baseline (No Optimizations)", 300, () -> {
            // Multiple component queries (typical game loop)
            for (int q = 0; q < 20; q++) {
                Array<Entity> positions = baseline.getEntitiesWithComponent(PositionComponent.class);
                Array<Entity> renders = baseline.getEntitiesWithComponent(RenderComponent.class);

                // Process all entities
                for (Entity e : positions) {
                    e.isActive();
                }
            }

            // Multiple proximity searches (collision detection)
            for (int i = 0; i < 10; i++) {
                Array<Entity> nearby = baseline.getEntitiesNear(i * 1000, i * 1000, 150);
                for (Entity e : nearby) {
                    e.isActive();
                }
            }
        });

        // Benchmark optimized
        BenchmarkResult optimizedResult = runner.run("Optimized (All Features)", 300, () -> {
            // Multiple component queries (cached!)
            for (int q = 0; q < 20; q++) {
                Array<Entity> positions = optimized.getEntitiesWithComponent(PositionComponent.class);
                Array<Entity> renders = optimized.getEntitiesWithComponent(RenderComponent.class);

                // Process all entities
                for (Entity e : positions) {
                    e.isActive();
                }
            }

            // Multiple proximity searches (spatial grid!)
            for (int i = 0; i < 10; i++) {
                Array<Entity> nearby = optimized.getEntitiesNear(i * 1000, i * 1000, 150);
                for (Entity e : nearby) {
                    e.isActive();
                }
            }
        });

        runner.compare(baselineResult, optimizedResult);
    }
}

