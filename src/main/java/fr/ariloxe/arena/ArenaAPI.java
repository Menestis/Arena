package fr.ariloxe.arena;

import fr.ariloxe.arena.commands.ArenaCommand;
import fr.ariloxe.arena.manager.GuiManager;
import fr.ariloxe.arena.manager.TaskManager;
import fr.ariloxe.arena.utils.CommandUtils;
import fr.ariloxe.arena.utils.Tasks;
import fr.ariloxe.arena.commands.CarteCommand;
import fr.ariloxe.arena.commands.VoteCommand;
import fr.ariloxe.arena.listeners.CombatLog;
import fr.ariloxe.arena.listeners.PlayersListener;
import fr.ariloxe.arena.listeners.PowerListeners;
import fr.ariloxe.arena.player.KPlayer;
import fr.ariloxe.arena.utils.ZipUtils;
import fr.ariloxe.arena.utils.scoreboard.ScoreboardManager;
import fr.ariloxe.arena.manager.GameManager;
import fr.ariloxe.arena.manager.KitManager;
import fr.blendman.magnet.api.MagnetApi;
import fr.blendman.magnet.api.server.ServerCacheHandler;
import fr.blendman974.kinventory.KInventoryManager;
import fr.menestis.commons.bukkit.moderation.ModerationManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.file.Paths;
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

    private ScheduledExecutorService executorMonoThread;
    private ScheduledExecutorService scheduledExecutorService;

    @Override
    public void onLoad(){
        instance = this;
    }


    @Override
    public void onEnable() {
        instance = this;
        KInventoryManager.init(this);


//        try {
//            ZipUtils.unzip(Paths.get("Arenas.zip"), Bukkit.getWorldContainer().toPath());
//            ZipUtils.unzip(Paths.get("Spawn.zip"), Bukkit.getWorldContainer().toPath());
//        } catch (IOException e){
//            e.printStackTrace();
//        }

        new WorldCreator("Spawn").createWorld();
        new WorldCreator("arenas").createWorld();


        this.scheduledExecutorService = Executors.newScheduledThreadPool(16);
        this.executorMonoThread = Executors.newScheduledThreadPool(1);

        this.gameManager = new GameManager();
        this.kitManager = new KitManager();
        this.guiManager = new GuiManager(this);

        new TaskManager();

        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new PlayersListener(this), this);
        pluginManager.registerEvents(new ScoreboardManager(this), this);
        pluginManager.registerEvents(new PowerListeners(), this);
        pluginManager.registerEvents(new CombatLog(), this);

        CommandUtils.registerCommand("arena", new CarteCommand());
        CommandUtils.registerCommand("arena", new VoteCommand());
        CommandUtils.registerCommand("arena", new ArenaCommand());

        ModerationManager.getInstance().init(this);

        informMagnetReady();

        Tasks.runLater(()-> {
            Bukkit.getWorlds().forEach(world -> world.setGameRuleValue("doFireTick", "false"));
        }, 20*10);
    }

    @Override
    public void onDisable() {
        if(!getGameManager().getLocationList().isEmpty())
            getGameManager().getLocationList().forEach(location -> location.getBlock().setType(Material.AIR));
    }

    private final static Map<UUID, KPlayer> uuidkPlayerMap = new HashMap<>();

    public static void registerPlayer(Player player){
        UUID uuid = player.getUniqueId();
        if(!uuidkPlayerMap.containsKey(uuid)){
            int kills = 0;
            int deaths = 0;

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


    public ScheduledExecutorService getExecutorMonoThread() {
        return this.executorMonoThread;
    }
    public ScheduledExecutorService getScheduledExecutorService() {
        return this.scheduledExecutorService;
    }

    public static ArenaAPI getApi() {
        return instance;
    }

    private void informMagnetReady() {
        if (MagnetApi.MagnetStore.getApi() != null) {
            ServerCacheHandler.ServerCacheHandlerStore.getServerCacheHandler().setServerState("Waiting").thenAccept(unused -> getLogger().info("Server is now waiting for players !")).exceptionally(throwable -> {
                throwable.printStackTrace();
                return null;
            }).exceptionally(throwable -> {
                throwable.printStackTrace();
                return null;
            });
        } else {
            getLogger().info("Magnet not running, state waiting was not propagated");
        }
    }

}
