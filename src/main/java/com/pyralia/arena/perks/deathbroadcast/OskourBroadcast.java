package com.pyralia.arena.perks.deathbroadcast;

import com.pyralia.arena.perks.DeathBroadcast;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * @author Ariloxe
 */
public class OskourBroadcast extends DeathBroadcast {
    public OskourBroadcast() {
        super("§c§lOskour", "arena.toskour", 150, new ItemStack(Material.NAME_TAG), Arrays.asList("", "§7§lMessage:", "§8- §c§lOSKOUR !!!", ""));
        super.setDeathMessage("§c§lOSKOUR !!!");
    }
}
