package fr.ariloxe.arena.listeners.task;

import fr.ariloxe.arena.ArenaAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Ariloxe
 */
public class ResetTask extends BukkitRunnable {

    public ResetTask() {
        runTaskTimer(ArenaAPI.getApi(), 20, 20*60*4);
    }

    @Override
    public void run() {
        Bukkit.broadcastMessage("§3§lMenestis §f» §7Tous les blocs et entitées vont être retirés dans §c1§7 minute.");

        Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), ()->{
            Bukkit.broadcastMessage("§3§lMenestis §f» §7Tous les blocs et entitées ont été retirés !");
            if(!Bukkit.getWorld("world").getEntities().isEmpty())
                Bukkit.getWorld("world").getEntities().stream().filter(entity -> !(entity instanceof Player)).forEach(Entity::remove);
            if(!ArenaAPI.getApi().getGameManager().getLocationList().isEmpty())
                ArenaAPI.getApi().getGameManager().getLocationList().forEach(location -> location.getBlock().setType(Material.AIR));
        }, 20*60);

    }
}
