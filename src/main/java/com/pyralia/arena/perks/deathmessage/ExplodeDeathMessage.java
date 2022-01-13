package com.pyralia.arena.perks.deathmessage;

import com.pyralia.arena.perks.DeathMessagePerks;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * @author Ariloxe
 */
public class ExplodeDeathMessage extends DeathMessagePerks {
    public ExplodeDeathMessage() {
        super("§7Explosé", "arena.explodedeath", -1, new ItemStack(Material.WOOD), Arrays.asList("", "§7§lMessage:", "§8- §eTMNono§7 s'est fait exploser par §cAmandaWest", ""));
        super.setDeathMessage("§e<victim> §7s'est fait exploser par §c<attacker>");
    }
}
