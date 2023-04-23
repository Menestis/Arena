package fr.ariloxe.arena.manager;

import fr.ariloxe.arena.utils.Tasks;
import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.maps.ForestWorld;
import fr.ariloxe.arena.maps.SpecialWorld;
import fr.ariloxe.arena.player.KPlayer;
import fr.ariloxe.arena.utils.inventory.InvUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Ariloxe
 */
public class GameManager {

    private SpecialWorld specialWorld;
    private WorldManager worldManager;
    private final List<Location> locationList=  new ArrayList<>();
    private final List<UUID> editingList = new ArrayList<>();

    public List<UUID> getEditingList() {
        return editingList;
    }

    public GameManager(){
        specialWorld = new ForestWorld();
        worldManager = new WorldManager(this, specialWorld);
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
        kPlayer.getBukkitPlayer().setGameMode(GameMode.SURVIVAL);
        kPlayer.sendMessage("§3§lMenestis §f» §cVous êtes désormais invulnérable aux dégâts pendant §l6 secondes§c.");

        Tasks.runLater(()->{
            kPlayer.setDamageable(true);
            kPlayer.sendMessage("§3§lMenestis §f» §aVous pouvez désormais subir des dégâts.");
        }, 20*6);

        InvUtils.removeFromInventory(player, Material.NETHER_STAR);

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
