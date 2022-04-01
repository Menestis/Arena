package com.pyralia.arena.kits.release;

import com.pyralia.api.utils.packets.Title;
import com.pyralia.arena.ArenaAPI;
import com.pyralia.arena.kits.KitSchedule;
import com.pyralia.arena.kits.KitType;
import com.pyralia.arena.player.KPlayer;
import com.pyralia.api.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ariloxe
 */
public class MeliodasKit extends KitSchedule {

    private final Map<KPlayer, Map<KPlayer, Double>> kPlayerIntegerMap = new HashMap<>();

    public MeliodasKit() {
        super("Meliodas", KitType.DEFENSIVE, new ItemStack(Material.GOLD_SWORD),
                "§8» §7Mode : §aNNT UHC",
                "§8» §7Pouvoirs:",
                "§f- §7Vous avez un item qui vous permet de renvoyer 50% des",
                "    §7dégâts obtenus pendant 3 secondes. (§a53 secondes de cooldown§7)",
                "");
        super.setSecondsDelay(53);
    }

    @Override
    public void power(KPlayer kPlayer) {
        kPlayerIntegerMap.put(kPlayer, new HashMap<>());
        new Title("", "§a§lINVINCIBLE..").sendToPlayer(kPlayer.getBukkitPlayer());
        Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), ()->{
            kPlayerIntegerMap.get(kPlayer).forEach((kPlayer1, integer) -> {
                if(kPlayer1.getBukkitPlayer() != null)
                    kPlayer1.getBukkitPlayer().damage(integer / 8);
            });

            new Title("", "§c§lCONTRE TOTAL !!").sendToPlayer(kPlayer.getBukkitPlayer());
            kPlayerIntegerMap.remove(kPlayer);
        }, 20*3);

    }

    @Override
    public void onEquip(Player player){
        player.getInventory().setItem(1, new ItemCreator(Material.NETHER_BRICK_ITEM).name("§c§lCONTRE TOTAL").get());
    }

    public Map<KPlayer, Map<KPlayer, Double>> getkPlayerIntegerMap() {
        return kPlayerIntegerMap;
    }
}
