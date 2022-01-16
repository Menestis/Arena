package com.pyralia.arena.perks.deathbroadcast;

import com.pyralia.arena.perks.DeathBroadcast;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * @author Ariloxe
 */
public class OofBroadcast extends DeathBroadcast {
    public OofBroadcast() {
        super("§7Oof", "arena.toof", 150, new ItemStack(Material.NAME_TAG), Arrays.asList("", "§7§lMessage:", "§8- §6§lOOF", ""));
        super.setDeathMessage("§6§lOOF");
    }
}
