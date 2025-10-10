package com.mygdx.game.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.EntityService;
import com.mygdx.game.entity.Player;
import com.mygdx.game.entity.components.PositionComponent;
import com.mygdx.game.entity.components.StatsComponent;

/**
 * CombatSystem - Handles combat interactions between entities
 * Processes attacks, damage calculation, and combat state
 */
public class CombatSystem extends GameSystem {
    private static final float ATTACK_RANGE = 50f; // Attack range in pixels
    private static final float ATTACK_COOLDOWN = 1.0f; // Seconds between attacks

    private final Array<CombatAction> pendingActions;

    public CombatSystem(EntityService entityService) {
        super(entityService);
        this.pendingActions = new Array<>();
    }

    @Override
    public void update(float delta) {
        if (!enabled) return;

        // Process pending combat actions
        for (CombatAction action : pendingActions) {
            processCombatAction(action);
        }
        pendingActions.clear();

        // Update cooldowns for entities (if you add cooldown component)
        // This is a simplified version
    }

    /**
     * Queue an attack from attacker to target
     */
    public void queueAttack(Entity attacker, Entity target) {
        if (canAttack(attacker, target)) {
            pendingActions.add(new CombatAction(attacker, target, CombatActionType.ATTACK));
        }
    }

    /**
     * Immediately perform an attack (bypasses queue)
     */
    public void performAttack(Entity attacker, Entity target) {
        if (!canAttack(attacker, target)) {
            return;
        }

        StatsComponent attackerStats = attacker.getComponent(StatsComponent.class);
        StatsComponent targetStats = target.getComponent(StatsComponent.class);

        if (attackerStats == null || targetStats == null) {
            return;
        }

        // Calculate damage
        int damage = calculateDamage(attackerStats, targetStats);

        // Apply damage
        applyDamage(target, damage);

        // Log combat action
        String attackerName = getEntityName(attacker);
        String targetName = getEntityName(target);
        Gdx.app.log("CombatSystem",
            attackerName + " attacks " + targetName + " for " + damage + " damage!");

        // Check if target died
        if (targetStats.getHealth() <= 0) {
            onEntityDeath(target, attacker);
        }
    }

    /**
     * Check if attacker can attack target
     */
    public boolean canAttack(Entity attacker, Entity target) {
        if (attacker == null || target == null) return false;
        if (attacker == target) return false; // Can't attack self

        // Check if both have required components
        StatsComponent attackerStats = attacker.getComponent(StatsComponent.class);
        StatsComponent targetStats = target.getComponent(StatsComponent.class);
        if (attackerStats == null || targetStats == null) return false;

        // Check if target is already dead
        if (targetStats.getHealth() <= 0) return false;

        // Check range
        if (!isInRange(attacker, target, ATTACK_RANGE)) return false;

        return true;
    }

    /**
     * Check if two entities are within range of each other
     * OPTIMIZED: Uses squared distance to avoid sqrt() call and Vector2 allocations
     */
    public boolean isInRange(Entity entity1, Entity entity2, float range) {
        PositionComponent pos1 = entity1.getComponent(PositionComponent.class);
        PositionComponent pos2 = entity2.getComponent(PositionComponent.class);

        if (pos1 == null || pos2 == null) return false;

        // OPTIMIZATION: Calculate centers inline (no Vector2 allocation)
        float center1X = pos1.getX() + pos1.getWidth() / 2;
        float center1Y = pos1.getY() + pos1.getHeight() / 2;
        float center2X = pos2.getX() + pos2.getWidth() / 2;
        float center2Y = pos2.getY() + pos2.getHeight() / 2;

        // OPTIMIZATION: Compare squared distances to avoid sqrt()
        float dx = center2X - center1X;
        float dy = center2Y - center1Y;
        float distSquared = dx * dx + dy * dy;
        float rangeSquared = range * range;

        return distSquared <= rangeSquared;
    }

    /**
     * Calculate damage based on attacker and defender stats
     */
    protected int calculateDamage(StatsComponent attacker, StatsComponent defender) {
        int baseDamage = attacker.getAttack();
        int defense = defender.getDefense();

        // Simple formula: damage = attack - defense (minimum 1)
        int damage = Math.max(1, baseDamage - defense);

        // Add some randomness (±20%)
        float randomFactor = 0.8f + (float) Math.random() * 0.4f;
        damage = (int) (damage * randomFactor);

        return Math.max(1, damage);
    }

    /**
     * Apply damage to an entity
     */
    protected void applyDamage(Entity target, int damage) {
        StatsComponent stats = target.getComponent(StatsComponent.class);
        if (stats != null) {
            int newHealth = Math.max(0, stats.getHealth() - damage);
            stats.setHealth(newHealth);
        }
    }

    /**
     * Apply healing to an entity
     */
    public void applyHealing(Entity target, int healing) {
        StatsComponent stats = target.getComponent(StatsComponent.class);
        if (stats != null) {
            int newHealth = Math.min(stats.getMaxHealth(), stats.getHealth() + healing);
            stats.setHealth(newHealth);

            Gdx.app.log("CombatSystem",
                getEntityName(target) + " healed for " + healing + " HP");
        }
    }

    /**
     * Process a combat action
     */
    private void processCombatAction(CombatAction action) {
        switch (action.type) {
            case ATTACK:
                performAttack(action.attacker, action.target);
                break;
            case HEAL:
                // Implement healing logic
                break;
            case SPELL:
                // Implement spell logic
                break;
        }
    }

    /**
     * Handle entity death
     */
    protected void onEntityDeath(Entity entity, Entity killer) {
        String entityName = getEntityName(entity);
        String killerName = getEntityName(killer);

        Gdx.app.log("CombatSystem", entityName + " was killed by " + killerName);

        // Award experience to killer if it's a player
        if (killer instanceof Player) {
            StatsComponent killerStats = killer.getComponent(StatsComponent.class);
            StatsComponent deadStats = entity.getComponent(StatsComponent.class);

            if (killerStats != null && deadStats != null) {
                int expGained = deadStats.getLevel() * 10; // Simple XP formula
                int newExp = killerStats.getExperience() + expGained;
                killerStats.setExperience(newExp);

                Gdx.app.log("CombatSystem", killerName + " gained " + expGained + " XP");
            }
        }

        // Remove dead entity (or mark for removal)
        entity.setActive(false);
        entityService.removeEntity(entity);
    }

    /**
     * Get entity name for logging
     */
    private String getEntityName(Entity entity) {
        if (entity instanceof Player) {
            return ((Player) entity).getPlayerComponent().getName();
        }
        return "Entity #" + entity.getId();
    }

    @Override
    public int getPriority() {
        return 30; // Combat happens after collision detection
    }

    /**
     * Combat action types
     */
    private enum CombatActionType {
        ATTACK,
        HEAL,
        SPELL
    }

    /**
     * Combat action data
     */
    private static class CombatAction {
        final Entity attacker;
        final Entity target;
        final CombatActionType type;

        CombatAction(Entity attacker, Entity target, CombatActionType type) {
            this.attacker = attacker;
            this.target = target;
            this.type = type;
        }
    }
}
