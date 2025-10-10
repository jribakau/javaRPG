package com.mygdx.game.systems;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.core.ServiceLocator;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.EntityService;
import com.mygdx.game.entity.components.InputComponent;
import com.mygdx.game.entity.components.MovementComponent;
import com.mygdx.game.input.InputAction;
import com.mygdx.game.input.InputService;

/**
 * InputSystem - Processes input for all entities with InputComponent
 * Translates player input into entity movement and actions
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

        Array<Entity> inputEntities = entityService.getEntitiesWithComponent(InputComponent.class);

        for (Entity entity : inputEntities) {
            InputComponent input = entity.getComponent(InputComponent.class);

            if (!input.isEnabled()) {
                stopMovement(entity);
                continue;
            }

            // Apply movement from input
            applyMovement(entity);

            // Handle action inputs
            handleActions(entity);
        }
    }

    private void applyMovement(Entity entity) {
        MovementComponent movement = entity.getComponent(MovementComponent.class);
        if (movement != null && movement.isCanMove()) {
            float moveX = inputService.getMoveX();
            float moveY = inputService.getMoveY();

            if (moveX != 0 || moveY != 0) {
                movement.moveInDirection(moveX, moveY);
            } else {
                movement.stop();
            }
        }
    }

    private void handleActions(Entity entity) {
        // Check for attack input
        if (inputService.isActionJustPressed(InputAction.ATTACK)) {
            // TODO: Trigger attack action (could emit event or set component flag)
        }

        // Check for interact input
        if (inputService.isActionJustPressed(InputAction.INTERACT)) {
            // TODO: Trigger interact action
        }

        // Check for use item input
        if (inputService.isActionJustPressed(InputAction.USE_ITEM)) {
            // TODO: Trigger use item action
        }
    }

    private void stopMovement(Entity entity) {
        MovementComponent movement = entity.getComponent(MovementComponent.class);
        if (movement != null) {
            movement.stop();
        }
    }

    @Override
    public int getPriority() {
        return 5; // Input processing happens before movement
    }
}

