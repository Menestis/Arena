package com.pyralia.arena.maps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ariloxe
 */
public class CityWorld extends SpecialWorld {

    public CityWorld() {
        super("§8CityWorld", new Location(Bukkit.getWorld("cityWorldArena"), 108, 166, 157), "CityWorld");

        List<Location> spawnLocations = new ArrayList<>();
        World world = Bukkit.getWorld("cityWorldArena");
        spawnLocations.add(new Location(world, 159, 30, 39));
        spawnLocations.add(new Location(world, 158, 30, -52));
        spawnLocations.add(new Location(world, 69, 30, -141));
        spawnLocations.add(new Location(world, 80, 30, 47));
        spawnLocations.add(new Location(world, 240, 30, 46));

        super.setArenaLocations(spawnLocations);
    }



}
