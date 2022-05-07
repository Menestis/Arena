package com.pyralia.arena.maps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ariloxe
 */
public class Original2World extends SpecialWorld {

    public Original2World() {
        super("§9§lOriginal (2020)", new Location(Bukkit.getWorld("originalV2WorldArena"), -57, 158, -5), "OriginaleV2");

        List<Location> spawnLocations = new ArrayList<>();
        World world = Bukkit.getWorld("originalV2WorldArena");
        spawnLocations.add(new Location(world, 43.9, 20, 16.2));
        spawnLocations.add(new Location(world, -21, 20, -47));
        spawnLocations.add(new Location(world, -68, 20, 6));
        spawnLocations.add(new Location(world, -45, 20, 66));
        spawnLocations.add(new Location(world, 23, 21, 43));

        super.setArenaLocations(spawnLocations);
    }



}
