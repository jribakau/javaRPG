package com.mygdx.game.entity.components;

import com.mygdx.game.entity.Component;
import lombok.Getter;
import lombok.Setter;

/**
 * InputComponent - Marks an entity as input-controlled
 * Input processing logic is handled by InputSystem
 */
@Getter
@Setter
public class InputComponent extends Component {
    private boolean enabled;

    public InputComponent() {
        this.enabled = true;
    }

    @Override
    public void update(float delta) {
        // Input processing is now handled by InputSystem
        // This component is just a marker
    }
}
