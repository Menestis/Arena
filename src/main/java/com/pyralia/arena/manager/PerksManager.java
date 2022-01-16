package com.pyralia.arena.manager;

import com.pyralia.arena.perks.Perks;
import com.pyralia.arena.perks.deathbroadcast.*;
import com.pyralia.arena.perks.deathmessage.AneantiDeathMessage;
import com.pyralia.arena.perks.deathmessage.DefaultDeathMessage;
import com.pyralia.arena.perks.deathmessage.ExplodeDeathMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ariloxe
 */
public class PerksManager {

    private final List<Perks> perksList = new ArrayList<>();

    public PerksManager(){
        perksList.add(new DefaultDeathMessage());
        perksList.add(new ExplodeDeathMessage());
        perksList.add(new AneantiDeathMessage());
        perksList.add(new WTFBroadcast());

        perksList.add(new DerriereNousBroadcast());
        perksList.add(new OofBroadcast());
        perksList.add(new OskourBroadcast());
        perksList.add(new TropFortBroascast());
    }

    public List<Perks> getPerksList() {
        return perksList;
    }
}
