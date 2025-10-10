package com.mygdx.game.entity.components;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.entity.Component;
import lombok.Getter;
import lombok.Setter;

/**
 * RenderComponent - Handles entity rendering
 */
@Getter
@Setter
public class RenderComponent extends Component {
    private TextureRegion textureRegion;
    private boolean visible;
    private float offsetX;
    private float offsetY;

    public RenderComponent(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
        this.visible = true;
        this.offsetX = 0;
        this.offsetY = 0;
    }

    public RenderComponent() {
        this.visible = true;
        this.offsetX = 0;
        this.offsetY = 0;
    }
}

