package fr.ariloxe.arena.maps;

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
        super("§b§lOriginal (2017)", "Originale");

        List<Location> spawnLocations = new ArrayList<>();
        World world = Bukkit.getWorld("arenas");
        spawnLocations.add(new Location(world, 1026, 61, 1020));
        spawnLocations.add(new Location(world, 991, 61, 958));
        spawnLocations.add(new Location(world, 966, 61, 1025));
        spawnLocations.add(new Location(world, 1054, 60, 986));
        spawnLocations.add(new Location(world, 992, 61, 977));

        super.setArenaLocations(spawnLocations);
    }



}
