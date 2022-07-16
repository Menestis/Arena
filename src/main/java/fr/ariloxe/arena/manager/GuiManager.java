package fr.ariloxe.arena.manager;

import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.uis.kits.SelectMainInventory;

/**
 * @author Ariloxe
 */
public class GuiManager {

    private final SelectMainInventory selectMainInventory;

    public GuiManager(ArenaAPI instance){
        this.selectMainInventory = new SelectMainInventory(instance);
    }

    public SelectMainInventory getSelectKitInventory() {
        return selectMainInventory;
    }

}
