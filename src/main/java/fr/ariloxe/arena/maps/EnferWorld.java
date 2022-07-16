package fr.ariloxe.arena.maps;

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
        super("§4§lEnfer", "Enfer");

        List<Location> spawnLocations = new ArrayList<>();
        World world = Bukkit.getWorld("arenas");
        spawnLocations.add(new Location(world, -481, 57, 460));
        spawnLocations.add(new Location(world, -549, 62, 479));
        spawnLocations.add(new Location(world, -533, 61, 539));
        spawnLocations.add(new Location(world, -454, 61, 559));
        spawnLocations.add(new Location(world, -492, 57, 516));

        super.setArenaLocations(spawnLocations);
    }



}
