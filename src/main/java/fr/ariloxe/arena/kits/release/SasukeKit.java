package fr.ariloxe.arena.kits.release;

import fr.ariloxe.arena.utils.ItemCreator;
import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.kits.KitSchedule;
import fr.ariloxe.arena.kits.KitType;
import fr.ariloxe.arena.player.KPlayer;
import fr.ariloxe.arena.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
            Material material = player.getLocation().add(0, 1, 0).getBlock().getType();
            if(material != Material.AIR){
                player.sendMessage("§3§lMenestis §f» §cVous ne devez pas avoir de bloc au dessus de vous pour user de ce pouvoir.");

                Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), ()->{
                    getkPlayerRemainsList().remove(kPlayer);
                    getPlayerIntegerMap().remove(kPlayer);
                }, 10);
            } else if (target != null) {
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
        player.getInventory().addItem(new ItemCreator(Material.BLAZE_POWDER).name("§7Téléportation").get());
    }
}
