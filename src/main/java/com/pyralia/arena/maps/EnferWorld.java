package com.pyralia.arena.maps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ariloxe
 */
public class EnferWorld extends SpecialWorld {

    public EnferWorld() {
        super("§4§lEnfer", new Location(Bukkit.getWorld("enferArena"), 108, 166, 157));

        List<Location> spawnLocations = new ArrayList<>();
        World world = Bukkit.getWorld("enferArena");
        spawnLocations.add(new Location(world, 121, 41, 146));
        spawnLocations.add(new Location(world, 87, 40, 201));
        spawnLocations.add(new Location(world, 40, 40, 161));
        spawnLocations.add(new Location(world, 69, 40, 98));
        spawnLocations.add(new Location(world, 173, 39, 139));

        super.setArenaLocations(spawnLocations);
    }



}
