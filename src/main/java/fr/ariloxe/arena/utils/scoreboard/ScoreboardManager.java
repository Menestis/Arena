package fr.ariloxe.arena.utils.scoreboard;

import fr.ariloxe.arena.ArenaAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScoreboardManager implements Listener {

    public final Map<UUID, PersonalScoreboard> scoreboards;
    public final ScheduledFuture glowingTask;
    public int ipCharIndex;
    public int cooldown;
    private final ArenaAPI hub;

    public ScoreboardManager(ArenaAPI hub) {

        this.hub = hub;

        this.scoreboards = new HashMap<>();
        this.ipCharIndex = 0;
        this.cooldown = 0;

        this.glowingTask = this.hub.getScheduledExecutorService().scheduleAtFixedRate(() -> {
            String ip = this.colorIpAt();

            for (PersonalScoreboard scoreboard : this.scoreboards.values())
                this.hub.getExecutorMonoThread().execute(() -> scoreboard.setLines(ip));
        }, 60, 60, TimeUnit.MILLISECONDS);
    }

    @EventHandler
    public void onLogin(PlayerJoinEvent playerJoinEvent) {
        Player player = playerJoinEvent.getPlayer();
        if (this.scoreboards.containsKey(player.getUniqueId())) {
            return;
        }

        Bukkit.getScheduler().runTaskLater(hub, ()-> this.scoreboards.put(player.getUniqueId(), new PersonalScoreboard(hub, player)), 5);
    }

    @EventHandler
    public void onLogout(PlayerQuitEvent playerQuitEvent) {
        Player player = playerQuitEvent.getPlayer();

        if (this.scoreboards.containsKey(player.getUniqueId())) {
            this.scoreboards.get(player.getUniqueId()).onLogout();
            this.scoreboards.remove(player.getUniqueId());
        }
    }


    private String colorIpAt() {
        String ip = "menestis.fr";

        if (this.cooldown > 0) {
            this.cooldown--;
            return ip;
        }

        StringBuilder formattedIp = new StringBuilder();

        if (this.ipCharIndex > 0) {
            formattedIp.append(ip, 0, this.ipCharIndex - 1);
            formattedIp.append(ChatColor.DARK_AQUA).append(ip.charAt(this.ipCharIndex - 1));
        }
        else
            formattedIp.append(ip, 0, this.ipCharIndex);


        formattedIp.append(ChatColor.WHITE).append(ip.charAt(this.ipCharIndex));

        if (this.ipCharIndex + 1 < ip.length()) {
            formattedIp.append(ChatColor.DARK_AQUA).append(ip.charAt(this.ipCharIndex + 1));

            if (this.ipCharIndex + 2 < ip.length())
                formattedIp.append(ChatColor.AQUA).append(ip.substring(this.ipCharIndex + 2));

            this.ipCharIndex++;
        } else {
            this.ipCharIndex = 0;
            this.cooldown = 20;
        }

        return formattedIp.toString();
    }
}


