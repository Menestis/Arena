package com.pyralia.arena.manager;

import com.pyralia.arena.ArenaAPI;
import com.pyralia.arena.uis.SelectKitInventory;

/**
 * @author Ariloxe
 */
public class GuiManager {

    private final SelectKitInventory selectKitInventory;

    public GuiManager(ArenaAPI instance){
        this.selectKitInventory = new SelectKitInventory(instance);
    }

    public SelectKitInventory getSelectKitInventory() {
        return selectKitInventory;
    }
}
