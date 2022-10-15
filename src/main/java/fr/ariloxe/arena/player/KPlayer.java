package fr.ariloxe.arena.player;

import fr.ariloxe.arena.kits.Kit;
import fr.ariloxe.arena.utils.inventory.PlayerInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Ariloxe
 */
public class KPlayer {

    private final UUID uuid;
    private final String name;

    private boolean damageable = true;
    private Kit kit;
    private PlayerInventory playerInventory;


    public KPlayer(UUID uuid, String name, int kills, int deaths) {
        this.uuid = uuid;
        this.name = name;
    }

    public Player getBukkitPlayer(){
        return Bukkit.getPlayer(uuid);
    }

    public PlayerInventory getPlayerInventory() {
        return playerInventory;
    }

    public void setPlayerInventory(PlayerInventory playerInventory) {
        this.playerInventory = playerInventory;
    }

    public void sendMessage(String message){
        if(getBukkitPlayer() != null)
            getBukkitPlayer().sendMessage(message);

    }

    public Kit getKit() {
        return kit;
    }

    public void setKit(Kit kit) {
        this.kit = kit;
    }

    public void setDamageable(boolean damageable) {
        this.damageable = damageable;
    }

    public boolean isDamageable() {
        return damageable;
    }
}
