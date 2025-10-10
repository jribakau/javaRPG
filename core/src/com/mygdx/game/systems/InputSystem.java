package com.mygdx.game.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.core.ServiceLocator;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.EntityService;
import com.mygdx.game.entity.components.InputComponent;
import com.mygdx.game.entity.components.MovementComponent;
import com.mygdx.game.input.InputAction;
import com.mygdx.game.input.InputService;

/**
 * InputSystem - Processes input for entities with InputComponent
 * Translates input actions into entity movement and actions
 */
public class InputSystem extends GameSystem {
    private final InputService inputService;

    public InputSystem(EntityService entityService) {
        super(entityService);
        this.inputService = ServiceLocator.get(InputService.class);
    }

    @Override
    public void update(float delta) {
        if (!enabled) return;

        // Get all entities with input components
        Array<Entity> inputEntities = entityService.getEntitiesWithComponent(InputComponent.class);

        for (Entity entity : inputEntities) {
            InputComponent input = entity.getComponent(InputComponent.class);
            MovementComponent movement = entity.getComponent(MovementComponent.class);

            if (!input.isEnabled() || movement == null) {
                continue;
            }

            // Apply movement from input
            float moveX = inputService.getMoveX();
            float moveY = inputService.getMoveY();

            if (moveX != 0 || moveY != 0) {
                movement.moveInDirection(moveX, moveY);
            } else {
                movement.stop();
            }

            // Handle action inputs (attack, interact, etc.)
            if (inputService.isActionJustPressed(InputAction.ATTACK)) {
                Gdx.app.log("InputSystem", "Attack action triggered");
                // TODO: Trigger attack through CombatSystem
            }

            if (inputService.isActionJustPressed(InputAction.INTERACT)) {
                Gdx.app.log("InputSystem", "Interact action triggered");
                // TODO: Trigger interaction
            }
        }
    }

    @Override
    public int getPriority() {
        return 5; // Input processing happens early, before movement
    }
}
