package com.pyralia.arena.maps;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ariloxe
 */
public abstract class SpecialWorld {

    private final String name;
    private final Location lobbyLocation;
    private List<Location> arenaLocations = new ArrayList<>();

    public SpecialWorld(String name, Location lobbyLocation){
        this.name = name;
        this.lobbyLocation = lobbyLocation;
    }

    public String getName() {
        return name;
    }

    public Location getLobbyLocation() {
        return lobbyLocation;
    }

    public List<Location> getArenaLocations() {
        return arenaLocations;
    }

    public void setArenaLocations(List<Location> arenaLocations) {
        this.arenaLocations = arenaLocations;
    }
}
