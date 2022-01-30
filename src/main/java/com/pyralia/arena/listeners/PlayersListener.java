package com.pyralia.arena.listeners;

import com.pyralia.arena.ArenaAPI;
import com.pyralia.arena.kits.release.LibeKit;
import com.pyralia.arena.player.KPlayer;
import com.pyralia.core.common.ItemCreator;
import com.pyralia.core.common.ranks.Rank;
import com.pyralia.core.common.redis.messaging.MessageWrapper;
import com.pyralia.core.spigot.CorePlugin;
import com.pyralia.core.spigot.player.PyraliaPlayer;

import com.pyralia.core.tools.skin.IdentityChanger;
import fr.ariloxe.eline.Eline;
import fr.ariloxe.eline.player.ElinePlayer;
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

    private ArenaAPI instance;

    public PlayersListener(ArenaAPI instance){
        this.instance = instance;
    }

    @EventHandler
    public void onLiquid(BlockFromToEvent blockSpreadEvent) {
        blockSpreadEvent.setCancelled(true);
    }


    @EventHandler
    public void onChat(AsyncPlayerChatEvent asyncPlayerChatEvent){
        Player player = asyncPlayerChatEvent.getPlayer();
        String message = asyncPlayerChatEvent.getMessage();
        asyncPlayerChatEvent.setCancelled(true);

        PyraliaPlayer pyraliaPlayer = CorePlugin.getPyraliaPlayer(player);
        ElinePlayer elinePlayer = Eline.get().getPlayerService().getElinePlayer(player);
        if(elinePlayer.getPyraliaMute().isMute()){
            player.sendMessage(elinePlayer.getPyraliaMute().getMuteMessage());
            return;
        }

        Bukkit.broadcastMessage(pyraliaPlayer.getRank().getTabPrefix() + " " + player.getName() + " §8» §f" + message);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent playerDropItemEvent){
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
        if(entityDamageEvent.getEntity().getLocation().getY() > 100)
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
    public void onInteract(PlayerInteractEvent playerDropItemEvent){

        if(playerDropItemEvent.getPlayer().getLocation().getY() > 100 && playerDropItemEvent.getItem() != null && playerDropItemEvent.getAction().name().contains("RIGHT")){
            if(playerDropItemEvent.getItem().getType() == Material.ENDER_PORTAL_FRAME)
                instance.getGuiManager().getSelectKitInventory().open(playerDropItemEvent.getPlayer());
            else if(playerDropItemEvent.getItem().getType() == Material.CHEST)
                instance.getGuiManager().getPerksMainInventory().open(playerDropItemEvent.getPlayer());
            else if(playerDropItemEvent.getItem().getType() == Material.BED)
                MessageWrapper.sendToHub(playerDropItemEvent.getPlayer());
            else if(playerDropItemEvent.getItem().getType() == Material.PAPER)
                Bukkit.dispatchCommand(playerDropItemEvent.getPlayer(), "pyralialink getlink");
            else if(playerDropItemEvent.getItem().getType() == Material.BOOK)
                Bukkit.dispatchCommand(playerDropItemEvent.getPlayer(), "pyralialink config");
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent blockPlaceEvent){
        if(blockPlaceEvent.getBlockPlaced().getLocation().getY() > 70){
            blockPlaceEvent.setCancelled(true);
            blockPlaceEvent.getPlayer().sendMessage("§6§lPyralia §8» §cVous ne pouvez pas placer de block si haut !");
        }

        if(blockPlaceEvent.getPlayer().getInventory().getItemInHand().hasItemMeta())
            blockPlaceEvent.setCancelled(true);

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

        player.teleport(instance.getGameManager().getSpecialWorld().getLobbyLocation());

        player.getInventory().clear();
        ItemStack air = new ItemStack(Material.AIR);
        player.getInventory().setHelmet(air);
        player.getInventory().setChestplate(air);
        player.getInventory().setLeggings(air);
        player.getInventory().setBoots(air);

        player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));

        player.getInventory().setItem(0, new ItemCreator(Material.CHEST).name("§8» §7Perks §8«").lore("", "§8» §7Cliquez ici pour choisir vos perks !").get());
        player.getInventory().setItem(2, new ItemCreator(Material.PAPER).name("§8» §7Rejoindre le Mumble §8«").lore("", "§8» §7Cliquez ici pour rejoindre le mumble !").get());
        player.getInventory().setItem(4, new ItemCreator(Material.ENDER_PORTAL_FRAME).name("§8» §7Entrer dans l'Arène §8«").lore("", "§8» §7Cliquez ici pour choisir un kit !").get());
        player.getInventory().setItem(6, new ItemCreator(Material.BOOK).name("§8» §7Configurer le Mumble §8«").lore("", "§8» §7Cliquez ici pour configurer le mumble !").get());
        player.getInventory().setItem(8, new ItemCreator(Material.BED).name("§8» §7Retour au Lobby §8«").lore("", "§8» §7Cliquez ici pour retourner au lobby !").get());

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
            }

        } else
            Bukkit.broadcastMessage("§6§lPyralia §8» §3" + player.getName() + "§7 est mort tout seul !");

        playerDeathEvent.getDrops().clear();
        Bukkit.getScheduler().runTaskLater(instance, ()->{
            player.spigot().respawn();

            player.teleport(instance.getGameManager().getSpecialWorld().getLobbyLocation());
            player.getInventory().clear();
            ItemStack air = new ItemStack(Material.AIR);
            player.getInventory().setHelmet(air);
            player.getInventory().setChestplate(air);
            player.getInventory().setLeggings(air);
            player.getInventory().setBoots(air);

            player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));

            player.getInventory().setItem(0, new ItemCreator(Material.CHEST).name("§8» §7Perks §8«").lore("", "§8» §7Cliquez ici pour choisir vos perks !").get());
            //player.getInventory().setItem(2, new ItemCreator(Material.PAPER).name("§8» §7Rejoindre le Mumble §8«").lore("", "§8» §7Cliquez ici pour rejoindre le mumble !").get());
            player.getInventory().setItem(4, new ItemCreator(Material.ENDER_PORTAL_FRAME).name("§8» §7Entrer dans l'Arène §8«").lore("", "§8» §7Cliquez ici pour choisir un kit !").get());
            //player.getInventory().setItem(6, new ItemCreator(Material.BOOK).name("§8» §7Configurer le Mumble §8«").lore("", "§8» §7Cliquez ici pour configurer le mumble !").get());
            player.getInventory().setItem(8, new ItemCreator(Material.BED).name("§8» §7Retour au Lobby §8«").lore("", "§8» §7Cliquez ici pour retourner au lobby !").get());

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
            player.teleport(instance.getGameManager().getSpecialWorld().getLobbyLocation());
            player.setGameMode(GameMode.SURVIVAL);
            player.getInventory().clear();
            ItemStack air = new ItemStack(Material.AIR);
            player.getInventory().setHelmet(air);
            player.getInventory().setChestplate(air);
            player.getInventory().setLeggings(air);
            player.getInventory().setBoots(air);

            player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));

            player.getInventory().setItem(0, new ItemCreator(Material.CHEST).name("§8» §7Perks §8«").lore("", "§8» §7Cliquez ici pour choisir vos perks !").get());
          //  player.getInventory().setItem(2, new ItemCreator(Material.PAPER).name("§8» §7Rejoindre le Mumble §8«").lore("", "§8» §7Cliquez ici pour rejoindre le mumble !").get());
            player.getInventory().setItem(4, new ItemCreator(Material.ENDER_PORTAL_FRAME).name("§8» §7Entrer dans l'Arène §8«").lore("", "§8» §7Cliquez ici pour choisir un kit !").get());
           // player.getInventory().setItem(6, new ItemCreator(Material.BOOK).name("§8» §7Configurer le Mumble §8«").lore("", "§8» §7Cliquez ici pour configurer le mumble !").get());
            player.getInventory().setItem(8, new ItemCreator(Material.BED).name("§8» §7Retour au Lobby §8«").lore("", "§8» §7Cliquez ici pour retourner au lobby !").get());

            player.sendMessage("");
            player.sendMessage("§6§lPyralia §8» §7Bienvenue dans l'Arène, ici vous pourrez combattre d'autres joueurs librement !");
            player.sendMessage("");
            player.sendMessage("§8» §7Le crossteam est §cinterdit§7 dans ce mini-jeux !");
            player.sendMessage("§8» §7Toute forme de triche est également §cprohibée§7 !");
            player.sendMessage("§8» §7Enfin, une §6bonne attitude§7 et du §2fairplay§7 sont encouragés !");
            player.sendMessage("");
            player.sendMessage("§8» §7Si vous souhaitez retourner au Lobby, faites §c/hub§7.");
            player.sendMessage("");

            player.setMaxHealth(20);
            player.setHealth(player.getMaxHealth());
            player.setAllowFlight(false);
            player.setFoodLevel(20);
        }, 3);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent playerQuitEvent){
        playerQuitEvent.setQuitMessage(null);
        Player player = playerQuitEvent.getPlayer();

        ArenaAPI.getkPlayer(player).refreshStats();
        Bukkit.broadcastMessage("§6§lPyralia §8» §c" + player.getName() + "§7 a quitté l'Arène.");
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


}
