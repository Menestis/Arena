package fr.ariloxe.arena.maps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ariloxe
 */
public class ForestWorld extends SpecialWorld {

    public ForestWorld() {
        super("§a§lForêt", "Forest");

        List<Location> spawnLocations = new ArrayList<>();
        World world = Bukkit.getWorld("arenas");
        spawnLocations.add(new Location(world, -65, 59, 63));
        spawnLocations.add(new Location(world, -64, 58, -52));
        spawnLocations.add(new Location(world, 66, 58, -47));
        spawnLocations.add(new Location(world, 70, 60, 36));
        spawnLocations.add(new Location(world, -13, 58, 40));

        super.setArenaLocations(spawnLocations);
    }



}
