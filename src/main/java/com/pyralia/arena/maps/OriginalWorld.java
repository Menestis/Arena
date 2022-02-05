package com.pyralia.arena.maps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ariloxe
 */
public class OriginalWorld extends SpecialWorld {

    public OriginalWorld() {
        super("§b§lOriginal (2017)", new Location(Bukkit.getWorld("originalArena"), -57, 158, -5), "Originale");

        List<Location> spawnLocations = new ArrayList<>();
        World world = Bukkit.getWorld("originalArena");
        spawnLocations.add(new Location(world, -47, 12, -14));
        spawnLocations.add(new Location(world, -16, 11, 25));
        spawnLocations.add(new Location(world, -53, 10, 30));
        spawnLocations.add(new Location(world, -111, 12, 21));
        spawnLocations.add(new Location(world, -113, 12, -20));

        super.setArenaLocations(spawnLocations);
    }



}
