package com.pyralia.arena.perks.deathmessage;

import com.pyralia.arena.perks.DeathMessagePerks;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * @author Ariloxe
 */
public class AneantiDeathMessage extends DeathMessagePerks {
    public AneantiDeathMessage() {
        super("§7Explosé", "arena.aneantideathmessage", 150, new ItemStack(Material.WOOD), Arrays.asList("", "§7§lMessage:", "§8- §5Magnus_57§7 a ANÉANTI §dAriloxe", ""));
        super.setDeathMessage("§5<attacker> §7a ANÉANTI §d<victim>");
    }
}
