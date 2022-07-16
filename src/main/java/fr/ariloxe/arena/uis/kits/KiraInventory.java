package fr.ariloxe.arena.uis.kits;

import fr.ariloxe.arena.player.KPlayer;
import fr.ariloxe.arena.utils.ItemCreator;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ariloxe
 */
public class KiraInventory {

    private final String prefix = "§3§lMenestis §f» ";
    public int p = 0;

    public KiraInventory(KPlayer kPlayer){
        p = 0;

        KInventory kInventory = new KInventory(27, "§cDeathNote §7> §8Attaque");
        Player player = kPlayer.getBukkitPlayer();

        for(Player online : getNearby(player.getLocation(),20)){
            if(online == player)
                continue;

            KItem kItem = new KItem(new ItemCreator(Material.SKULL_ITEM, 1, (short) 3).owner(online.getName()).name("§c" + online.getName()).get());
            kItem.addCallback((kInventoryRepresentation, itemStack, player1, kInventoryClickContext) -> {
                player1.closeInventory();

                player1.playSound(player1.getLocation(), Sound.LEVEL_UP, 1, 1);
                player1.sendMessage(prefix + "§cVous avez attaqué le joueur §e" + online.getName());
                online.sendMessage(prefix + "§cVous avez été attaqué(e) par le pouvoir du DeathNote !");
                if(online.getHealth() - 7 > 1)
                    online.setHealth(online.getHealth() - 6);
                else
                    online.setHealth(1);

            });
            kInventory.setElement(p, kItem);
            p++;
        }

        kInventory.open(player);
    }

    private List<Player> getNearby(Location loc, int distance) {
        List<Player> list = new ArrayList<>();

        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.getWorld() == loc.getWorld() && loc.distance(online.getLocation()) <= distance)
                list.add(online);
        }

        return list;
    }
}
