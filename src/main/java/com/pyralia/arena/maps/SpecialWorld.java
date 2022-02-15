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
    private final String realName;
    private List<Location> arenaLocations = new ArrayList<>();

    public SpecialWorld(String name, Location lobbyLocation, String realName){
        this.name = name;
        this.lobbyLocation = lobbyLocation;
        this.realName = realName;
    }

    public String getName() {
        return name;
    }

    public String getRealName() {return realName;}


    public List<Location> getArenaLocations() {
        return arenaLocations;
    }

    public void setArenaLocations(List<Location> arenaLocations) {
        this.arenaLocations = arenaLocations;
    }
}
