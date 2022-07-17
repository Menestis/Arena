package fr.ariloxe.arena.maps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ariloxe
 */
public class Naruto extends SpecialWorld {

    public Naruto() {
        super("§6Naruto", "Naruto");

        List<Location> spawnLocations = new ArrayList<>();
        World world = Bukkit.getWorld("arenas");
        spawnLocations.add(new Location(world, 1098, 7, -955));
        spawnLocations.add(new Location(world, 1013, 10, -935));
        spawnLocations.add(new Location(world, 956, 7, -1062));
        spawnLocations.add(new Location(world, 1073, 7, -1064));
        spawnLocations.add(new Location(world, 1134, 7, -954));

        super.setArenaLocations(spawnLocations);
    }



}
