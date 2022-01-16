package com.pyralia.arena.perks.deathbroadcast;

import com.pyralia.arena.perks.DeathBroadcast;
import com.pyralia.arena.perks.DeathMessagePerks;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * @author Ariloxe
 */
public class DerriereNousBroadcast extends DeathBroadcast {
    public DerriereNousBroadcast() {
        super("§7Derrière-Nous", "arena.tderrierenous", 100, new ItemStack(Material.NAME_TAG), Arrays.asList("", "§7§lMessage:", "§8- §cderrière nous", ""));
        super.setDeathMessage("§cderrière nous !");
    }
}
