package fr.ariloxe.arena.maps;

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
        super("§9§lOriginal (2020)", "OriginaleV2");

        List<Location> spawnLocations = new ArrayList<>();
        World world = Bukkit.getWorld("arenas");
        spawnLocations.add(new Location(world, -1020, 60, -940));
        spawnLocations.add(new Location(world, -984, 59, -933));
        spawnLocations.add(new Location(world, -929, 60, -984));
        spawnLocations.add(new Location(world, -959, 60, -1038));
        spawnLocations.add(new Location(world, -1027, 60, -1038));

        super.setArenaLocations(spawnLocations);
    }



}
