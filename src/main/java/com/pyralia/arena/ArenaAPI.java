package com.pyralia.arena;

import com.mongodb.BasicDBObject;
import com.pyralia.arena.commands.CarteCommand;
import com.pyralia.arena.listeners.PlayersListener;
import com.pyralia.arena.listeners.PowerListeners;
import com.pyralia.arena.listeners.task.TabTask;
import com.pyralia.arena.manager.*;
import com.pyralia.arena.player.KPlayer;
import com.pyralia.arena.utils.scoreboard.ScoreboardManager;
import com.pyralia.arena.utils.FileUtils;
import com.pyralia.arena.utils.mongo.DatabaseManager;
import com.pyralia.core.spigot.utils.CommandUtils;
import fr.blendman974.kinventory.KInventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
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

public final class ArenaAPI extends JavaPlugin {

    private static ArenaAPI instance;

    private GameManager gameManager;
    private KitManager kitManager;
    private GuiManager guiManager;
    private PerksManager perksManager;

    private ScheduledExecutorService executorMonoThread;
    private ScheduledExecutorService scheduledExecutorService;

    private DatabaseManager databaseManager;

    @Override
    public void onLoad(){
        instance = this;

        File worldContainer = this.getServer().getWorldContainer();
        File worldFolder = new File(worldContainer, "world");
        File copyFolder = new File(worldContainer, "Arena");

        if(copyFolder.exists()){
            Bukkit.unloadWorld("nakimeArena", false);
            FileUtils.delete(worldFolder);
            try {
                FileUtils.copyFolder(copyFolder, worldFolder);
                getLogger().info("World copied.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    public void onEnable() {
        instance = this;
        KInventoryManager.init(this);

        File worldContainer = this.getServer().getWorldContainer();

        File worldFolderNaki = new File(worldContainer, "nakimeArena");
        File copyFolderNaki = new File(worldContainer, "nakimeWorld");

        File worldFolderEnfer = new File(worldContainer, "enferArena");
        File copyFolderEnfer = new File(worldContainer, "enferWorld");
        if(copyFolderNaki.exists()){
            Bukkit.unloadWorld("nakimeArena", false);
            FileUtils.delete(worldFolderNaki);
            try {
                FileUtils.copyFolder(copyFolderNaki, worldFolderNaki);
                new WorldCreator("nakimeArena").createWorld();
                getLogger().info("World copied.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(copyFolderEnfer.exists()){
            Bukkit.unloadWorld("world", false);
            FileUtils.delete(worldFolderEnfer);
            try {
                FileUtils.copyFolder(copyFolderEnfer, worldFolderEnfer);
                new WorldCreator("enferArena").createWorld();
                getLogger().info("World copied.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        this.scheduledExecutorService = Executors.newScheduledThreadPool(16);
        this.executorMonoThread = Executors.newScheduledThreadPool(1);

        this.databaseManager = new DatabaseManager();

        this.gameManager = new GameManager();
        this.kitManager = new KitManager();
        this.perksManager = new PerksManager();
        this.guiManager = new GuiManager(this);

        new TaskManager();

        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new PlayersListener(this), this);
        pluginManager.registerEvents(new ScoreboardManager(this), this);
        pluginManager.registerEvents(new PowerListeners(), this);

        CommandUtils.registerCommand("arena", new CarteCommand());
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
            int kills = 0;
            int deaths = 0;

            if(getApi().getDatabaseManager().getArenaCollection().find(new BasicDBObject("uuid", uuid.toString())).one() != null){
                kills = (int) getApi().getDatabaseManager().getFromArenaCollection(player.getUniqueId(), "kills");
                deaths = (int) getApi().getDatabaseManager().getFromArenaCollection(player.getUniqueId(), "deaths");
            } else
                getApi().getDatabaseManager().createProfile(player.getUniqueId());


            KPlayer kPlayer = new KPlayer(uuid, player.getName(), kills, deaths);
            kPlayer.setKit(getApi().getKitManager().getDefaultKit());
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

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public PerksManager getPerksManager() {
        return perksManager;
    }

    public ScheduledExecutorService getExecutorMonoThread() {
        return this.executorMonoThread;
    }
    public ScheduledExecutorService getScheduledExecutorService() {
        return this.scheduledExecutorService;
    }

    public static ArenaAPI getApi() {
        return instance;
    }
}
