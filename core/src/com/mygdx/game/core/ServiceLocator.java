package com.mygdx.game.core;

import com.badlogic.gdx.Gdx;
import java.util.HashMap;
import java.util.Map;

/**
 * ServiceLocator - Central registry for all game services
 * This pattern allows easy access to services without tight coupling
 *
 * Usage:
 * - Register: ServiceLocator.provide(AudioManager.class, audioManager);
 * - Retrieve: AudioManager audio = ServiceLocator.get(AudioManager.class);
 * - Check: if (ServiceLocator.has(SaveManager.class)) { ... }
 */
public class ServiceLocator {
    private static final Map<Class<?>, Object> services = new HashMap<>();

    public static void initialize() {
        Gdx.app.log("ServiceLocator", "Initializing...");
        services.clear();
    }

    /**
     * Register a service with the locator
     * @param serviceClass The class/interface type of the service
     * @param service The service instance
     * @param <T> The service type
     */
    public static <T> void provide(Class<T> serviceClass, T service) {
        if (service == null) {
            throw new IllegalArgumentException("Cannot register null service for " + serviceClass.getName());
        }

        if (services.containsKey(serviceClass)) {
            Gdx.app.log("ServiceLocator", "Warning: Overwriting existing service: " + serviceClass.getSimpleName());
        }

        services.put(serviceClass, service);
        Gdx.app.log("ServiceLocator", "Service registered: " + serviceClass.getSimpleName());
    }

    /**
     * Retrieve a service from the locator
     * @param serviceClass The class/interface type of the service
     * @param <T> The service type
     * @return The service instance
     * @throws IllegalStateException if service is not registered
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(Class<T> serviceClass) {
        T service = (T) services.get(serviceClass);
        if (service == null) {
            throw new IllegalStateException(
                "Service not registered: " + serviceClass.getName() +
                ". Call ServiceLocator.provide() first."
            );
        }
        return service;
    }

    /**
     * Retrieve a service from the locator, or return null if not registered
     * @param serviceClass The class/interface type of the service
     * @param <T> The service type
     * @return The service instance or null
     */
    @SuppressWarnings("unchecked")
    public static <T> T getOrNull(Class<T> serviceClass) {
        return (T) services.get(serviceClass);
    }

    /**
     * Check if a service is registered
     * @param serviceClass The class/interface type to check
     * @return true if the service is registered
     */
    public static boolean has(Class<?> serviceClass) {
        return services.containsKey(serviceClass);
    }

    /**
     * Unregister a service
     * @param serviceClass The class/interface type of the service to remove
     */
    public static void remove(Class<?> serviceClass) {
        if (services.remove(serviceClass) != null) {
            Gdx.app.log("ServiceLocator", "Service unregistered: " + serviceClass.getSimpleName());
        }
    }

    // Convenience methods for GameContext (backward compatibility)

    /**
     * Register GameContext (convenience method)
     * @deprecated Use provide(GameContext.class, context) instead
     */
    @Deprecated
    public static void registerGameContext(GameContext context) {
        provide(GameContext.class, context);
    }

    /**
     * Get GameContext (convenience method)
     * @deprecated Use get(GameContext.class) instead
     */
    @Deprecated
    public static GameContext getGameContext() {
        return get(GameContext.class);
    }

    /**
     * Dispose all services and clear the registry
     */
    public static void dispose() {
        Gdx.app.log("ServiceLocator", "Disposing all services...");

        // Dispose GameContext if registered (it has its own dispose method)
        if (has(GameContext.class)) {
            GameContext context = get(GameContext.class);
            context.dispose();
        }

        // Clear all services
        services.clear();
        Gdx.app.log("ServiceLocator", "All services disposed");
    }

    /**
     * Get count of registered services (useful for debugging)
     */
    public static int getServiceCount() {
        return services.size();
    }
}