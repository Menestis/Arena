package com.pyralia.arena.manager;

import com.pyralia.arena.Main;
import com.pyralia.arena.kits.DefaultKit;
import com.pyralia.arena.kits.DemonBatKit;
import com.pyralia.arena.kits.Kit;
import com.pyralia.arena.uis.SelectKitInventory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ariloxe
 */
public class GuiManager {

    private final SelectKitInventory selectKitInventory;

    public GuiManager(Main instance){
        this.selectKitInventory = new SelectKitInventory(instance);
    }

    public SelectKitInventory getSelectKitInventory() {
        return selectKitInventory;
    }
}
