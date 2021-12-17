package com.pyralia.arena.player;

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


    public KPlayer(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
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
}
