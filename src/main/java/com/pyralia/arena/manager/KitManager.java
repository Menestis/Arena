package com.pyralia.arena.manager;

import com.pyralia.arena.kits.*;
import com.pyralia.arena.kits.release.*;

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
        this.kitList.add(new InosukeKit());
        this.kitList.add(new DemonRequinKit());
        this.kitList.add(new OursKit());
        this.kitList.add(new TengenKit());
        this.kitList.add(new MadaraKit());
        this.kitList.add(new LibeKit());
        this.kitList.add(new AkazaKit());
        this.kitList.add(new TitanColossalKit());
        this.kitList.add(new KyogaiKit());
        this.kitList.add(new DakiKit());
        this.kitList.add(new GojoKit());
        this.kitList.add(new NamiKit());
        this.kitList.add(new MeliodasKit());
        this.kitList.add(new GuepKit());
        this.kitList.add(new KiraKit());
        this.kitList.add(new WitchKit());
        this.kitList.add(new KokushiboKit());
        this.kitList.add(new FurutoKit());
        this.kitList.add(new TercoMusclor());
        this.kitList.add(new ObanaiKit());

        this.defaultKit = defaultKit;
    }

    public List<Kit> getKitList() {
        return kitList;
    }

    public Kit getDefaultKit() {
        return defaultKit;
    }
}
