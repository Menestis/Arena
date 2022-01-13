package com.pyralia.arena.manager;

import com.pyralia.arena.ArenaAPI;
import com.pyralia.arena.uis.kits.SelectMainInventory;
import com.pyralia.arena.uis.perks.PerksMainInventory;

/**
 * @author Ariloxe
 */
public class GuiManager {

    private final SelectMainInventory selectMainInventory;
    private final PerksMainInventory perksMainInventory;

    public GuiManager(ArenaAPI instance){
        this.selectMainInventory = new SelectMainInventory(instance);
        this.perksMainInventory = new PerksMainInventory(instance);
    }

    public SelectMainInventory getSelectKitInventory() {
        return selectMainInventory;
    }

    public PerksMainInventory getPerksMainInventory() {
        return perksMainInventory;
    }
}
