package com.pyralia.arena.listeners;

import com.pyralia.arena.Main;
import com.pyralia.arena.kits.DemonBatKit;
import com.pyralia.arena.kits.Kit;
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
        if(playerInteractEvent.getItem() != null && playerInteractEvent.getItem().hasItemMeta() && playerInteractEvent.getItem().getItemMeta().getDisplayName() != null){
            Player player = playerInteractEvent.getPlayer();
            String name = playerInteractEvent.getItem().getItemMeta().getDisplayName();
            ItemStack itemStack = playerInteractEvent.getItem();
            KPlayer kPlayer = Main.getkPlayer(player);
            Kit kit = Main.getkPlayer(player).getKit();
            if(kit instanceof DemonBatKit && itemStack.getType() == Material.FEATHER){
                DemonBatKit demonBatKit = ((DemonBatKit) kit);
                demonBatKit.use(kPlayer);
            }
        }
    }
}
