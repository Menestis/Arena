package com.pyralia.arena.manager;

import com.pyralia.arena.ArenaAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ariloxe
 */
public class GameManager {

    private final Location lobbyLocation = new Location(Bukkit.getWorld("world"), -80, 114, -80);
    private final List<Location> locationList;


    public GameManager(){
        World world = Bukkit.getWorld("world");
        locationList =  new ArrayList<>();

        locationList.add(new Location(world, -58, 57, -115));
        locationList.add(new Location(world, -43, 53, -33));
        locationList.add(new Location(world, -122, 56, -22));
        locationList.add(new Location(world, -18, 56, -47));
        locationList.add(new Location(world, -117, 55, -39));
    }

    public List<Location> getLocationList() {
        return locationList;
    }

    public Location getLobbyLocation() {
        return lobbyLocation;
    }

    public void joinArena(Player player){
        ArenaAPI.getkPlayer(player).getKit().onEquip(player);
    }

}
