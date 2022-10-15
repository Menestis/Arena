package fr.ariloxe.arena.utils.inventory;

import org.bukkit.inventory.ItemStack;

/**
 * @author Ariloxe
 */
public class PlayerInventory {

    private ItemStack[] contents;
    private boolean changed = false;

    public PlayerInventory(ItemStack[] contents) {
        this.contents = contents;
    }

    public void setInventory(ItemStack[] contents, boolean newer) {
        this.contents = contents;
        if(newer)
            changed = true;
    }

    public ItemStack[] getContents() {
        return contents;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public boolean isChanged() {
        return changed;
    }
}
