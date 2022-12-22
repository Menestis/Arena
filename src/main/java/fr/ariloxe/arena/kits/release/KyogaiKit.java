package fr.ariloxe.arena.kits.release;

import fr.ariloxe.arena.kits.KitSchedule;
import fr.ariloxe.arena.kits.KitType;
import fr.ariloxe.arena.player.KPlayer;
import fr.ariloxe.arena.utils.ItemCreator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author Ariloxe
 */
public class KyogaiKit extends KitSchedule {
    public KyogaiKit() {
        super("Kyogai", KitType.DEFENSIVE, new ItemStack(Material.STICK),
                "§8» §7Mode : §3Démon-Slayer",
                "§8» §7Pouvoirs:",
                "§f- §7Vous pouvez retourner de 180 degrés tous les joueurs autours",
                "    §7de vous. (§a18 secondes de cooldown§7)",
                "");
        super.setSecondsDelay(18);
    }

    @Override
    public void onEquip(Player player){
        player.getInventory().addItem(new ItemCreator(Material.STICK).name("§6Retournement 180° §8§l▪ §7Clic-droit").get());
    }

    @Override
    public void power(KPlayer kPlayer){
        Player player = kPlayer.getBukkitPlayer();
        if(player != null){
            player.playSound(player.getLocation(), Sound.ZOMBIE_WOODBREAK, 1, 1);
            for(Object target : player.getNearbyEntities(7, 7, 7).stream().filter(entity -> entity instanceof Player).toArray()){
                if(target instanceof Player){
                    Player targetPlayer = ((Player) target);
                    if(targetPlayer == player)
                        continue;

                    Location tp = targetPlayer.getLocation();
                    tp.setYaw(tp.getYaw() + 180);
                    targetPlayer.teleport(tp);
                }
            }
        }
    }

}
