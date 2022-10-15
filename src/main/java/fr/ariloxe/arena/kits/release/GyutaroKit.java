package fr.ariloxe.arena.kits.release;

import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.kits.KitSchedule;
import fr.ariloxe.arena.kits.KitType;
import fr.ariloxe.arena.player.KPlayer;
import fr.ariloxe.arena.utils.ItemCreator;
import fr.ariloxe.arena.utils.Tasks;
import fr.ariloxe.arena.utils.particle.DoubleCircleEffect;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Ariloxe
 */
public class GyutaroKit extends KitSchedule {
    public GyutaroKit() {
        super("Gyutaro", KitType.DPS, new ItemStack(Material.GOLD_HOE),
                "§8» §7Mode : §3Demon-Slayer",
                "§8» §7Pouvoirs:",
                "§f- §7Vous infligerez §c0.5 coeurs §7toutes",
                "    §7les 0.75s, pendant 7 secondes",
                "§8(§648 secondes de cooldown§8)"
        );
        super.setSecondsDelay(48);
    }

    @Override
    public void power(KPlayer kPlayer) {
        Player player = kPlayer.getBukkitPlayer();

        if(player != null){
            final int[] p = {0};

            new DoubleCircleEffect(15*7, EnumParticle.DRIP_LAVA).start(player);

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (p[0] >= 7 || player.getLocation() == null || player.getWorld().getName().equals("Spawn")){
                        cancel();
                        return;
                    }

                    player.getNearbyEntities(3, 3, 3).stream().filter(entity -> entity instanceof Player).filter(entity -> entity != player).forEach(entity -> ((Player) entity).damage(1));
                    p[0]++;
                }
            }.runTaskTimer(ArenaAPI.getApi(), 15, 15);
        }
    }

    @Override
    public void onEquip(Player player) {
        player.getInventory().setItem(1, new ItemCreator(Material.GOLD_HOE).name("§eFaucilles §8§l▪ §7Clic-droit").get());
    }
}
