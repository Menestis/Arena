package com.pyralia.arena.kits;

import com.pyralia.arena.ArenaAPI;
import com.pyralia.arena.player.KPlayer;
import com.pyralia.arena.utils.PlayerUtils;
import com.pyralia.core.common.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author Ariloxe
 */
public class TengenKit extends KitSchedule {
    public TengenKit() {
        super("Kit de Tengen", new ItemStack(Material.RED_ROSE),
                "§8» §7Mode : §3Démon-Slayer",
                "§8» §7Type : §eOffensif",
                "§8» §7Pouvoirs:",
                "§f- §7En cliquant sur un joueur avec votre fleur, vous-vous téléporterez derrière lui.",
                "    §7(§a23 secondes de cooldown§7)"
        );
        super.setSecondsDelay(23);
    }

    @Override
    public void power(KPlayer kPlayer) {
        Player player = kPlayer.getBukkitPlayer();

        if(player != null){
            Player target = PlayerUtils.getTarget(player, 60, 1.4D, true);
            if (target != null) {
                Location targetLocation = target.getLocation().clone();
                targetLocation.add(targetLocation.getDirection().clone().multiply(-1));
                player.teleport(targetLocation);
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 3.0F, 1.0F);
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
        player.getInventory().setItem(1, new ItemCreator(Material.RED_ROSE).name("§7Téléportation").lore("", "§fVous permet de vous TP sur un mec.").get());
    }
}
