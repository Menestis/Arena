package com.pyralia.arena;

import com.pyralia.api.PyraliaAPI;
import com.pyralia.api.tech.mongo.MongoProfileCreator;
import com.pyralia.api.utils.java.CommandUtils;
import com.pyralia.arena.commands.CarteCommand;
import com.pyralia.arena.commands.VoteCommand;
import com.pyralia.arena.listeners.CombatLog;
import com.pyralia.arena.listeners.PlayersListener;
import com.pyralia.arena.listeners.PowerListeners;
import com.pyralia.arena.manager.*;
import com.pyralia.arena.player.KPlayer;
import com.pyralia.arena.utils.mongo.ArenaCollection;
import com.pyralia.arena.utils.scoreboard.ScoreboardManager;
import com.pyralia.arena.utils.FileUtils;
import fr.blendman974.kinventory.KInventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
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

        File worldFolderOriginal = new File(worldContainer, "originalArena");
        File copyFolderOriginal = new File(worldContainer, "originalWorld");

        File cityWorldFolder = new File(worldContainer, "cityWorldArena");
        File copyCityWorldFolder = new File(worldContainer, "cityWorld");

        File originalv3Folder = new File(worldContainer, "originalV3WorldArena");
        File copyOriginalv3WorldFolder = new File(worldContainer, "originalV3World");

        File originalv2Folder = new File(worldContainer, "originalV2WorldArena");
        File copyOriginalv2WorldFolder = new File(worldContainer, "originalV2World");

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

        if(copyFolderOriginal.exists()){
            Bukkit.unloadWorld("originalArena", false);
            FileUtils.delete(worldFolderOriginal);
            try {
                FileUtils.copyFolder(copyFolderOriginal, worldFolderOriginal);
                new WorldCreator("originalArena").createWorld();
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

        if(copyCityWorldFolder.exists()){
            Bukkit.unloadWorld("world", false);
            FileUtils.delete(cityWorldFolder);
            try {
                FileUtils.copyFolder(copyCityWorldFolder, cityWorldFolder);
                new WorldCreator("cityWorldArena").createWorld();
                getLogger().info("World copied.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(copyOriginalv3WorldFolder.exists()){
            Bukkit.unloadWorld("originalV3WorldArena", false);
            FileUtils.delete(originalv3Folder);
            try {
                FileUtils.copyFolder(copyOriginalv3WorldFolder, originalv3Folder);
                new WorldCreator("originalV3WorldArena").createWorld();
                getLogger().info("World copied.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(copyOriginalv2WorldFolder.exists()){
            Bukkit.unloadWorld("originalV2WorldArena", false);
            FileUtils.delete(originalv2Folder);
            try {
                FileUtils.copyFolder(copyOriginalv2WorldFolder, originalv2Folder);
                new WorldCreator("originalV2WorldArena").createWorld();
                getLogger().info("World copied.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        new WorldCreator("Spawn").createWorld();

        this.scheduledExecutorService = Executors.newScheduledThreadPool(16);
        this.executorMonoThread = Executors.newScheduledThreadPool(1);

        this.gameManager = new GameManager();
        this.kitManager = new KitManager();
        this.perksManager = new PerksManager();
        this.guiManager = new GuiManager(this);

        new TaskManager();
        PyraliaAPI.getInstance().getMongoManager().registerObject(ArenaCollection.class);

        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new PlayersListener(this), this);
        pluginManager.registerEvents(new ScoreboardManager(this), this);
        pluginManager.registerEvents(new PowerListeners(), this);
        pluginManager.registerEvents(new CombatLog(), this);

        CommandUtils.registerCommand("arena", new CarteCommand());
        CommandUtils.registerCommand("arena", new VoteCommand());

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

            if(PyraliaAPI.getInstance().getMongoManager().contains(ArenaCollection.class, uuid.toString())){
                kills = (int)PyraliaAPI.getInstance().getMongoManager().getFromCollection(ArenaCollection.class, player.getUniqueId(), "kills");
                deaths = (int)PyraliaAPI.getInstance().getMongoManager().getFromCollection(ArenaCollection.class, player.getUniqueId(), "deaths");
            } else {
                final MongoProfileCreator mongoProfileCreator = new MongoProfileCreator(uuid.toString()).addEntry("uuid", uuid.toString()).addEntry("kills", 0).addEntry("deaths", 0);
                PyraliaAPI.getInstance().getMongoManager().createProfile(ArenaCollection.class, mongoProfileCreator);
            }


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
