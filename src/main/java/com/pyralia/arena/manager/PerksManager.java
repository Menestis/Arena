package com.pyralia.arena.manager;

import com.pyralia.arena.perks.Perks;
import com.pyralia.arena.perks.deathbroadcast.DerriereNousBroadcast;
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

        perksList.add(new DerriereNousBroadcast());
    }

    public List<Perks> getPerksList() {
        return perksList;
    }
}
