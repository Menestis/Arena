package com.pyralia.arena.listeners.task;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Sorts;
import com.pyralia.api.PyraliaAPI;
import com.pyralia.api.player.ICorePlayer;
import com.pyralia.api.utils.holograms.Hologram;
import com.pyralia.arena.ArenaAPI;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.*;

/**
 * @author Ariloxe
 */
public class LeaderboardTask {

    private final Location location = new Location(Bukkit.getWorld("Spawn"), 180, 126, -191);
    private final Map<UUID, Integer> killTop = new HashMap<>();
    private static Hologram hologram;

    public LeaderboardTask(){
        Bukkit.getScheduler().runTaskTimer(ArenaAPI.getApi(), this::statsWatchers, 100, 20*60*10);
    }

    private void statsWatchers(){
        if(hologram != null){
            hologram.getEntitylist().forEach(entityArmorStand -> entityArmorStand.getBukkitEntity().remove());
            Bukkit.getOnlinePlayers().forEach(player -> hologram.hideHologram(player));
        }

        final int[] place = {1};
        List<String> strings = new ArrayList<>();
        strings.add("§f(§6⚔§f) §e§lClassement des Kills §f(§6⚔§f)");

        for (final Document document : getAllStats().find().sort(Sorts.descending("kills")).limit(10)){
            UUID uuid = UUID.fromString(document.getString("uuid"));
            ICorePlayer iCorePlayer = PyraliaAPI.getInstance().getPlayerManager().loadPlayer(uuid);
            if (iCorePlayer != null)
                strings.add("§6#" + place[0] + " " + iCorePlayer.getName() + " §8- §e§l" + document.getInteger("kills")  + "");
            else
                strings.add("§6#" + place[0] + " §cCompte introuvable.. §8- §e§l" + document.getInteger("kills") + "");

            place[0]++;
        }
        strings.add("§7§oMise à jour toutes les §e§o10§7§o minutes...");

        hologram = new Hologram(strings, location);
        Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), ()-> Bukkit.getOnlinePlayers().forEach(player -> hologram.showHologram(player)), 30);
    }

    private MongoCollection<Document> getAllStats() {
        return PyraliaAPI.getInstance().getMongoManager().getMongoClient().getDatabase("Pyralia").getCollection("arenaStats");
    }

    public static Hologram getHologram() {
        return hologram;
    }
}