package com.pyralia.arena.perks.deathmessage;

import com.pyralia.arena.perks.DeathMessagePerks;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * @author Ariloxe
 */
public class DefaultDeathMessage extends DeathMessagePerks {
    public DefaultDeathMessage() {
        super("§7Message par Défaut", null, -1, new ItemStack(Material.WOOD), Arrays.asList("", "§7§lMessage:", "§8- §6Dompe_§7 est mort par §cAriloxe", ""));
        super.setDeathMessage("§6<victim> §7est mort par §c<attacker>");
    }
}
