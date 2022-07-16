package fr.ariloxe.arena.maps;

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
        super("§8CityWorld", "CityWorld");

        List<Location> spawnLocations = new ArrayList<>();
        World world = Bukkit.getWorld("arenas");
        spawnLocations.add(new Location(world, -562, 60, -391));
        spawnLocations.add(new Location(world, -483, 60, -437));
        spawnLocations.add(new Location(world, -488, 60, -550));
        spawnLocations.add(new Location(world, -498, 60, -496));
        spawnLocations.add(new Location(world, -438, 60, -470));

        super.setArenaLocations(spawnLocations);
    }



}
