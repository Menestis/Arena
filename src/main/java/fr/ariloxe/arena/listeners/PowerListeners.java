package fr.ariloxe.arena.listeners;

import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.kits.*;
import fr.ariloxe.arena.kits.release.*;
import fr.ariloxe.arena.kits.Kit;
import fr.ariloxe.arena.kits.KitSchedule;
import fr.ariloxe.arena.player.KPlayer;
import fr.ariloxe.arena.utils.BlockUtils;
import fr.ariloxe.arena.utils.PlayerUtils;
import fr.ariloxe.arena.kits.release.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
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
                if(kit instanceof KitSchedule)
                    ((KitSchedule) kit).use(kPlayer);
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

        Bukkit.getOnlinePlayers().stream().filter(p -> p.getWorld().getName().equals(player.getWorld().getName()) && p.getLocation().distance(player.getLocation()) <= 4).forEach(p -> {
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

            if(!(entityDamageByEntityEvent.getDamager() instanceof Player))
                return;


            KPlayer kAttacker = ArenaAPI.getkPlayer(((Player) entityDamageByEntityEvent.getDamager()));

            if(kPlayer.getKit() instanceof MeliodasKit && ((MeliodasKit) kPlayer.getKit()).getkPlayerIntegerMap().containsKey(kPlayer)){
                if(!((MeliodasKit) kPlayer.getKit()).getkPlayerIntegerMap().get(kPlayer).containsKey(ArenaAPI.getkPlayer(((Player) entityDamageByEntityEvent.getDamager())))){
                    ((MeliodasKit) kPlayer.getKit()).getkPlayerIntegerMap().get(kPlayer).put(ArenaAPI.getkPlayer(((Player) entityDamageByEntityEvent.getDamager())), 0.0);
                }
                ((MeliodasKit) kPlayer.getKit()).getkPlayerIntegerMap().get(kPlayer).put(ArenaAPI.getkPlayer(((Player) entityDamageByEntityEvent.getDamager())), ((MeliodasKit) kPlayer.getKit()).getkPlayerIntegerMap().get(kPlayer).get(ArenaAPI.getkPlayer(((Player) entityDamageByEntityEvent.getDamager()))) + entityDamageByEntityEvent.getDamage());

                entityDamageByEntityEvent.setCancelled(true);
            } else if(kAttacker.getKit() instanceof NobaraKit && ((NobaraKit) kAttacker.getKit()).getPlayerPlayerIntegerMap().containsKey(kPlayer)){
                if(!((NobaraKit) kAttacker.getKit()).getPlayerPlayerIntegerMap().get(kAttacker).containsKey(kPlayer)){
                    ((NobaraKit) kAttacker.getKit()).getPlayerPlayerIntegerMap().get(kAttacker).put(kPlayer, 0);
                }
                ((NobaraKit) kAttacker.getKit()).getPlayerPlayerIntegerMap().get(kAttacker).put(kPlayer, ((NobaraKit) kAttacker.getKit()).getPlayerPlayerIntegerMap().get(kAttacker).get(kPlayer) + 1);

            }
        }
    }
}
