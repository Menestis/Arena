package com.pyralia.arena.listeners;

import com.pyralia.arena.ArenaAPI;
import com.pyralia.arena.kits.*;
import com.pyralia.arena.kits.release.*;
import com.pyralia.arena.player.KPlayer;
import com.pyralia.arena.utils.BlockUtils;
import com.pyralia.arena.utils.PlayerUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
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
            if(kPlayer.getKit() instanceof MeliodasKit && ((MeliodasKit) kPlayer.getKit()).getkPlayerIntegerMap().containsKey(kPlayer)){
                if(!((MeliodasKit) kPlayer.getKit()).getkPlayerIntegerMap().get(kPlayer).containsKey(ArenaAPI.getkPlayer(((Player) entityDamageByEntityEvent.getDamager())))){
                    ((MeliodasKit) kPlayer.getKit()).getkPlayerIntegerMap().get(kPlayer).put(ArenaAPI.getkPlayer(((Player) entityDamageByEntityEvent.getDamager())), 0.0);
                }
                ((MeliodasKit) kPlayer.getKit()).getkPlayerIntegerMap().get(kPlayer).put(ArenaAPI.getkPlayer(((Player) entityDamageByEntityEvent.getDamager())), ((MeliodasKit) kPlayer.getKit()).getkPlayerIntegerMap().get(kPlayer).get(ArenaAPI.getkPlayer(((Player) entityDamageByEntityEvent.getDamager()))) + entityDamageByEntityEvent.getDamage());
                entityDamageByEntityEvent.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent playerInteractEvent){
        KPlayer kPlayer = ArenaAPI.getkPlayer(playerInteractEvent.getPlayer());
        if(kPlayer.getKit() instanceof GuepKit && kPlayer.getBukkitPlayer().getItemInHand().getType() == Material.BOW){
            if(playerInteractEvent.getAction().name().contains("LEFT")){
                Entity entity = PlayerUtils.getTarget(playerInteractEvent.getPlayer(), 50, 3.4D, true);
                if(entity != null){
                    ((GuepKit) kPlayer.getKit()).getkPlayerEntityMap().put(kPlayer, entity);
                    playerInteractEvent.getPlayer().sendMessage("§6§lPyralia §8§l» §7Cible séléctionnée: §c" + entity.getName());
                }

            }
        }
    }

    @EventHandler
    public void onShootBow(EntityShootBowEvent e) {
        if(!(e.getEntity() instanceof Player))
            return;

        KPlayer kPlayer = ArenaAPI.getkPlayer(((Player) e.getEntity()));
        if(kPlayer.getKit() instanceof GuepKit){
            if (e.getEntity() instanceof Player && e.getProjectile() instanceof org.bukkit.entity.Arrow) {
                if (((GuepKit) kPlayer.getKit()).getkPlayerEntityMap().get(kPlayer) != null){
                    final Entity minEntity = ((GuepKit) kPlayer.getKit()).getkPlayerEntityMap().get(kPlayer);
                    (new BukkitRunnable() {
                        final Entity arrow = e.getProjectile();

                        public void run() {
                            Vector newVelocity;
                            double speed = this.arrow.getVelocity().length();
                            if (this.arrow.isOnGround() || this.arrow.isDead() || minEntity.isDead() || minEntity.getLocation().getWorld() != kPlayer.getBukkitPlayer().getLocation().getWorld()) {
                                cancel();
                                return;
                            }
                            Vector toTarget = minEntity.getLocation().clone().add(new Vector(0.0D, 0.5D, 0.0D))
                                    .subtract(this.arrow.getLocation()).toVector();
                            Vector dirVelocity = this.arrow.getVelocity().clone().normalize();
                            Vector dirToTarget = toTarget.clone().normalize();
                            double angle = dirVelocity.angle(dirToTarget);
                            double newSpeed = 0.9D * speed + 0.14D;

                            if (angle < 0.12D) {
                                newVelocity = dirVelocity.clone().multiply(newSpeed);
                            } else {
                                Vector newDir = dirVelocity.clone().multiply((angle - 0.12D) / angle)
                                        .add(dirToTarget.clone().multiply(0.12D / angle));
                                newDir.normalize();
                                newVelocity = newDir.clone().multiply(newSpeed);
                            }
                            this.arrow.setVelocity(newVelocity.add(new Vector(0.0D, 0.03D, 0.0D)));
                        }
                    }).runTaskTimer(ArenaAPI.getApi(), 1L, 1L);
                }
            }
        }
    }
}
