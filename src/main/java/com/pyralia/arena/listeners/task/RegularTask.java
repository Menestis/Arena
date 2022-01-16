package com.pyralia.arena.listeners.task;

import com.pyralia.arena.ArenaAPI;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Ariloxe
 */
public class RegularTask extends BukkitRunnable {

    public RegularTask() {
        runTaskTimer(ArenaAPI.getApi(), 20, 20);
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().stream().filter(player -> player.getLocation().getBlockY() < 0).forEach(player -> {
            player.damage(40);
            player.sendMessage("§cNe vous éloignez pas trop :o");
        });
        if(!Bukkit.getOnlinePlayers().isEmpty())
            Bukkit.getOnlinePlayers().forEach(player -> ((CraftPlayer) player).getHandle().getDataWatcher().watch(9, (byte) 0));
    }
}
