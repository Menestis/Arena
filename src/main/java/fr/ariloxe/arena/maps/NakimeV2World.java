package fr.ariloxe.arena.maps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ariloxe
 */
public class NakimeV2World extends SpecialWorld {

    public NakimeV2World() {
        super("§c§lNakime§4§lV2", "NakikiV2");

        List<Location> spawnLocations = new ArrayList<>();
        World world = Bukkit.getWorld("arenas");
        spawnLocations.add(new Location(world, 513, 60, 529));
        spawnLocations.add(new Location(world, 450, 56, 490));
        spawnLocations.add(new Location(world, 560, 71, 552));
        spawnLocations.add(new Location(world, 479, 126, 542));
        spawnLocations.add(new Location(world, 483, 105, 445));
        spawnLocations.add(new Location(world, 535, 88, 508));
        spawnLocations.add(new Location(world, 544, 94, 501));

        super.setArenaLocations(spawnLocations);
    }



}
