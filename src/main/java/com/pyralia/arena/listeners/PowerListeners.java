package com.pyralia.arena.listeners;

import com.pyralia.arena.ArenaAPI;
import com.pyralia.arena.kits.*;
import com.pyralia.arena.player.KPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

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
            if(name.contains("Choisir") || name.contains("Rentrer dans"))
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
}
