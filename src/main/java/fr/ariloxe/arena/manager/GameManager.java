package fr.ariloxe.arena.manager;

import fr.ariloxe.arena.utils.Tasks;
import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.maps.ForestWorld;
import fr.ariloxe.arena.maps.SpecialWorld;
import fr.ariloxe.arena.player.KPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
        KPlayer kPlayer = ArenaAPI.getkPlayer(player);
        kPlayer.setDamageable(false);
        kPlayer.sendMessage("§3§lMenestis §f» §cVous êtes désormais invulnérable aux dégâts pendant §l6 secondes§c.");

        Tasks.runLater(()->{
            kPlayer.setDamageable(true);
            kPlayer.sendMessage("§3§lMenestis §f» §aVous pouvez désormais subir des dégâts.");
        }, 20*6);


        ArenaAPI.getkPlayer(player).getKit().onEquip(player);
        Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), ()->{
            if(player.getLocation() != null){
                if(player.getWorld().getName().contains("enferArena"))
                    player.playSound(player.getLocation(), "pyralia.ambiant_enfer", 15, 15);
                else if(player.getWorld().getName().contains("nakimeArena"))
                    player.playSound(player.getLocation(), "pyralia.ambiant_nakime", 15, 15);
                else
                    player.playSound(player.getLocation(), "pyralia.ambiant_chill", 15, 15);
            }
        }, 20);
    }

    public void setSpecialWorld(SpecialWorld specialWorld) {
        this.specialWorld = specialWorld;
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }
}
