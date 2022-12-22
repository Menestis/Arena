package fr.ariloxe.arena.kits.release;

import fr.ariloxe.arena.utils.packets.Title;
import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.kits.KitSchedule;
import fr.ariloxe.arena.kits.KitType;
import fr.ariloxe.arena.player.KPlayer;
import fr.ariloxe.arena.utils.ItemCreator;
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
        super.setSecondsDelay(39);
    }

    @Override
    public void power(KPlayer kPlayer) {
        kPlayerIntegerMap.put(kPlayer, new HashMap<>());
        new Title("", "§a§lINVINCIBLE..").sendToPlayer(kPlayer.getBukkitPlayer());
        Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), ()->{
            kPlayerIntegerMap.get(kPlayer).forEach((kPlayer1, integer) -> {
                if(kPlayer1.getBukkitPlayer() != null)
                    kPlayer1.getBukkitPlayer().damage(integer / 8, kPlayer.getBukkitPlayer());
            });

            new Title("", "§c§lCONTRE TOTAL !!").sendToPlayer(kPlayer.getBukkitPlayer());
            kPlayerIntegerMap.remove(kPlayer);
        }, 20*3);

    }

    @Override
    public void onEquip(Player player){
        player.getInventory().addItem(new ItemCreator(Material.NETHER_BRICK_ITEM).name("§cContre Total §8§l▪ §7Clic-droit").get());
    }

    public Map<KPlayer, Map<KPlayer, Double>> getkPlayerIntegerMap() {
        return kPlayerIntegerMap;
    }
}
