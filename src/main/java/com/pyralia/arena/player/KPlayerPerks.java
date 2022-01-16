package com.pyralia.arena.player;

import com.pyralia.arena.ArenaAPI;
import com.pyralia.arena.perks.DeathBroadcast;
import com.pyralia.arena.perks.DeathMessagePerks;
import com.pyralia.arena.perks.deathmessage.DefaultDeathMessage;

/**
 * @author Ariloxe
 */
public class KPlayerPerks {

    private final KPlayer kPlayer;

    private DeathMessagePerks deathMessagePerks;
    private DeathBroadcast deathBroadcast;

    public KPlayerPerks(KPlayer kPlayer) {
        this.kPlayer = kPlayer;
        this.deathMessagePerks = (DeathMessagePerks)ArenaAPI.getApi().getPerksManager().getPerksList().stream().filter(perks -> perks instanceof DefaultDeathMessage).findFirst().get();
    }

    public DeathMessagePerks getDeathMessagePerks() {
        return deathMessagePerks;
    }

    public void setDeathMessagePerks(DeathMessagePerks deathMessagePerks) {
        this.deathMessagePerks = deathMessagePerks;
    }

    public DeathBroadcast getDeathBroadcast() {
        return deathBroadcast;
    }

    public void setDeathBroadcast(DeathBroadcast deathBroadcast) {
        this.deathBroadcast = deathBroadcast;
    }
}
