package fr.ariloxe.arena.kits.release;

import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.kits.KitSchedule;
import fr.ariloxe.arena.kits.KitType;
import fr.ariloxe.arena.player.KPlayer;
import fr.ariloxe.arena.utils.particle.DoubleCircleEffect;
import fr.ariloxe.arena.utils.ItemCreator;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author Ariloxe
 */
public class DakiKit extends KitSchedule {
    public DakiKit() {
        super("Daki", KitType.HEALER, new ItemStack(Material.RED_ROSE),
                "§8» §7Mode : §3Démon-Slayer",
                "§8» §7Pouvoirs:",
                "§f- §7Vous créez une explosion de particules vertes, et vous",
                "    §7serez §dsoignée§7 de 4 coeurs. (§a31 secondes de cooldown§7)",
                "    §7uniquement utilisable en dessous de §c2 coeurs§7."
        );
        super.setSecondsDelay(31);
    }

    @Override
    public void power(KPlayer kPlayer) {
        Player player = kPlayer.getBukkitPlayer();

        if(player != null){
            if (player.getHealth() < 5) {
                Bukkit.getOnlinePlayers().forEach(player1 -> player1.playSound(player.getLocation(), "pyralia.daki", 5, 5));
                player.setHealth(player.getHealth() + 8);
                new DoubleCircleEffect(30, EnumParticle.HEART).start(player);
                player.playSound(player.getLocation(), Sound.DRINK, 3.0F, 1.0F);
            } else {
                Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), ()->{
                    getkPlayerRemainsList().remove(kPlayer);
                    getPlayerIntegerMap().remove(kPlayer);
                }, 10);
            }
        }
    }

    @Override
    public void onEquip(Player player) {
        player.getInventory().addItem(new ItemCreator(Material.RED_ROSE).name("§dOniisan §8§l▪ §7Clic-droit").get());
    }
}
