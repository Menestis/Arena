package fr.ariloxe.arena.kits.release;

import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.kits.KitSchedule;
import fr.ariloxe.arena.kits.KitType;
import fr.ariloxe.arena.player.KPlayer;
import fr.ariloxe.arena.utils.ItemCreator;
import fr.ariloxe.arena.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

/**
 * @author Ariloxe
 */
public class BrookKit extends KitSchedule {

    public BrookKit(){
        super("Brook", KitType.DPS, new ItemStack(Material.JUKEBOX),
                "§8» §7Mode : §bOne Piece UHC",
                "§8» §7Pouvoirs:",
                "§f- §7Permet de tuer d'un coup le joueur visé si ce dernier",
                "§7possède moins de §c2❤§7 en se téléportant à lui. (§a53 de cooldown§7)");
        super.setSecondsDelay(53);
    }

    @Override
    public void power(KPlayer kPlayer) {
        Player player = kPlayer.getBukkitPlayer();

        if(player != null){
            Player target = PlayerUtils.getTarget(player, 60, 1.4D, true);
            if (target != null) {
                if(target.getHealth() > 4){
                    Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), ()->{
                        getkPlayerRemainsList().remove(kPlayer);
                        getPlayerIntegerMap().remove(kPlayer);
                    }, 10);

                    player.sendMessage("§3§lMenestis §8» §cLe joueur §l" + target.getName() + "§c n'a pas moins de 2❤ !");
                    return;
                }

                player.setVelocity(player.getLocation().getDirection().multiply(1.2D).setY(0));

                Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), ()->{
                    player.teleport(target.getLocation());
                    player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 3.0F, 1.0F);
                    player.setVelocity(new Vector(0, 0, 0));
                    target.damage(40, player);
                    target.sendMessage("§3§lMenestis §8» §cVous avez été mis à mort par le Song Slash !");
                }, 7);
            } else {
                Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), ()->{
                    getkPlayerRemainsList().remove(kPlayer);
                    getPlayerIntegerMap().remove(kPlayer);
                }, 10);
            }
        }
    }

    @Override
    public void onEquip(Player player){
        player.getInventory().addItem(new ItemCreator(Material.JUKEBOX).name("§dSong Slash §8§l▪ §7Clic-droit").get());
    }
}
