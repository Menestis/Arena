package com.pyralia.arena.listeners;

import com.pyralia.arena.ArenaAPI;
import com.pyralia.arena.kits.*;
import com.pyralia.arena.kits.release.ChainsawKit;
import com.pyralia.arena.kits.release.LibeKit;
import com.pyralia.arena.kits.release.MadaraKit;
import com.pyralia.arena.kits.release.MeliodasKit;
import com.pyralia.arena.player.KPlayer;
import com.pyralia.arena.utils.BlockUtils;
import com.pyralia.arena.utils.PlayerUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.*;

/**
 * @author Ariloxe
 */
public class PowerListeners implements Listener {

    // GENERAL
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

    //MADARA
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

    //LIBE
    private final Map<UUID, Double> lastDamage = new HashMap<>();
    private final Map<UUID, Boolean> canHit = new HashMap<>();

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player))
            return;

        lastDamage.put(event.getDamager().getUniqueId(), event.getDamage());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        KPlayer kPlayer = ArenaAPI.getkPlayer(player);
        Action action = event.getAction();
        if (action.name().contains("RIGHT"))
            return;
        if(!event.getPlayer().getItemInHand().getType().equals(Material.DIAMOND_SWORD))
            return;
        if (!(kPlayer.getKit() instanceof LibeKit))
            return;
        if(!PlayerUtils.getCardinalDirection(player).equals("Sud"))
            return;

        Bukkit.getOnlinePlayers().stream().filter(p -> p.getLocation().distance(player.getLocation()) <= 4).forEach(p -> {
            if (getLookingAt(player, p) && canHit.getOrDefault(player.getUniqueId(), true)) {
                p.damage(1);
                p.setVelocity(player.getLocation().getDirection().multiply(0.5).setY(0.3));
                p.sendMessage("§fLibe_§c vous mets un dégât !");
                p.playSound(p.getLocation(), "pyralia.libe", 5, 5);
                canHit.put(player.getUniqueId(), false);
                Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), () -> canHit.put(player.getUniqueId(), true), 100);
            }
        });
    }

    private boolean getLookingAt(Player player, Player player1) {
        Location eye = player.getEyeLocation();
        Vector toEntity = player1.getEyeLocation().toVector().subtract(eye.toVector());
        double dot = toEntity.normalize().dot(eye.getDirection());

        return dot > 0.59D;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent entityDamageByEntityEvent){
        if(entityDamageByEntityEvent.getDamager() instanceof Player && entityDamageByEntityEvent.getEntity() instanceof Player){
            KPlayer kPlayer = ArenaAPI.getkPlayer(((Player) entityDamageByEntityEvent.getEntity()));
            if(kPlayer.getKit() instanceof MeliodasKit && ((MeliodasKit) kPlayer.getKit()).getkPlayerIntegerMap().containsKey(kPlayer)){
                if(!((MeliodasKit) kPlayer.getKit()).getkPlayerIntegerMap().get(kPlayer).containsKey(ArenaAPI.getkPlayer(((Player) entityDamageByEntityEvent.getDamager())))){
                    ((MeliodasKit) kPlayer.getKit()).getkPlayerIntegerMap().get(kPlayer).put(ArenaAPI.getkPlayer(((Player) entityDamageByEntityEvent.getDamager())), (int)entityDamageByEntityEvent.getDamage());
                }
                ((MeliodasKit) kPlayer.getKit()).getkPlayerIntegerMap().get(kPlayer).put(ArenaAPI.getkPlayer(((Player) entityDamageByEntityEvent.getDamager())), ((MeliodasKit) kPlayer.getKit()).getkPlayerIntegerMap().get(kPlayer).get(ArenaAPI.getkPlayer(((Player) entityDamageByEntityEvent.getDamager()))) + (int)entityDamageByEntityEvent.getDamage());
                entityDamageByEntityEvent.setCancelled(true);
            }
        }
    }
}
