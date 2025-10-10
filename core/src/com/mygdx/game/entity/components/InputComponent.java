package com.mygdx.game.entity.components;

import com.mygdx.game.core.ServiceLocator;
import com.mygdx.game.entity.Component;
import com.mygdx.game.input.InputAction;
import com.mygdx.game.input.InputService;
import lombok.Getter;
import lombok.Setter;

/**
 * InputComponent - Handles input for player-controlled entities
 * Now uses InputService for device-agnostic input handling
 */
@Getter
@Setter
public class InputComponent extends Component {
    private boolean enabled;
    private InputService inputService;

    public InputComponent() {
        this.enabled = true;
    }

    @Override
    public void update(float delta) {
        if (!enabled) {
            stopMovement();
            return;
        }

        // Get input service on first update if not set
        if (inputService == null) {
            inputService = ServiceLocator.get(InputService.class);
        }

        // Apply movement from input service
        applyMovement();

        // Handle action inputs
        handleActions();
    }

    private void applyMovement() {
        MovementComponent movement = entity.getComponent(MovementComponent.class);
        if (movement != null) {
            float moveX = inputService.getMoveX();
            float moveY = inputService.getMoveY();

            if (moveX != 0 || moveY != 0) {
                movement.moveInDirection(moveX, moveY);
            } else {
                movement.stop();
            }
        }
    }

    private void handleActions() {
        // Check for attack input
        if (inputService.isActionJustPressed(InputAction.ATTACK)) {
            // TODO: Trigger attack action
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

    private void stopMovement() {
        MovementComponent movement = entity.getComponent(MovementComponent.class);
        if (movement != null) {
            movement.stop();
        }
    }
}
