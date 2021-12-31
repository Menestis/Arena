package com.pyralia.arena.kits;

import com.pyralia.arena.ArenaAPI;
import com.pyralia.arena.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author Ariloxe
 */
public class DefaultKit extends Kit {
    public DefaultKit() {
        super("Kit par défaut", new ItemStack(Material.BREAD),
                "§8» §7Mode : §c/",
                "§8» §7Type : §e/",
                "§8» §7Pouvoirs:",
                "§f- §7Vous avez une canne à pêche dans votre kit.");
    }

    @Override
    public void onEquip(Player player) {}

}
