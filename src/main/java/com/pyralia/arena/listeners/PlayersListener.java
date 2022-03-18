package com.pyralia.arena.listeners;

import com.pyralia.arena.ArenaAPI;
import com.pyralia.arena.kits.release.GuepKit;
import com.pyralia.arena.kits.release.KokushiboKit;
import com.pyralia.arena.kits.release.LibeKit;
import com.pyralia.arena.listeners.task.LeaderboardTask;
import com.pyralia.arena.player.KPlayer;
import com.pyralia.arena.uis.PackInventory;
import com.pyralia.core.common.ItemCreator;
import com.pyralia.core.common.ranks.Rank;
import com.pyralia.core.common.redis.messaging.MessageWrapper;
import com.pyralia.core.spigot.CorePlugin;
import com.pyralia.core.spigot.player.PyraliaPlayer;

import com.pyralia.core.tools.skin.IdentityChanger;
import fr.ariloxe.eline.Eline;
import fr.ariloxe.eline.player.ElinePlayer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;


/**
 * @author Ariloxe
 */
public class PlayersListener implements Listener {

    private final ArenaAPI instance;
    private final PackInventory packInventory = new PackInventory();

    public PlayersListener(ArenaAPI instance){
        this.instance = instance;
    }

    @EventHandler
    public void onLiquid(BlockFromToEvent blockSpreadEvent) {
        blockSpreadEvent.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent playerDropItemEvent){
        if(playerDropItemEvent.getPlayer().getWorld().getName().contains("Spawn")){
            playerDropItemEvent.setCancelled(true);
            return;
        }
        if(playerDropItemEvent.getPlayer().getLocation().getY() > 100)
            playerDropItemEvent.setCancelled(true);
    }

    @EventHandler
    public void onWeather(WeatherChangeEvent weatherChangeEvent){
        weatherChangeEvent.setCancelled(true);
    }

    @EventHandler
    public void onSpawn(EntitySpawnEvent entitySpawnEvent){
        entitySpawnEvent.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent entityDamageEvent){
        if(entityDamageEvent.getEntity().getWorld().getName().contains("Spawn")){
            entityDamageEvent.setCancelled(true);
            return;
        }

        if(entityDamageEvent.getEntity().getLocation().getY() > 100)
            entityDamageEvent.setCancelled(true);

        if(entityDamageEvent.getEntity() instanceof Player && !ArenaAPI.getkPlayer(((Player) entityDamageEvent.getEntity())).isDamageable())
            entityDamageEvent.setCancelled(true);

    }


    @EventHandler
    public void onObsidian(BlockFromToEvent blockFromToEvent){
        if(blockFromToEvent.getBlock().getType() == Material.OBSIDIAN)
            instance.getGameManager().getLocationList().add(blockFromToEvent.getToBlock().getLocation());
    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent foodLevelChangeEvent){
        foodLevelChangeEvent.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent playerInteractEvent){
        if(playerInteractEvent.getItem() == null)
            return;

        if(playerInteractEvent.getPlayer().getLocation().getWorld().getName().contains("Spawn")){
            if(playerInteractEvent.getItem().getType() == Material.ENDER_PORTAL_FRAME)
                instance.getGuiManager().getSelectKitInventory().open(playerInteractEvent.getPlayer());
            else if(playerInteractEvent.getItem().getType() == Material.CHEST)
                instance.getGuiManager().getPerksMainInventory().open(playerInteractEvent.getPlayer());
            else if(playerInteractEvent.getItem().getType() == Material.BED)
                MessageWrapper.sendToHub(playerInteractEvent.getPlayer());
            else if(playerInteractEvent.getItem().getType() == Material.PAPER)
                packInventory.open(playerInteractEvent.getPlayer());
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent blockPlaceEvent){
        if(blockPlaceEvent.getBlockPlaced().getLocation().getY() > 70){
            blockPlaceEvent.setCancelled(true);
            blockPlaceEvent.getPlayer().sendMessage("§6§lPyralia §8» §cVous ne pouvez pas placer de block si haut !");
            return;
        }

        if(blockPlaceEvent.getPlayer().getInventory().getItemInHand().hasItemMeta()){
            blockPlaceEvent.setCancelled(true);
            return;
        }

        instance.getGameManager().getLocationList().add(blockPlaceEvent.getBlock().getLocation());
    }

    @EventHandler
    public void onBreak(BlockBreakEvent blockBreakEvent){
        if(blockBreakEvent.getPlayer().getGameMode() == GameMode.CREATIVE)
            return;


        if(!instance.getGameManager().getLocationList().contains(blockBreakEvent.getBlock().getLocation())){
            blockBreakEvent.getPlayer().sendMessage("§6§lPyralia §8» §cVous ne pouvez pas casser un bloc qui n'a pas été posé par un joueur.");
            blockBreakEvent.setCancelled(true);
            return;
        }

        instance.getGameManager().getLocationList().remove(blockBreakEvent.getBlock().getLocation());
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent playerRespawnEvent){
        Player player = playerRespawnEvent.getPlayer();

        player.teleport(instance.getGameManager().getWorldManager().getLobbyLocation());

        player.getInventory().clear();
        ItemStack air = new ItemStack(Material.AIR);
        player.getInventory().setHelmet(air);
        player.getInventory().setChestplate(air);
        player.getInventory().setLeggings(air);
        player.getInventory().setBoots(air);

        player.getInventory().setHeldItemSlot(4);

        player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));

