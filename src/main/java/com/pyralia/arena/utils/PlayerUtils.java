package com.pyralia.arena.utils;

import com.pyralia.arena.ArenaAPI;
import com.pyralia.core.common.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Ariloxe
 */
public class PlayerUtils {

    public static void giveDefaultKit(Player player){
        player.getInventory().clear();
        player.setMaxHealth(20);
        player.setHealth(player.getMaxHealth());

        player.getInventory().setItem(0, new ItemCreator(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, 3).get());
        player.getInventory().setItem(1, new ItemCreator(Material.FISHING_ROD).get());
        player.getInventory().setItem(2, new ItemStack(Material.GOLDEN_APPLE, 9));
        player.getInventory().setItem(3, new ItemCreator(Material.DIAMOND_PICKAXE).enchant(Enchantment.DIG_SPEED, 3).get());
        player.getInventory().setItem(4, new ItemStack(Material.WATER_BUCKET));
        player.getInventory().setItem(5, new ItemStack(Material.COBBLESTONE, 64));
        player.getInventory().setItem(6, new ItemStack(Material.ARROW, 32));
        player.getInventory().setItem(7, new ItemCreator(Material.BOW).enchant(Enchantment.ARROW_DAMAGE, 2).get());
        player.getInventory().setItem(8, new ItemStack(Material.COBBLESTONE, 64));

        player.getInventory().setHelmet(new ItemCreator(Material.DIAMOND_HELMET).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).unbreakable(true).get());
        player.getInventory().setChestplate(new ItemCreator(Material.DIAMOND_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).unbreakable(true).get());
        player.getInventory().setLeggings(new ItemCreator(Material.IRON_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).unbreakable(true).get());
        player.getInventory().setBoots(new ItemCreator(Material.DIAMOND_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).unbreakable(true).get());
    }

    public static Player getTarget(Player player, int maxRange, double aiming, boolean wallHack) {
        Player target = null;
        double distance = 0.0D;
        Location playerEyes = player.getEyeLocation();
        Vector direction = playerEyes.getDirection().normalize();
        List<Player> targets = new ArrayList<>();
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online == player ||
                    !online.getWorld().equals(player.getWorld()) || online
                    .getLocation().distanceSquared(playerEyes) > (maxRange * maxRange) || online
                    .getGameMode().equals(GameMode.SPECTATOR))
                continue;
            targets.add(online);
        }
        if (targets.size() > 0) {
            Location loc = playerEyes.clone();
            Vector progress = direction.clone().multiply(0.7D);
            maxRange = 100 * maxRange / 70;
            int loop = 0;
            while (loop < maxRange) {
                loop++;
                loc.add(progress);
                Block block = loc.getBlock();
                if (!wallHack && block.getType().isSolid())
                    break;
                double lx = loc.getX();
                double ly = loc.getY();
                double lz = loc.getZ();
                for (Player possibleTarget : targets) {
                    if (possibleTarget == player)
                        continue;
                    Location testLoc = possibleTarget.getLocation().add(0.0D, 0.85D, 0.0D);
                    double px = testLoc.getX();
                    double py = testLoc.getY();
                    double pz = testLoc.getZ();
                    boolean dX = (Math.abs(lx - px) < 0.7D * aiming);
                    boolean dY = (Math.abs(ly - py) < 1.7D * aiming);
                    boolean dZ = (Math.abs(lz - pz) < 0.7D * aiming);
                    if (dX && dY && dZ) {
                        target = possibleTarget;
                        break;
                    }
                }
                if (target != null) {
                    distance = (loop * 70 / 100);
                    break;
                }
            }
        }
        if (target != null)
            return target;
        return null;
    }
}
