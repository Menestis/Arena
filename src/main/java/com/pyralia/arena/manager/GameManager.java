package com.pyralia.arena.manager;

import com.pyralia.arena.ArenaAPI;
import com.pyralia.arena.maps.ForestWorld;
import com.pyralia.arena.maps.SpecialWorld;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ariloxe
 */
public class GameManager {

    private SpecialWorld specialWorld;
    private WorldManager worldManager;
    private final List<Location> locationList=  new ArrayList<>();


    public GameManager(){
        ForestWorld forestWorld = new ForestWorld();
        specialWorld = forestWorld;

        worldManager = new WorldManager(this, forestWorld);
    }

    public SpecialWorld getSpecialWorld() {
        return specialWorld;
    }

    public List<Location> getLocationList() {
        return locationList;
    }

    public void joinArena(Player player){
        ArenaAPI.getkPlayer(player).getKit().onEquip(player);
    }

    public void setSpecialWorld(SpecialWorld specialWorld) {
        this.specialWorld = specialWorld;
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }
}
