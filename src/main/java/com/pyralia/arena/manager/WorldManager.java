package com.pyralia.arena.manager;

import com.pyralia.arena.maps.EnferWorld;
import com.pyralia.arena.maps.ForestWorld;
import com.pyralia.arena.maps.NakimeWorld;
import com.pyralia.arena.maps.SpecialWorld;
import com.pyralia.core.common.ItemCreator;
import org.bukkit.Bukkit;
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

    public WorldManager(GameManager gameManager, ForestWorld forestWorld){
        specialWorlds.add(forestWorld);
        specialWorlds.add(new NakimeWorld());
        specialWorlds.add(new EnferWorld());

        this.gameManager = gameManager;
    }

    public List<SpecialWorld> getSpecialWorlds() {
        return specialWorlds;
    }

    public void switchWorld(SpecialWorld specialWorld){
        if(gameManager.getSpecialWorld() == specialWorld)
            return;

        gameManager.setSpecialWorld(specialWorld);
        Bukkit.broadcastMessage("§6§lPyralia §8§l» §7Le monde de l'arène vient de changer: " + specialWorld.getName());
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.getInventory().clear();
            ItemStack air = new ItemStack(Material.AIR);
            player.getInventory().setHelmet(air);
            player.getInventory().setChestplate(air);
            player.getInventory().setLeggings(air);
            player.getInventory().setBoots(air);

            player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
            player.getInventory().setItem(4, new ItemCreator(Material.ENDER_PORTAL_FRAME).name("§8» §7Choisir un Kit").lore("", "§8» §7Cliquez ici pour choisir un kit !").get());

            player.setMaxHealth(20);
            player.setHealth(player.getMaxHealth());
            player.setAllowFlight(false);

            player.setFoodLevel(20);

            player.teleport(specialWorld.getLobbyLocation());
        });
    }


}