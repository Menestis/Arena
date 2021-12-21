package com.pyralia.arena.manager;

import com.pyralia.arena.kits.*;

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
        this.kitList.add(new TanjiroKit());
        this.kitList.add(new ChainsawKit());
        this.kitList.add(new ErenKit());
        this.kitList.add(new RuiKit());
        this.kitList.add(new FreezKit());
        this.kitList.add(new SukunaKit());

        this.defaultKit = defaultKit;
    }

    public List<Kit> getKitList() {
        return kitList;
    }

    public Kit getDefaultKit() {
        return defaultKit;
    }
}
