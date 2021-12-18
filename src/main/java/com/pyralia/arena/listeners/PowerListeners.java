package com.pyralia.arena.listeners;

import com.pyralia.arena.Main;
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
        if(playerInteractEvent.getItem() != null && playerInteractEvent.getItem().hasItemMeta() && playerInteractEvent.getItem().getItemMeta().getDisplayName() != null && playerInteractEvent.getPlayer().getLocation().getBlockY() < 120){
            Player player = playerInteractEvent.getPlayer();
            String name = playerInteractEvent.getItem().getItemMeta().getDisplayName();
            ItemStack itemStack = playerInteractEvent.getItem();
            KPlayer kPlayer = Main.getkPlayer(player);
            Kit kit = Main.getkPlayer(player).getKit();
            if(kit instanceof DemonBatKit && itemStack.getType() == Material.FEATHER){
                DemonBatKit demonBatKit = ((DemonBatKit) kit);
                demonBatKit.use(kPlayer);
            } else if(kit instanceof TanjiroKit && itemStack.getType() == Material.BLAZE_ROD){
                TanjiroKit tanjiroKit = ((TanjiroKit) kit);
                tanjiroKit.use(kPlayer);
            } else if(kit instanceof ErenKit && itemStack.getType() == Material.DEAD_BUSH){
                ErenKit erenKit = ((ErenKit) kit);
                erenKit.use(kPlayer);

            } else if(kit instanceof RuiKit && itemStack.getType() == Material.STRING){
                RuiKit ruiKit = ((RuiKit) kit);
                ruiKit.use(kPlayer);

            } else if(kit instanceof ChainsawKit){
                ChainsawKit chainsawKit = ((ChainsawKit) kit);
                 if(itemStack.getType() == Material.FEATHER){
                    chainsawKit.use(kPlayer);
                 } else if(itemStack.getType() == Material.ENDER_PORTAL_FRAME){
                     chainsawKit.activate(player);
                 }
            }
        }
    }
}
