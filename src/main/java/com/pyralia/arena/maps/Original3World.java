package com.pyralia.arena.maps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ariloxe
 */
public class Original3World extends SpecialWorld {

    public Original3World() {
        super("§3§lOriginal (2021)", new Location(Bukkit.getWorld("originalV3WorldArena"), -57, 158, -5), "OriginaleV3");

        List<Location> spawnLocations = new ArrayList<>();
        World world = Bukkit.getWorld("originalV3WorldArena");
        spawnLocations.add(new Location(world, 972.8, 81, -1066.9, -22, 0));
        spawnLocations.add(new Location(world, 950.8, 81, -1024.1, -75, 0));
        spawnLocations.add(new Location(world, 1028.6, 82, -954.244, 145, 0));
        spawnLocations.add(new Location(world, 1064.7, 85, -991.3, 100, 0));
        spawnLocations.add(new Location(world, 993.0, 81, -1010.8, -90, 0));

        super.setArenaLocations(spawnLocations);
    }



}
