package fr.ariloxe.arena.kits.release;

import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.kits.KitSchedule;
import fr.ariloxe.arena.kits.KitType;
import fr.ariloxe.arena.player.KPlayer;
import fr.ariloxe.arena.utils.ItemCreator;
import fr.ariloxe.arena.utils.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

/**
 * @author Ariloxe
 */
public class DemonGorilleKit extends KitSchedule {
    public DemonGorilleKit(){
        super("Démon Gorille", KitType.DPS, new ItemStack(Material.BEDROCK),
                "§8» §7Mode : §cChainsawMan-UHC",
                "§8» §7Pouvoirs:",
                "§f- §7Vous éjecte en l'air puis vous fait retomber, créant ainsi une",
                "§7onde de choc blessant les joueurs aux alentours. (§a41 de cooldown§7)",
                "§f- §7Vous ne prenez aucun dégât de chute.");
        super.setSecondsDelay(41);
    }

    @Override
    public void power(KPlayer kPlayer) {
        Player player = kPlayer.getBukkitPlayer();
        if(player != null){
            player.setVelocity(player.getVelocity().setY(4));
            ArenaAPI.getkPlayer(player).setDamageable(false);

            Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), ()-> player.setVelocity(player.getVelocity().setY(-8)), 10);

            Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), ()->{
                ParticleEffect.FLAME.display(0, 0, 0, 0.3F, 15, player.getLocation(), 30);
                ParticleEffect.EXPLOSION_LARGE.display(0, 0, 0, 0.05F, 1, player.getLocation(), 30);

                player.getWorld().getNearbyEntities(player.getLocation(), 15, 15, 15).stream().filter(entity -> entity instanceof Player).filter(entity -> entity != player).forEach(entity -> {
                    Vector v = getVectorForPoints(entity.getLocation(), player.getLocation());
                    v = v.clone().multiply(-2);
                    v = v.clone().setY(1D);
                    entity.setVelocity(v);
                    ((Player) entity).damage(5);
                    ((Player) entity).playSound(entity.getLocation(), Sound.EXPLODE, 1, 1);
                });
            }, 15);

            Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), ()->{
                ArenaAPI.getkPlayer(player).setDamageable(true);
            }, 35);
        }
    }

    private Vector getVectorForPoints(Location l1, Location l2) {
        double vX = (1.0D + 0.07D * l2.distance(l1)) * (l2.getX() - l1.getX()) / l2.distance(l1);

        double vY = 0.5D;
        double vZ = (1.0D + 0.07D * l2.distance(l1)) * (l2.getZ() - l1.getZ()) / l2.distance(l1);
        return new Vector(vX, vY, vZ);
    }


    @Override
    public void onEquip(Player player) {
        player.getInventory().addItem(new ItemCreator(Material.BEDROCK).name("§eSaut du Gorille §8§l▪ §7Clic-droit").get());
    }

}
