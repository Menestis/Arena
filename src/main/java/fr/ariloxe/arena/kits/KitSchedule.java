package fr.ariloxe.arena.kits;

import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.player.KPlayer;
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
                    for (KPlayer kPlayer : new ArrayList<>(kPlayerRemainsList)){
                        if (playerIntegerMap.get(kPlayer) > 0)
                            playerIntegerMap.replace(kPlayer, playerIntegerMap.get(kPlayer), playerIntegerMap.get(kPlayer) - 1);
                        else {
                            kPlayer.sendMessage("§3§lMenestis §f» §aVous pouvez de nouveau utiliser le pouvoir du kit " + name);
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
            kPlayer.sendMessage("§3§lMenestis §f» §7Merci d'attendre §c" + playerIntegerMap.get(kPlayer) + " §7secondes.");
            return;
        }

        power(kPlayer);
        kPlayerRemainsList.add(kPlayer);
        playerIntegerMap.put(kPlayer, this.secondsDelay);
    }

    public void setSecondsDelay(int secondsDelay) {
        this.secondsDelay = secondsDelay;
    }

    public int getSecondsDelay() {
        return secondsDelay;
    }

    public List<KPlayer> getkPlayerRemainsList() {
        return kPlayerRemainsList;
    }

    public Map<KPlayer, Integer> getPlayerIntegerMap() {
        return playerIntegerMap;
    }
}
