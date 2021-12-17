package com.pyralia.arena.kits;

import com.pyralia.arena.utils.PlayerUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author Ariloxe
 */
public class DefaultKit extends Kit {
    public DefaultKit() {
        super("Kit par défaut", new ItemStack(Material.BREAD), "§fLe kit de base");
    }

    @Override
    public void onEquip(Player player) {
        player.getInventory().clear();
        player.setMaxHealth(20);
        player.setHealth(player.getMaxHealth());
        PlayerUtils.giveDefaultKit(player);
        PlayerUtils.teleportPlayer(player);
    }

}
