package com.pyralia.arena.perks.deathbroadcast;

import com.pyralia.arena.perks.DeathBroadcast;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * @author Ariloxe
 */
public class WTFBroadcast extends DeathBroadcast {
    public WTFBroadcast() {
        super("§2§kLLLLLLL", "arena.twtf", 150, new ItemStack(Material.NAME_TAG), Arrays.asList("", "§7§lMessage:", "§8- §2§kLLLLLLL", ""));
        super.setDeathMessage("§2§kLLLLLLL");
    }
}
