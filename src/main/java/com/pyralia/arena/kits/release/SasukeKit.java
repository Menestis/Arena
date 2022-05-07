package com.pyralia.arena.kits.release;

import com.pyralia.api.utils.ItemCreator;
import com.pyralia.arena.ArenaAPI;
import com.pyralia.arena.kits.KitSchedule;
import com.pyralia.arena.kits.KitType;
import com.pyralia.arena.player.KPlayer;
import com.pyralia.arena.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @author Ariloxe
 */
public class SasukeKit extends KitSchedule {
    public SasukeKit() {
        super("Sasuke", KitType.DPS, new ItemStack(Material.BLAZE_POWDER),
                "§8» §7Mode : §6Naruto",
                "§8» §7Pouvoirs:",
                "§f- §7En cliquant sur un joueur avec votre oeil, vous échangerez votre place avec.",
                "    §7ce joueur (§a19 secondes de cooldown§7)"
        );
        super.setSecondsDelay(19);
    }

    @Override
    public void power(KPlayer kPlayer) {
        Player player = kPlayer.getBukkitPlayer();

        if(player != null){
            Player target = PlayerUtils.getTarget(player, 60, 1.4D, true);
            if (target != null) {
                Location targetLocation = target.getLocation().clone();
                target.teleport(player);
                player.teleport(targetLocation);

                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 3.0F, 1.0F);
                target.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 3.0F, 1.0F);
            } else {
                Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), ()->{
                    getkPlayerRemainsList().remove(kPlayer);
                    getPlayerIntegerMap().remove(kPlayer);
                }, 10);
            }
        }
    }

    @Override
    public void onEquip(Player player) {
        player.getInventory().setItem(1, new ItemCreator(Material.BLAZE_POWDER).name("§7Téléportation").get());
    }
}
