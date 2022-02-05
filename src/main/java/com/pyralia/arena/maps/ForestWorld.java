package com.pyralia.arena.maps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ariloxe
 */
public class ForestWorld extends SpecialWorld {

    public ForestWorld() {
        super("§a§lForêt", new Location(Bukkit.getWorld("world"), -80, 114, -80), "Forest");

        List<Location> spawnLocations = new ArrayList<>();
        World world = Bukkit.getWorld("world");
        spawnLocations.add(new Location(world, -58, 57, -115));
        spawnLocations.add(new Location(world, -43, 53, -33));
        spawnLocations.add(new Location(world, -122, 56, -22));
        spawnLocations.add(new Location(world, -18, 56, -47));
        spawnLocations.add(new Location(world, -117, 55, -39));

        super.setArenaLocations(spawnLocations);
    }



}
