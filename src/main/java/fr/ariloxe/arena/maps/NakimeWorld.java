package fr.ariloxe.arena.maps;

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
        super("§c§lNakime", "Nakiki");

        List<Location> spawnLocations = new ArrayList<>();
        World world = Bukkit.getWorld("arenas");
        spawnLocations.add(new Location(world, 480, 107, -479));
        spawnLocations.add(new Location(world, 532, 86, -494));
        spawnLocations.add(new Location(world, 475, 65, -533));
        spawnLocations.add(new Location(world, 530, 61, -536));
        spawnLocations.add(new Location(world, 531, 57, -466));
        spawnLocations.add(new Location(world, 508, 56, -498));
        spawnLocations.add(new Location(world, 460, 80, -527));

        super.setArenaLocations(spawnLocations);
    }



}
