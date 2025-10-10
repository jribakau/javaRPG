package com.mygdx.game.entity.components;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entity.Component;
import com.mygdx.game.entity.Entity;
import lombok.Getter;

/**
 * InventoryComponent - Manages an entity's inventory
 */
@Getter
public class InventoryComponent extends Component {
    private final Array<Entity> items;
    private final int maxCapacity;

    public InventoryComponent(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.items = new Array<>(maxCapacity);
    }

    public boolean addItem(Entity item) {
        if (items.size < maxCapacity) {
            items.add(item);
            return true;
        }
        return false;
    }

    public boolean removeItem(Entity item) {
        return items.removeValue(item, true);
    }

    public Entity removeItemAt(int index) {
        if (index >= 0 && index < items.size) {
            return items.removeIndex(index);
        }
        return null;
    }

    public Entity getItem(int index) {
        if (index >= 0 && index < items.size) {
            return items.get(index);
        }
        return null;
    }

    public boolean isFull() {
        return items.size >= maxCapacity;
    }

    public int getItemCount() {
        return items.size;
    }

    public void clear() {
        items.clear();
    }
}

