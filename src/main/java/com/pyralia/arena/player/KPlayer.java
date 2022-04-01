package com.pyralia.arena.player;

import com.pyralia.api.PyraliaAPI;
import com.pyralia.arena.ArenaAPI;
import com.pyralia.arena.kits.Kit;
import com.pyralia.arena.utils.mongo.ArenaCollection;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Ariloxe
 */
public class KPlayer {

    private final UUID uuid;
    private final String name;
    private final KPlayerPerks kPlayerPerks;

    private boolean damageable = true;
    private Kit kit;

    private int kills;
    private int deaths;


    public KPlayer(UUID uuid, String name, int kills, int deaths) {
        this.uuid = uuid;
        this.name = name;
        this.kPlayerPerks = new KPlayerPerks(this);

        this.kills = kills;
        this.deaths = deaths;
    }

    public Player getBukkitPlayer(){
        return Bukkit.getPlayer(uuid);
    }

    public void sendMessage(String message){
        if(getBukkitPlayer() != null)
            getBukkitPlayer().sendMessage(message);

    }

    public Kit getKit() {
        return kit;
    }

    public void setKit(Kit kit) {
        this.kit = kit;
    }

    public void refreshStats(){
        PyraliaAPI.getInstance().getMongoManager().setFromCollection(ArenaCollection.class, this.uuid.toString(), "kills", this.kills);
        PyraliaAPI.getInstance().getMongoManager().setFromCollection(ArenaCollection.class, this.uuid.toString(), "deaths", this.deaths);
    }

    public KPlayerPerks getkPlayerPerks() {
        return kPlayerPerks;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }


    public void setDamageable(boolean damageable) {
        this.damageable = damageable;
    }

    public boolean isDamageable() {
        return damageable;
    }
}