        player.getInventory().setItem(0, new ItemCreator(Material.CHEST).name("§d§lCosmétiques §8§l▪ §7§lClic-droit").lore("", "§8» §7Cliquez ici pour choisir vos perks !").get());
        player.getInventory().setItem(2, new ItemCreator(Material.PAPER).name("§e§lPack de Texture §8§l▪ §7§lClic-droit").lore("", "§8» §7Cliquez ici pour télécharger le pack !").get());
        player.getInventory().setItem(4, new ItemCreator(Material.ENDER_PORTAL_FRAME).name("§a§lChoisir son Kit §8§l▪ §7§lClic-droit").lore("", "§8» §7Cliquez ici pour choisir un kit !").get());
        player.getInventory().setItem(8, new ItemCreator(Material.BED).name("§c§lRetourner au Lobby §8§l▪ §7§lClic-droit").lore("", "§8» §7Cliquez ici pour retourner au lobby !").get());

        player.setMaxHealth(20);
        player.setHealth(player.getMaxHealth());
        player.setAllowFlight(false);
        player.setFoodLevel(20);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent playerDeathEvent){
        playerDeathEvent.setDeathMessage(null);
        Player player = playerDeathEvent.getEntity();
        KPlayer kPlayer = ArenaAPI.getkPlayer(player);
        kPlayer.setDeaths(ArenaAPI.getkPlayer(player).getDeaths() + 1);
        if(kPlayer.getKit() instanceof LibeKit)
            IdentityChanger.changeSkin(player, ((LibeKit) kPlayer.getKit()).getSkinsMap().get(kPlayer));

        if(player.getKiller() != null){
            KPlayer kAttacker = ArenaAPI.getkPlayer(player.getKiller());
            if(kAttacker.getkPlayerPerks().getDeathBroadcast() != null){
                kAttacker.getkPlayerPerks().getDeathBroadcast().paste(player.getLocation());
            }

            Bukkit.broadcastMessage("§6§lPyralia §8» " + kAttacker.getkPlayerPerks().getDeathMessagePerks().getDeathMessage().replaceAll("<victim>", player.getName()).replaceAll("<attacker>", player.getKiller().getName()) + " §8[§f" + ((int) player.getKiller().getHealth()) / 2 + "§f ❤§8]");

            ArenaAPI.getkPlayer(player.getKiller()).setKills(ArenaAPI.getkPlayer(player.getKiller()).getKills() + 1);
            player.getKiller().getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 3));
            player.getKiller().getInventory().addItem(new ItemStack(Material.COBBLESTONE, 20));
            if (ArenaAPI.getkPlayer(player.getKiller()).getKit() instanceof GuepKit)
                player.getKiller().getInventory().addItem(new ItemStack(Material.ARROW, 2));
            else if (ArenaAPI.getkPlayer(player.getKiller()).getKit() instanceof GuepKit)
                player.getKiller().getInventory().addItem(new ItemStack(Material.ARROW, 16));


            player.getKiller().setHealth(Math.min(player.getKiller().getHealth() + 4, player.getKiller().getMaxHealth()));
            int a = new Random().nextInt(3);
            if(a == 1){
                PyraliaPlayer pyraliaPlayer = CorePlugin.getPyraliaPlayer(player.getKiller());
                Rank rank = pyraliaPlayer.getRank();
                int creditsWon = 3;
                double multiplicateur = 0.0;

                if(rank == Rank.JOUEUR)
                    multiplicateur = 1.0;
                else if(rank == Rank.STAR || rank == Rank.NITRO)
                    multiplicateur = 1.5;
                else if(rank == Rank.MOON || rank == Rank.ADMIN)
                    multiplicateur = 3.0;
                else
                    multiplicateur = 2.0;

                creditsWon = (int)(creditsWon * multiplicateur);
                pyraliaPlayer.getBukkitPlayer().sendMessage("§7Vous reçevez §d" + creditsWon + " Crédits §7(§eExécution§7) §cx" + multiplicateur);
                pyraliaPlayer.setCredits(pyraliaPlayer.getCredits() + creditsWon);

                if(pyraliaPlayer.getPyraliaGuild() != null){
                    pyraliaPlayer.getPyraliaGuild().addPoints(1);
                    pyraliaPlayer.getBukkitPlayer().sendMessage("§7Vous reçevez §b1 Points de Guilde §7(§bExécution§7) §cx" + multiplicateur);
                }
            }

        } else
            Bukkit.broadcastMessage("§6§lPyralia §8» §3" + player.getName() + "§7 est mort tout seul !");

        playerDeathEvent.getDrops().clear();
        Bukkit.getScheduler().runTaskLater(instance, ()->{
            player.spigot().respawn();

            player.teleport(instance.getGameManager().getWorldManager().getLobbyLocation());
            player.getInventory().clear();
            ItemStack air = new ItemStack(Material.AIR);
            player.getInventory().setHelmet(air);
            player.getInventory().setChestplate(air);
            player.getInventory().setLeggings(air);
            player.getInventory().setBoots(air);

            player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));

            player.getInventory().setItem(0, new ItemCreator(Material.CHEST).name("§d§lCosmétiques §8§l▪ §7§lClic-droit").lore("", "§8» §7Cliquez ici pour choisir vos perks !").get());
            player.getInventory().setItem(2, new ItemCreator(Material.PAPER).name("§e§lPack de Texture §8§l▪ §7§lClic-droit").lore("", "§8» §7Cliquez ici pour télécharger le pack !").get());
            player.getInventory().setItem(4, new ItemCreator(Material.ENDER_PORTAL_FRAME).name("§a§lChoisir son Kit §8§l▪ §7§lClic-droit").lore("", "§8» §7Cliquez ici pour choisir un kit !").get());
            player.getInventory().setItem(8, new ItemCreator(Material.BED).name("§c§lRetourner au Lobby §8§l▪ §7§lClic-droit").lore("", "§8» §7Cliquez ici pour retourner au lobby !").get());

            player.setMaxHealth(20);
            player.setHealth(player.getMaxHealth());
            player.setAllowFlight(false);

            player.setFoodLevel(20);

            if(kPlayer.getKit() instanceof LibeKit){
                IdentityChanger.changeSkin(player, ((LibeKit) kPlayer.getKit()).getSkinsMap().get(kPlayer));
                ((LibeKit) kPlayer.getKit()).getSkinsMap().remove(kPlayer);
            }

        }, 3);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent playerJoinEvent){
        playerJoinEvent.setJoinMessage(null);
        Player player = playerJoinEvent.getPlayer();
        Bukkit.broadcastMessage("§6§lPyralia §8» §a" + player.getName() + "§7 a rejoint l'Arène.");
        ArenaAPI.registerPlayer(player);

        Bukkit.getScheduler().runTaskLater(instance, ()->{
            player.teleport(instance.getGameManager().getWorldManager().getLobbyLocation());
            player.setGameMode(GameMode.SURVIVAL);
            player.getInventory().clear();
            ItemStack air = new ItemStack(Material.AIR);
            player.getInventory().setHelmet(air);
            player.getInventory().setChestplate(air);
            player.getInventory().setLeggings(air);
            player.getInventory().setBoots(air);

            player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));

            player.getInventory().setItem(0, new ItemCreator(Material.CHEST).name("§d§lCosmétiques §8§l▪ §7§lClic-droit").lore("", "§8» §7Cliquez ici pour choisir vos perks !").get());
            player.getInventory().setItem(2, new ItemCreator(Material.PAPER).name("§e§lPack de Texture §8§l▪ §7§lClic-droit").lore("", "§8» §7Cliquez ici pour télécharger le pack !").get());
            player.getInventory().setItem(4, new ItemCreator(Material.ENDER_PORTAL_FRAME).name("§a§lChoisir son Kit §8§l▪ §7§lClic-droit").lore("", "§8» §7Cliquez ici pour choisir un kit !").get());
            player.getInventory().setItem(8, new ItemCreator(Material.BED).name("§c§lRetourner au Lobby §8§l▪ §7§lClic-droit").lore("", "§8» §7Cliquez ici pour retourner au lobby !").get());

            player.sendMessage("");
            player.sendMessage("§6§lPyralia §8| §e§lArène");
            player.sendMessage("");
            player.sendMessage("§6§k!§f§k!§6§k! §e§lNouveautés:");
            player.sendMessage("§8• §7Découvrez notre SoundPack customisé !");
            player.sendMessage("");

            player.setMaxHealth(20);
            player.setHealth(player.getMaxHealth());
            player.setAllowFlight(false);
            player.setFoodLevel(20);

            if(LeaderboardTask.getHologram() != null)
                LeaderboardTask.getHologram().showHologram(player);
        }, 3);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent playerQuitEvent){
        playerQuitEvent.setQuitMessage(null);
        Player player = playerQuitEvent.getPlayer();

        ArenaAPI.getkPlayer(player).refreshStats();
        Bukkit.broadcastMessage("§6§lPyralia §8» §c" + player.getName() + "§7 a quitté l'Arène.");
        if(LeaderboardTask.getHologram() != null)
            LeaderboardTask.getHologram().hideHologram(player);
    }

    private int strengthRate = 30;
    private int resistanceRate = 15;

    @EventHandler
    private void onPatchPotion(EntityDamageByEntityEvent event) {
        event.setDamage(event.getDamage() * 0.90);

        if (!(event.getEntity() instanceof Player))
            return;

        if (!(event.getDamager() instanceof Player))
            return;
        Player damager = (Player) event.getDamager();
        Player player = (Player) event.getEntity();

        if (damager.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {

            if (damager.getActivePotionEffects().stream().filter(potionEffect -> potionEffect.getType().equals(PotionEffectType.INCREASE_DAMAGE)).map(PotionEffect::getAmplifier).findFirst().orElse(-1) == 0) {
                event.setDamage(event.getDamage() / 2.3f *
                        (1 + strengthRate / 100f));
            } else event.setDamage(event.getDamage() *
                    (1 + strengthRate / 100f));
        }
        if (player.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
            if (resistanceRate >= 100) {
                event.setCancelled(true);
            }
            event.setDamage(event.getDamage() * (100 - resistanceRate) / 80f);
        }
    }

    @EventHandler
    public void onPlayerDamaging(EntityDamageByEntityEvent entityDamageByEntityEvent){
        if(entityDamageByEntityEvent.getDamager() instanceof Arrow && entityDamageByEntityEvent.getEntity() instanceof Player && ((Arrow)entityDamageByEntityEvent.getDamager()).getShooter() instanceof Player){
            Player player = ((Player) ((Arrow) entityDamageByEntityEvent.getDamager()).getShooter());
            Player victim = ((Player) entityDamageByEntityEvent.getEntity());
            Bukkit.getScheduler().runTaskLater(instance, ()-> player.sendMessage("§6§lPyralia §8§l» §7" + victim.getName() + " §8§l» " + makePercentColor(victim.getHealth()) + "% §8[§6" + makePercent(entityDamageByEntityEvent.getDamage()) + "%§8]"), 2);
        }
    }

    private String makePercentColor(double health) {
        double hearts = health / 2;
        double percent = hearts * 10;

        if (percent >= 66) {
            return "§a" + ((int) percent);
        } else if (percent >= 33) {
            return "§e" + ((int) percent);
        } else if (percent == 0) {
            return "§0" + (0);
        } else {
            return "§c" + ((int) percent);
        }
    }

    private String makePercent(double health) {
        double hearts = health / 2;
        double percent = hearts * 10;

        if (percent == 0) {
            return "" + (0);
        } else {
            return "" + ((int) percent);
        }
    }

    @EventHandler
    public void onResourcePackStatusEvent(PlayerResourcePackStatusEvent e) {
        Player player = e.getPlayer();
        if (e.getStatus().equals(PlayerResourcePackStatusEvent.Status.ACCEPTED)) {
            player.sendMessage("§6§lPyralia §8§l» §7Téléchargement du pack en cours...");
        } else if (e.getStatus() == PlayerResourcePackStatusEvent.Status.DECLINED) {
            player.sendMessage("§6§lPyralia §8§l» §7Le resource pack est conseillé pour une meilleure expérience de jeu !");
            ComponentBuilder message = new ComponentBuilder("");
            message.append(" §7Téléchargement du pack ");
            message.append("§aCliquez-ici").event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder("§7Télécharger le pack")).create())).event(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://static.pyralia.com/PyraliaArene.zip"));
            message.append("\n ");
            player.spigot().sendMessage(message.create());
        } else if (e.getStatus().equals(PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED)) {
            player.sendMessage("§6§lPyralia §8§l» §7Téléchargement du pack terminé !");
        }
    }


}
