package com.pyralia.arena.kits;

import com.pyralia.arena.ArenaAPI;
import com.pyralia.arena.player.KPlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * @author Ariloxe
 */
public abstract class KitSchedule extends Kit {

    private final Map<KPlayer, Integer> playerIntegerMap = new HashMap<>();
    private final List<KPlayer> kPlayerRemainsList = new LinkedList<>();
    private int secondsDelay;

    public KitSchedule(String name, KitType kitType, ItemStack itemStack, String... description) {
        super(name, kitType, itemStack, description);
        new BukkitRunnable() {
            @Override
            public void run() {
                if(!kPlayerRemainsList.isEmpty()){
                    for (KPlayer kPlayer : kPlayerRemainsList){
                        if (playerIntegerMap.get(kPlayer) > 0)
                            playerIntegerMap.replace(kPlayer, playerIntegerMap.get(kPlayer), playerIntegerMap.get(kPlayer) - 1);
                        else {
                            kPlayer.sendMessage("§6§lPyralia §8§l» §aVous pouvez de nouveau utiliser le pouvoir du kit " + name);
                            playerIntegerMap.remove(kPlayer);
                            kPlayerRemainsList.remove(kPlayer);
                        }
                    }
                }
            }
        }.runTaskTimer(ArenaAPI.getApi(), 0, 20);
    }

    public void power(KPlayer kPlayer){
    }

    public void use(KPlayer kPlayer){
        if(this.kPlayerRemainsList.contains(kPlayer)){
            kPlayer.sendMessage("§6§lPyralia §8§l» §7Merci d'attendre §c" + playerIntegerMap.get(kPlayer) + " §7secondes.");
            return;
        }

        power(kPlayer);
        kPlayerRemainsList.add(kPlayer);
        playerIntegerMap.put(kPlayer, this.secondsDelay);
    }

    public void setSecondsDelay(int secondsDelay) {
        this.secondsDelay = secondsDelay;
    }

    public List<KPlayer> getkPlayerRemainsList() {
        return kPlayerRemainsList;
    }

    public Map<KPlayer, Integer> getPlayerIntegerMap() {
        return playerIntegerMap;
    }
}
