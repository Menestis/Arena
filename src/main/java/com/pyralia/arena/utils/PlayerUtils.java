package com.pyralia.arena.utils;

import com.pyralia.arena.ArenaAPI;
import com.pyralia.core.common.ItemCreator;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

/**
 * @author Ariloxe
 */
public class PlayerUtils {

    public static void teleportPlayer(Player player){
        player.teleport(ArenaAPI.getApi().getGameManager().getLocationList().get(new Random().nextInt(ArenaAPI.getApi().getGameManager().getLocationList().size())));
    }

    public static void giveDefaultKit(Player player){
        player.getInventory().clear();
        player.setMaxHealth(20);
        player.setHealth(player.getMaxHealth());

        player.getInventory().setItem(0, new ItemCreator(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, 2).get());
        player.getInventory().setItem(1, new ItemCreator(Material.FISHING_ROD).get());
        player.getInventory().setItem(2, new ItemStack(Material.GOLDEN_APPLE, 9));
        player.getInventory().setItem(3, new ItemCreator(Material.DIAMOND_PICKAXE).enchant(Enchantment.DIG_SPEED, 3).get());
        player.getInventory().setItem(4, new ItemStack(Material.WATER_BUCKET));
        player.getInventory().setItem(5, new ItemStack(Material.COBBLESTONE, 64));
        player.getInventory().setItem(6, new ItemStack(Material.ARROW, 32));
        player.getInventory().setItem(7, new ItemCreator(Material.BOW).enchant(Enchantment.ARROW_DAMAGE, 2).get());
        player.getInventory().setItem(8, new ItemStack(Material.COBBLESTONE, 64));

        player.getInventory().setHelmet(new ItemCreator(Material.DIAMOND_HELMET).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).get());
        player.getInventory().setChestplate(new ItemCreator(Material.DIAMOND_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).get());
        player.getInventory().setLeggings(new ItemCreator(Material.IRON_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).get());
        player.getInventory().setBoots(new ItemCreator(Material.DIAMOND_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).get());
    }

}
