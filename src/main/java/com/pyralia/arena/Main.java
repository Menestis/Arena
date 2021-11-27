package com.pyralia.arena;

import com.pyralia.arena.listeners.PlayersListener;
import com.pyralia.arena.manager.GameManager;
import com.pyralia.arena.scoreboard.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public final class Main extends JavaPlugin {

    private GameManager gameManager;

    private ScheduledExecutorService executorMonoThread;
    private ScheduledExecutorService scheduledExecutorService;

    @Override
    public void onEnable() {
        this.scheduledExecutorService = Executors.newScheduledThreadPool(16);
        this.executorMonoThread = Executors.newScheduledThreadPool(1);

        this.gameManager = new GameManager();
        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new PlayersListener(this), this);
        pluginManager.registerEvents(new ScoreboardManager(this), this);

        getServer().getScheduler().runTaskTimer(this, ()->{
            if(!Bukkit.getOnlinePlayers().isEmpty())
                Bukkit.getOnlinePlayers().forEach(player -> ((CraftPlayer) player).getHandle().getDataWatcher().watch(9, (byte) 0));
        }, 20, 20);

        getServer().getScheduler().runTaskTimer(this, ()->{
            Bukkit.broadcastMessage("§6§lPyralia §8» §7Tous les blocs et entitées vont être retirés dans §c30§7 secondes.");
        }, 20, 290*20);

        getServer().getScheduler().runTaskTimer(this, ()->{
            Bukkit.broadcastMessage("§6§lPyralia §8» §7Tous les blocs et entitées ont été retirés !");
            if(!Bukkit.getWorld("world").getEntities().isEmpty())
                Bukkit.getWorld("world").getEntities().stream().filter(entity -> !(entity instanceof Player)).forEach(Entity::remove);
            if(!getGameManager().getLocationList().isEmpty())
                getGameManager().getLocationList().forEach(location -> location.getBlock().setType(Material.AIR));
        }, 20, 20*60*5);

    }

    @Override
    public void onDisable() {
        if(!getGameManager().getLocationList().isEmpty())
            getGameManager().getLocationList().forEach(location -> location.getBlock().setType(Material.AIR));
    }

    public GameManager getGameManager() {
        return gameManager;
    }


    public ScheduledExecutorService getExecutorMonoThread() {
        return this.executorMonoThread;
    }
    public ScheduledExecutorService getScheduledExecutorService() {
        return this.scheduledExecutorService;
    }

}
