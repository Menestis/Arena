package com.pyralia.arena.listeners;

import com.pyralia.arena.Main;
import com.pyralia.core.common.ItemCreator;
import com.pyralia.core.spigot.CorePlugin;
import com.pyralia.core.spigot.player.PyraliaPlayer;
import org.bukkit.Bukkit;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;


/**
 * @author Ariloxe
 */
public class PlayersListener implements Listener {

    private Main instance;

    public PlayersListener(Main instance){
        this.instance = instance;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent asyncPlayerChatEvent){
        Player player = asyncPlayerChatEvent.getPlayer();
        String message = asyncPlayerChatEvent.getMessage();
        asyncPlayerChatEvent.setCancelled(true);

        PyraliaPlayer pyraliaPlayer = CorePlugin.getPyraliaPlayer(player);
        if(pyraliaPlayer.getPyraliaMute().isMute()){
            player.sendMessage(pyraliaPlayer.getPyraliaMute().getMuteMessage());
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
        if(playerDropItemEvent.getPlayer().getLocation().getY() > 100 && playerDropItemEvent.getItem() != null && playerDropItemEvent.getItem().getType() == Material.DIAMOND_SWORD && playerDropItemEvent.getAction().name().contains("RIGHT"))
            instance.getGameManager().joinArena(playerDropItemEvent.getPlayer());
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent blockPlaceEvent){
        if(blockPlaceEvent.getBlockPlaced().getLocation().getY() > 70){
            blockPlaceEvent.setCancelled(true);
            blockPlaceEvent.getPlayer().sendMessage("§6§lPyralia §8» §cVous ne pouvez pas placer de block si haut !");
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

        player.teleport(instance.getGameManager().getLobbyLocation());

        player.getInventory().clear();
        ItemStack air = new ItemStack(Material.AIR);
        player.getInventory().setHelmet(air);
        player.getInventory().setChestplate(air);
        player.getInventory().setLeggings(air);
        player.getInventory().setBoots(air);

        player.getInventory().setItem(4, new ItemCreator(Material.DIAMOND_SWORD).name("§8» §7Rentrer dans l'Arène").lore("", "§8» §7Cliquez ici pour rejoindre le combat !").get());

        player.setMaxHealth(20);
        player.setHealth(player.getMaxHealth());

        player.setFoodLevel(20);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent playerDeathEvent){
        playerDeathEvent.setDeathMessage(null);

        Player player = playerDeathEvent.getEntity();

        if(player.getKiller() != null){
            Bukkit.broadcastMessage("§6§lPyralia §8» §3" + player.getName() + "§7 est mort par le joueur §3" + player.getKiller().getName() + "§8 [§f" + ((int) player.getKiller().getHealth()) / 2 + "§f ♥§8]");
            player.getKiller().setStatistic(Statistic.PLAYER_KILLS, player.getKiller().getStatistic(Statistic.PLAYER_KILLS) + 1);
        } else
            Bukkit.broadcastMessage("§6§lPyralia §8» §3" + player.getName() + "§7 est mort tout seul !");

        playerDeathEvent.getDrops().clear();
        playerDeathEvent.getDrops().add(new ItemStack(Material.GOLDEN_APPLE, 2));
        playerDeathEvent.getDrops().add(new ItemStack(Material.ARROW, 16));

        player.spigot().respawn();
        Bukkit.getScheduler().runTaskLater(instance, ()->{
            player.teleport(instance.getGameManager().getLobbyLocation());

            player.getInventory().clear();
            ItemStack air = new ItemStack(Material.AIR);
            player.getInventory().setHelmet(air);
            player.getInventory().setChestplate(air);
            player.getInventory().setLeggings(air);
            player.getInventory().setBoots(air);

            player.getInventory().setItem(4, new ItemCreator(Material.DIAMOND_SWORD).name("§8» §7Rentrer dans l'Arène").lore("", "§8» §7Cliquez ici pour rejoindre le combat !").get());

            player.setMaxHealth(20);
            player.setHealth(player.getMaxHealth());

            player.setFoodLevel(20);
        }, 3);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent playerJoinEvent){
        playerJoinEvent.setJoinMessage(null);
        Player player = playerJoinEvent.getPlayer();
        Bukkit.broadcastMessage("§6§lPyralia §8» §a" + player.getName() + "§7 a rejoint l'Arène.");

        Bukkit.getScheduler().runTaskLater(instance, ()->{
            player.teleport(instance.getGameManager().getLobbyLocation());

            player.getInventory().clear();
            ItemStack air = new ItemStack(Material.AIR);
            player.getInventory().setHelmet(air);
            player.getInventory().setChestplate(air);
            player.getInventory().setLeggings(air);
            player.getInventory().setBoots(air);

            player.getInventory().setItem(4, new ItemCreator(Material.DIAMOND_SWORD).name("§8» §7Rentrer dans l'Arène").lore("", "§8» §7Cliquez ici pour rejoindre le combat !").get());
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

            player.setFoodLevel(20);
        }, 3);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent playerQuitEvent){
        playerQuitEvent.setQuitMessage(null);
        Player player = playerQuitEvent.getPlayer();

        Bukkit.broadcastMessage("§6§lPyralia §8» §c" + player.getName() + "§7 a quitté l'Arène.");
    }


}
