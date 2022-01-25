package com.pyralia.arena.listeners.task;

import com.pyralia.arena.ArenaAPI;
import com.pyralia.arena.kits.release.AkazaKit;
import com.pyralia.arena.player.KPlayer;
import com.pyralia.arena.utils.ActionBar;
import com.pyralia.arena.utils.PlayerUtils;
import fr.ariloxe.api.utils.DirectionnalArrow;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Player;

/**
 * @author Ariloxe
 */
public class RegularTask extends BukkitRunnable {

    public RegularTask() {
        runTaskTimer(ArenaAPI.getApi(), 20, 7);
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            ((CraftPlayer) player).getHandle().getDataWatcher().watch(9, (byte) 0);

            if(player.getLocation().getBlockY() < 0){
                player.damage(40);
                player.sendMessage("§cNe vous éloignez pas trop :o");
                return;
            }

            KPlayer kPlayer = ArenaAPI.getkPlayer(player);
            if(kPlayer.getKit() instanceof AkazaKit){
                Player target = PlayerUtils.getNearestPlayer(player);
                ActionBar actionBar;
                if(target == null)
                    actionBar = new ActionBar("§cAucun joueur ne se trouve à proximité...");
                else
                    actionBar = new ActionBar("§f" + target.getName() + " §l" + DirectionnalArrow.fleche(DirectionnalArrow.angle(player, target.getLocation())) + " §8(§f" + DirectionnalArrow.distance(player.getLocation(), target.getLocation()) + "m§8)");
                actionBar.sendToPlayer(player);
            }

        });
    }
}
