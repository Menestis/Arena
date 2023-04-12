package fr.ariloxe.arena.manager;

import fr.ariloxe.arena.kits.*;
import fr.ariloxe.arena.kits.release.*;
import fr.ariloxe.arena.kits.Kit;
import fr.ariloxe.arena.kits.release.*;

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
        this.kitList.add(new SukunaKit());
        this.kitList.add(new DemonRequinKit());
        this.kitList.add(new OursKit());
        this.kitList.add(new TengenKit());
        //this.kitList.add(new MadaraKit());
        this.kitList.add(new LibeKit());
        this.kitList.add(new AkazaKit());
        this.kitList.add(new TitanColossalKit());
        this.kitList.add(new KyogaiKit());
        this.kitList.add(new DakiKit());
        this.kitList.add(new GojoKit());
        this.kitList.add(new NamiKit());
        this.kitList.add(new MeliodasKit());
        this.kitList.add(new KiraKit());
        this.kitList.add(new WitchKit());
       // this.kitList.add(new KokushiboKit());
        this.kitList.add(new TercoMusclor());
        this.kitList.add(new ObanaiKit());
        this.kitList.add(new SasukeKit());
        this.kitList.add(new MuichiroKit());
        this.kitList.add(new GyomeiKit());
        this.kitList.add(new MuzanKit());
        this.kitList.add(new ZenitsuKit());
        this.kitList.add(new LoupGarouBlancKit());
        this.kitList.add(new EkkoKit());
        //this.kitList.add(new NobaraKit());

        this.kitList.add(new GyutaroKit());
        this.kitList.add(new MiphaKit());
        this.kitList.add(new SageKit());
        this.kitList.add(new CharetteKit());
        this.kitList.add(new DemonGorilleKit());
        this.kitList.add(new SengokuKit());
        this.kitList.add(new BrookKit());



        this.defaultKit = defaultKit;
    }

    public List<Kit> getKitList() {
        return kitList;
    }

    public Kit getDefaultKit() {
        return defaultKit;
    }
}
