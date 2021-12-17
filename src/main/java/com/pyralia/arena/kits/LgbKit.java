package com.pyralia.arena.kits;

import com.pyralia.arena.Main;
import com.pyralia.arena.player.KPlayer;
import com.pyralia.arena.utils.PlayerUtils;
import com.pyralia.core.common.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author Ariloxe
 */
public class LgbKit extends KitSchedule {
    public LgbKit() {
        super("Kit du loup garou blanc", new ItemStack(Material.DRAGON_EGG), "§f15 coeurs.");

        super.setSecondsDelay(45);
    }

    @Override
    public void power(KPlayer kPlayer) {
        Player player = kPlayer.getBukkitPlayer();

        if(player != null){
            player.setAllowFlight(true);
            player.playSound(player.getLocation(), Sound.BAT_HURT, 1, 1);
            player.sendMessage("§6§lPyralia §8§l» §7Vous avez activé le pouvoir du §cDémon-Bat§7 ! Celui-ci vous octroie §620 secondes§7 de Fly !");
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), ()-> player.setAllowFlight(false), 20*20);
        }
    }

    @Override
    public void onEquip(Player player) {
        player.getInventory().clear();
        player.setMaxHealth(25);
        player.setHealth(player.getMaxHealth());

        player.getInventory().setItem(0, new ItemCreator(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, 3).get());
        player.getInventory().setItem(4, new ItemStack(Material.WATER_BUCKET));
        player.getInventory().setItem(2, new ItemStack(Material.GOLDEN_APPLE, 9));
        player.getInventory().setItem(3, new ItemCreator(Material.DIAMOND_PICKAXE).enchant(Enchantment.DIG_SPEED, 3).get());
        player.getInventory().setItem(4, new ItemStack(Material.WATER_BUCKET));
        player.getInventory().setItem(5, new ItemStack(Material.COBBLESTONE, 64));
        player.getInventory().setItem(6, new ItemStack(Material.ARROW, 32));
        player.getInventory().setItem(7, new ItemCreator(Material.BOW).enchant(Enchantment.ARROW_DAMAGE, 2).get());
        player.getInventory().setItem(8, new ItemStack(Material.COBBLESTONE, 64));

        player.getInventory().setHelmet(new ItemCreator(Material.DIAMOND_HELMET).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).get());
        player.getInventory().setChestplate(new ItemCreator(Material.DIAMOND_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).get());
        player.getInventory().setLeggings(new ItemCreator(Material.IRON_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).get());
        player.getInventory().setBoots(new ItemCreator(Material.DIAMOND_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).get());
        PlayerUtils.teleportPlayer(player);
    }
}
