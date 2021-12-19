package com.pyralia.arena.player;

import com.pyralia.arena.Main;
import com.pyralia.arena.kits.Kit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Ariloxe
 */
public class KPlayer {

    private final UUID uuid;
    private final String name;
    private Kit kit;

    private int kills;
    private int deaths;


    public KPlayer(UUID uuid, String name, int kills, int deaths) {
        this.uuid = uuid;
        this.name = name;

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
        Main.getInstance().getDatabaseManager().setArenaCollection(this.uuid, "kills", this.kills);
        Main.getInstance().getDatabaseManager().setArenaCollection(this.uuid, "deaths", this.deaths);
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
}
