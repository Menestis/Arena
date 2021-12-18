package com.pyralia.arena;

import com.pyralia.arena.listeners.PlayersListener;
import com.pyralia.arena.listeners.PowerListeners;
import com.pyralia.arena.manager.GameManager;
import com.pyralia.arena.manager.GuiManager;
import com.pyralia.arena.manager.KitManager;
import com.pyralia.arena.player.KPlayer;
import com.pyralia.arena.scoreboard.ScoreboardManager;
import com.pyralia.arena.utils.FileUtils;
import fr.blendman974.kinventory.KInventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public final class Main extends JavaPlugin {

    private static Main instance;

    private GameManager gameManager;
    private KitManager kitManager;
    private GuiManager guiManager;

    private ScheduledExecutorService executorMonoThread;
    private ScheduledExecutorService scheduledExecutorService;


    @Override
    public void onLoad(){
        instance = this;

        File worldContainer = this.getServer().getWorldContainer();
        File worldFolder = new File(worldContainer, "world");
        File copyFolder = new File(worldContainer, "Arena");

        if(!copyFolder.exists()) {
            getLogger().info("Can't find copied world 'world_save'");
            return;
        }

        Bukkit.unloadWorld("world", false);
        FileUtils.delete(worldFolder);
        try {
            FileUtils.copyFolder(copyFolder, worldFolder);
            getLogger().info("World copied.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onEnable() {
        instance = this;
        KInventoryManager.init(this);

        this.scheduledExecutorService = Executors.newScheduledThreadPool(16);
        this.executorMonoThread = Executors.newScheduledThreadPool(1);

        this.gameManager = new GameManager();
        this.kitManager = new KitManager();
        this.guiManager = new GuiManager(this);

        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new PlayersListener(this), this);
        pluginManager.registerEvents(new ScoreboardManager(this), this);
        pluginManager.registerEvents(new PowerListeners(), this);

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

    private static Map<UUID, KPlayer> uuidkPlayerMap = new HashMap<>();

    public static void registerPlayer(Player player){
        UUID uuid = player.getUniqueId();
        if(!uuidkPlayerMap.containsKey(uuid)){
            KPlayer kPlayer = new KPlayer(uuid, player.getName());
            kPlayer.setKit(getInstance().getKitManager().getDefaultKit());
            uuidkPlayerMap.put(uuid, kPlayer);
        }
    }

    public static KPlayer getkPlayer(Player player){
        return uuidkPlayerMap.get(player.getUniqueId());
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }

    public KitManager getKitManager() {
        return kitManager;
    }

    public ScheduledExecutorService getExecutorMonoThread() {
        return this.executorMonoThread;
    }
    public ScheduledExecutorService getScheduledExecutorService() {
        return this.scheduledExecutorService;
    }

    public static Main getInstance() {
        return instance;
    }
}
