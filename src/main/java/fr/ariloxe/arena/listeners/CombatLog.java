package fr.ariloxe.arena.listeners;

import fr.ariloxe.arena.utils.packets.ActionBar;
import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.player.KPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import java.util.HashMap;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Ariloxe
 */
public class CombatLog implements Listener {

    public static HashMap<Player, Integer> inCombat = new HashMap<>();

    public static boolean isInCombat(Player p) {
        return inCombat.containsKey(p);
    }

    public static int getInCombat(Player p) {
        return inCombat.get(p);
    }

    public static void addInCombat(Player p, int seconds) {
        inCombat.put(p, Integer.valueOf(seconds));
    }

    public static void removeInCombat(Player p) {
        inCombat.remove(p);
    }

    @EventHandler(ignoreCancelled = true)
    public void onAttack(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player) || !(e.getDamager() instanceof Player))
            return;

        Player victime = (Player)e.getEntity();
        Player killer = (Player)e.getDamager();

        if (isInCombat(victime))
            addInCombat(victime, 15);
        else
            startCooldownTimer(victime, 15);

        if (isInCombat(killer))
            addInCombat(killer, 15);
        else
            startCooldownTimer(killer, 15);

    }


    public void startCooldownTimer(final Player p, int seconds) {
        final KPlayer up = ArenaAPI.getkPlayer(p);
        new ActionBar("§cVous êtes en combat - Ne vous déconnectez pas.").sendToPlayer(p);

        addInCombat(p, seconds);
        (new BukkitRunnable() {
            public void run() {
                if (CombatLog.inCombat.get(p) == null) {
                    cancel();
                    return;
                }
                if (CombatLog.getInCombat(p) > 0) {
                    CombatLog.addInCombat(p, CombatLog.getInCombat(p) - 1);
                    new ActionBar("§cVous êtes en combat - Ne vous déconnectez pas. (" + CombatLog.getInCombat(p) + ")").sendToPlayer(p);
                } else {
                    new ActionBar("§cVous n'êtes plus en combat.").sendToPlayer(p);
                    CombatLog.removeInCombat(p);
                    cancel();
                }
            }
        }).runTaskTimer(ArenaAPI.getApi(), 20L, 20L);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        KPlayer upPlayer = ArenaAPI.getkPlayer(p);
        if (isInCombat(p) && upPlayer.getBukkitPlayer().getLocation().getY() < 110 && !e.getQuitMessage().contains("Timed Out")) {
            Bukkit.broadcastMessage("§c[!] " + upPlayer.getBukkitPlayer().getName() + " vient de se déconnecter en combat !");
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        if (isInCombat(p)) {
            removeInCombat(p);
            if (p.getKiller() != null && isInCombat(p.getKiller()))
                removeInCombat(p.getKiller());

        }
    }

}
