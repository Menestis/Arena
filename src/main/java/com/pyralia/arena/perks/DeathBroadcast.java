package com.pyralia.arena.perks;

import com.pyralia.api.utils.holograms.Hologram;
import com.pyralia.arena.ArenaAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ariloxe
 */
public abstract class DeathBroadcast extends Perks {

    private String deathMessage;

    protected DeathBroadcast(String name, String permission, int price, ItemStack itemStack, List<String> description) {
        super(name, permission, price, itemStack, description);
    }

    public void setDeathMessage(String deathMessage) {
        this.deathMessage = deathMessage;
    }

    public String getDeathMessage() {
        return deathMessage;
    }


    public void paste(Location location){
        Hologram hologram = new Hologram(Arrays.asList(getDeathMessage()), location.clone().subtract(0, 1, 0));
        Bukkit.getOnlinePlayers().forEach(hologram::showHologram);

        Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), ()-> Bukkit.getOnlinePlayers().forEach(hologram::hideHologram), 20*5);
    }

}
