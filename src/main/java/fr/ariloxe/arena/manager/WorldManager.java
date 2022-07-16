package fr.ariloxe.arena.manager;

import fr.ariloxe.arena.maps.*;
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

    private List<SpecialWorld> specialWorlds = new ArrayList<>();
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
            player.getInventory().clear();
            ItemStack air = new ItemStack(Material.AIR);
            player.getInventory().setHelmet(air);
            player.getInventory().setChestplate(air);
            player.getInventory().setLeggings(air);
            player.getInventory().setBoots(air);

            player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));

            //   player.getInventory().setItem(2, new ItemCreator(SkullList.CAMERA.getItemStack()).name("§e§lPack de Texture §8§l▪ §7§lClic-droit").lore("", "§8» §7Cliquez ici pour télécharger le pack !").get());
            player.getInventory().setItem(4, new ItemCreator(Material.ENDER_PORTAL_FRAME).name("§a§lChoisir son Kit §8§l▪ §7§lClic-droit").lore("", "§8» §7Cliquez ici pour choisir un kit !").get());
            player.getInventory().setItem(8, new ItemCreator(Material.BED).name("§c§lRetourner au Lobby §8§l▪ §7§lClic-droit").lore("", "§8» §7Cliquez ici pour retourner au lobby !").get());

            player.setMaxHealth(20);
            player.setHealth(player.getMaxHealth());
            player.setAllowFlight(false);

            player.setFoodLevel(20);

            player.teleport(getLobbyLocation());
        });
    }


}
