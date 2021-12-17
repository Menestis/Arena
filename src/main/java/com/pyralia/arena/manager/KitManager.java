package com.pyralia.arena.manager;

import com.pyralia.arena.kits.DefaultKit;
import com.pyralia.arena.kits.DemonBatKit;
import com.pyralia.arena.kits.Kit;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ariloxe
 */
public class KitManager {

    private final List<Kit> kitList = new ArrayList<>();
    private final Kit defaultKit;

    public KitManager(){
        DefaultKit defaultKit = new DefaultKit();

        this.kitList.add(defaultKit);
        this.kitList.add(new DemonBatKit());

        this.defaultKit = defaultKit;
    }

    public List<Kit> getKitList() {
        return kitList;
    }

    public Kit getDefaultKit() {
        return defaultKit;
    }
}
