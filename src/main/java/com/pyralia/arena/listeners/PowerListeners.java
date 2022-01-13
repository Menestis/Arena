package com.pyralia.arena.listeners;

import com.pyralia.arena.ArenaAPI;
import com.pyralia.arena.kits.*;
import com.pyralia.arena.kits.release.ChainsawKit;
import com.pyralia.arena.kits.release.MadaraKit;
import com.pyralia.arena.player.KPlayer;
import com.pyralia.arena.utils.BlockUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ariloxe
 */
public class PowerListeners implements Listener {

    @EventHandler
    public void onRightclick(PlayerInteractEvent playerInteractEvent){
        if(playerInteractEvent.getItem() != null && playerInteractEvent.getItem().hasItemMeta() && playerInteractEvent.getItem().getItemMeta().getDisplayName() != null && playerInteractEvent.getPlayer().getLocation().getBlockY() < 100){
            Player player = playerInteractEvent.getPlayer();
            if(player.getLocation().getBlockY() > 110)
                return;

            String name = playerInteractEvent.getItem().getItemMeta().getDisplayName();
            if(name.contains("Choisir") || name.contains("Rentrer dans") || name.contains("Perks") || name.contains("Lobby") || name.contains("Retour au"))
                return;

            ItemStack itemStack = playerInteractEvent.getItem();
            KPlayer kPlayer = ArenaAPI.getkPlayer(player);
            Kit kit = ArenaAPI.getkPlayer(player).getKit();

            if(kit instanceof ChainsawKit){
                ChainsawKit chainsawKit = ((ChainsawKit) kit);
                if(itemStack.getType() == Material.FEATHER){
                    chainsawKit.use(kPlayer);
                } else if(itemStack.getType() == Material.ENDER_PORTAL_FRAME){
                    chainsawKit.activate(player);
                }
            } else {
                if(kit instanceof KitSchedule){
                    ((KitSchedule) kit).use(kPlayer);
                }
            }
        }
    }

    @EventHandler
    public void onBlockUpdate(EntityChangeBlockEvent event) {
        Block block = event.getBlock();
        if (event.getEntity() instanceof FallingBlock) {
            FallingBlock fallingBlock = (FallingBlock) event.getEntity();
            if (fallingBlock.getCustomName() != null) {
                if (fallingBlock.getCustomName().equals(MadaraKit.METEORE_KEY)) {
                    block.setType(Material.AIR);
                    Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(),()-> BlockUtils.getBlocksInRadius(block.getLocation().clone().subtract(0, 5, 0), 20, false).stream().filter(block1 -> block1.getType() == Material.BEDROCK).forEach(block1 -> block1.setType(Material.AIR)), 20*2);

                    for (Player players : Bukkit.getOnlinePlayers()) {
                        if (players.getWorld() == fallingBlock.getWorld()) {
                            if (players.getGameMode() != GameMode.SPECTATOR) {
                                if (players.getLocation().distance(fallingBlock.getLocation()) <= 10){
                                    players.damage(10);
                                    players.playSound(players.getLocation(), Sound.EXPLODE, 1, 1);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
