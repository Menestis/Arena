package fr.ariloxe.arena.maps;

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
        super("§3§lOriginal (2021)", "OriginaleV3");

        List<Location> spawnLocations = new ArrayList<>();
        World world = Bukkit.getWorld("arenas");
        spawnLocations.add(new Location(world, -1025, 60, 1049));
        spawnLocations.add(new Location(world, -1043, 60, 974));
        spawnLocations.add(new Location(world, -957, 62, 958));
        spawnLocations.add(new Location(world, -955, 62, 1038));
        spawnLocations.add(new Location(world, -1046, 59, 1056));

        super.setArenaLocations(spawnLocations);
    }



}
