package fr.ariloxe.arena.kits.release;

import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.kits.KitSchedule;
import fr.ariloxe.arena.kits.KitType;
import fr.ariloxe.arena.player.KPlayer;
import fr.ariloxe.arena.utils.ItemCreator;
import fr.ariloxe.arena.utils.particle.DoubleCircleEffect;
import fr.menestis.commons.bukkit.Cuboid;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @author Ariloxe
 */
public class SengokuKit extends KitSchedule {

    private final PotionEffect slownessEffect = new PotionEffect(PotionEffectType.SLOW, 3*20, 200, false, false);
    private final PotionEffect jumpEffect = new PotionEffect(PotionEffectType.JUMP, 3*20, 240, false, false);

    public SengokuKit(){
        super("Sengoku", KitType.HEALER, new ItemStack(Material.GOLD_NUGGET),
                "§8» §7Mode : §bOnePiece UHC",
                "§8» §7Pouvoirs:",
                "§f- §7Vous ne pourrez plus bouger pendant 3 secondes, ni prendre ni infliger de dégâts.",
                "§f- §7Néanmoins à la fin, vous serez heal de la moitié de votre vie en moins. (§a37 de cooldown§7)"
        );
        super.setSecondsDelay(37);
    }

    @Override
    public void power(KPlayer kPlayer) {
        Player player = kPlayer.getBukkitPlayer();
        if(player != null){
            ArenaAPI.getkPlayer(player).setDamageable(false);

            player.addPotionEffect(slownessEffect);
            player.addPotionEffect(jumpEffect);

            new DoubleCircleEffect(3*20, EnumParticle.FLAME).start(player);

            Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), () -> {
                player.setHealth(Math.min(20, player.getHealth() + (20 - player.getHealth() / 2)));
                ArenaAPI.getkPlayer(player).setDamageable(true);
            }, 3 * 20);
        }
    }

    @Override
    public void onEquip(Player player){
        player.getInventory().addItem(new ItemCreator(Material.GOLD_NUGGET).name("§6Grand Bouddha §8§l▪ §7Clic-droit").get());
    }
}
