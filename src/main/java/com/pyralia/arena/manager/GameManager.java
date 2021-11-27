package com.pyralia.arena.manager;

import com.pyralia.core.common.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Ariloxe
 */
public class GameManager {

    private Location lobbyLocation = new Location(Bukkit.getWorld("world"), -80, 114, -80);
    private List<Location> locationList = new ArrayList<>();


    public GameManager(){
        World world = Bukkit.getWorld("world");

        locations.add(new Location(world, -58, 57, -115));
        locations.add(new Location(world, -43, 53, -33));
        locations.add(new Location(world, -122, 56, -22));
        locations.add(new Location(world, -18, 56, -47));
        locations.add(new Location(world, -117, 55, -39));
    }

    public List<Location> getLocationList() {
        return locationList;
    }

    public Location getLobbyLocation() {
        return lobbyLocation;
    }

    private List<Location> locations = new ArrayList<>();

    public void joinArena(Player player){
        player.getInventory().setItem(0, new ItemCreator(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, 3).get());
        player.getInventory().setItem(1, new ItemCreator(Material.FISHING_ROD).get());
        player.getInventory().setItem(2, new ItemStack(Material.GOLDEN_APPLE, 9));
        player.getInventory().setItem(3, new ItemCreator(Material.DIAMOND_PICKAXE).enchant(Enchantment.DIG_SPEED, 3).get());
        player.getInventory().setItem(4, new ItemStack(Material.WATER_BUCKET));
        player.getInventory().setItem(5, new ItemStack(Material.COBBLESTONE, 64));
        player.getInventory().setItem(6, new ItemStack(Material.ARROW, 32));
        player.getInventory().setItem(7, new ItemCreator(Material.BOW).enchant(Enchantment.ARROW_DAMAGE, 2).get());
        player.getInventory().setItem(8, new ItemStack(Material.COBBLESTONE, 64));

        player.getInventory().setHelmet(new ItemCreator(Material.DIAMOND_HELMET).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).get());
        player.getInventory().setChestplate(new ItemCreator(Material.DIAMOND_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).get());
        player.getInventory().setLeggings(new ItemCreator(Material.IRON_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).get());
        player.getInventory().setBoots(new ItemCreator(Material.DIAMOND_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).get());

        player.teleport(locations.get(new Random().nextInt(locations.size())));

    }


}
