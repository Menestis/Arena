package com.pyralia.arena.maps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ariloxe
 */
public class NakimeWorld extends SpecialWorld {

    public NakimeWorld() {
        super("§f§lChateau de l'Infini", new Location(Bukkit.getWorld("nakimeArena"), 7, 180, 35));

        List<Location> spawnLocations = new ArrayList<>();
        World world = Bukkit.getWorld("nakimeArena");
        spawnLocations.add(new Location(world, -23, 34, 30));
        spawnLocations.add(new Location(world, 5, 35, 0));
        spawnLocations.add(new Location(world, -33, 29, -16));
        spawnLocations.add(new Location(world, -34, 10, 24));
        spawnLocations.add(new Location(world, 37, 10, 39));
        spawnLocations.add(new Location(world, 10, 51, 10));
        spawnLocations.add(new Location(world, 28, 57, 38));

        super.setArenaLocations(spawnLocations);
    }



}
