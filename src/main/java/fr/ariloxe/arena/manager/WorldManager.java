package fr.ariloxe.arena.manager;

import fr.ariloxe.arena.maps.*;
import fr.ariloxe.arena.utils.PlayerUtils;
import fr.ariloxe.arena.utils.skull.SkullList;
import fr.ariloxe.arena.maps.*;
import fr.ariloxe.arena.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ariloxe
 */
public class WorldManager {

    private final List<SpecialWorld> specialWorlds = new ArrayList<>();
    private final GameManager gameManager;
    private final Location lobbyLocation;

    public WorldManager(GameManager gameManager, ForestWorld forestWorld){
        specialWorlds.add(forestWorld);
        specialWorlds.add(new NakimeWorld());
        specialWorlds.add(new NakimeV2World());
        specialWorlds.add(new EnferWorld());
        specialWorlds.add(new OriginalWorld());
        specialWorlds.add(new Original2World());
        specialWorlds.add(new Original3World());
        specialWorlds.add(new CityWorld());
        specialWorlds.add(new Naruto());

        this.gameManager = gameManager;
        this.lobbyLocation = new Location(Bukkit.getWorld("Spawn"), 190.354, 126, -198.449, 47, 0);
    }

    public Location getLobbyLocation() {
        return lobbyLocation;
    }

    public List<SpecialWorld> getSpecialWorlds() {
        return specialWorlds;
    }
    public void switchWorld(SpecialWorld specialWorld){
        if(gameManager.getSpecialWorld() == specialWorld)
            return;

        gameManager.setSpecialWorld(specialWorld);
        Bukkit.broadcastMessage("§3§lMenestis §f» §7Le monde de l'arène vient de changer: " + specialWorld.getName());
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));

            player.setMaxHealth(20);
            player.setHealth(player.getMaxHealth());
            player.setAllowFlight(false);

            player.setFoodLevel(20);

            player.teleport(getLobbyLocation());

            PlayerUtils.giveDefaultInventory(player);
        });
    }


}
