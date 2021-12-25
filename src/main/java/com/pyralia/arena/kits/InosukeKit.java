package com.pyralia.arena.kits;

import com.pyralia.arena.ArenaAPI;
import com.pyralia.arena.player.KPlayer;
import com.pyralia.arena.utils.PlayerUtils;
import com.pyralia.core.common.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Ariloxe
 */
public class InosukeKit extends KitSchedule {

    public InosukeKit() {
        super("Kit d'Inosuke", new ItemStack(Material.RED_ROSE),
                "§8» §7Mode : §3Démon-Slayer",
                "§8» §7Type : §eOffensif",
                "§8» §7Pouvoirs:",
                "§f- §7Faites un Dash en avant d'une 10aine de blocs et faites §c§l3 coeurs§7 de",
                "    §7dégats aux joueurs que vous traverserez. (§a38 secondes de cooldown§7)");
        super.setSecondsDelay(38);
    }

    @Override
    public void power(KPlayer kPlayer){
        Player player = kPlayer.getBukkitPlayer();
        if(player != null){
            player.playSound(player.getLocation(), Sound.ZOMBIE_WOODBREAK, 5.0F, 0.0F);

            (new BukkitRunnable() {
                private int time = 24;

                public void run() {
                    player.setVelocity(player.getLocation().getDirection().multiply(1.2D).setY(0));
                    Location location = player.getLocation();
                    location.getWorld().getNearbyEntities(location, 2.0D, 2.0D, 2.0D).forEach(entity -> {
                        if (entity instanceof Player && entity != player) {
                            Player players = (Player)entity;
                            players.damage(0.0D);
                            players.setVelocity(players.getLocation().getDirection().multiply(-1).setY(0.8D));
                        }
                    });

                    if (time <= 0)
                        cancel();
                    this.time--;
                }
            }).runTaskTimer(ArenaAPI.getApi(), 0L, 1L);
        }
    }

    @Override
    public void onEquip(Player player){
        PlayerUtils.teleportPlayer(player);

        Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), ()-> {
            player.getInventory().clear();
            player.setMaxHealth(20);
            player.setHealth(player.getMaxHealth());

            player.getInventory().setItem(0, new ItemCreator(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, 2).get());
            player.getInventory().setItem(1, new ItemCreator(Material.RED_ROSE).name("§7Charge du Sanglier").lore("", "§fVous permet de charger tête baissée !").get());
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

        }, 3);
    }
}
