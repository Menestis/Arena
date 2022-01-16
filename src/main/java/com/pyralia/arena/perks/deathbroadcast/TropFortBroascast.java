package com.pyralia.arena.perks.deathbroadcast;

import com.pyralia.arena.perks.DeathBroadcast;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * @author Ariloxe
 */
public class TropFortBroascast extends DeathBroadcast {
    public TropFortBroascast() {
        super("§eT tro for uesh", "arena.ttropfort", 150, new ItemStack(Material.NAME_TAG), Arrays.asList("", "§7§lMessage:", "§8- §eT tro for uesh", ""));
        super.setDeathMessage("§eT tro for uesh");
    }
}
