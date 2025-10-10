package com.mygdx.game.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

/**
 * SystemManager - Manages and coordinates all game systems
 * Handles system registration, execution order, and lifecycle
 */
public class SystemManager {
    private final Array<GameSystem> systems;
    private boolean systemsNeedSorting;

    public SystemManager() {
        this.systems = new Array<>();
        this.systemsNeedSorting = false;
    }

    /**
     * Add a system to the manager
     */
    public void addSystem(GameSystem system) {
        systems.add(system);
        systemsNeedSorting = true;
        system.initialize();
        Gdx.app.log("SystemManager", "System added: " + system.getClass().getSimpleName());
    }

    /**
     * Remove a system from the manager
     */
    public void removeSystem(GameSystem system) {
        if (systems.removeValue(system, true)) {
            system.dispose();
            Gdx.app.log("SystemManager", "System removed: " + system.getClass().getSimpleName());
        }
    }

    /**
     * Get a system by type
     */
    @SuppressWarnings("unchecked")
    public <T extends GameSystem> T getSystem(Class<T> systemClass) {
        for (GameSystem system : systems) {
            if (systemClass.isInstance(system)) {
                return (T) system;
            }
        }
        return null;
    }

    /**
     * Update all systems in priority order
     */
    public void update(float delta) {
        // Sort systems by priority if needed
        if (systemsNeedSorting) {
            sortSystems();
            systemsNeedSorting = false;
        }

        // Update all enabled systems
        for (GameSystem system : systems) {
            if (system.isEnabled()) {
                system.update(delta);
            }
        }
    }

    /**
     * Sort systems by priority (lower priority = earlier execution)
     */
    private void sortSystems() {
        systems.sort((s1, s2) -> Integer.compare(s1.getPriority(), s2.getPriority()));
        Gdx.app.log("SystemManager", "Systems sorted by priority");
    }

    /**
     * Dispose all systems
     */
    public void dispose() {
        Gdx.app.log("SystemManager", "Disposing " + systems.size + " systems...");
        for (GameSystem system : systems) {
            system.dispose();
        }
        systems.clear();
    }

    /**
     * Get number of registered systems
     */
    public int getSystemCount() {
        return systems.size;
    }
}

