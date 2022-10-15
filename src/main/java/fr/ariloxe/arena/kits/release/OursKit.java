package fr.ariloxe.arena.kits.release;

import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.kits.KitSchedule;
import fr.ariloxe.arena.kits.KitType;
import fr.ariloxe.arena.player.KPlayer;
import fr.ariloxe.arena.utils.ItemCreator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Ariloxe
 */
public class OursKit extends KitSchedule {

    public OursKit() {
        super("Ours", KitType.TANK, new ItemStack(Material.STICK),
                "§8» §7Mode : §bElityBox §7(§fHommage§7)",
                "§8» §7Pouvoirs:",
                "§f- §7Effectuez une rotation qui infligera des dégats à tous les joueurs",
                "    §7autour de vous. (§a21 secondes de cooldown§7)",
                "§f- §7Obtenez l'effet de Résistance pendant 12 secondes après votre rotation.");
        super.setSecondsDelay(21);
    }

    @Override
    public void power(KPlayer kPlayer){
        Player player = kPlayer.getBukkitPlayer();
        if(player != null){
            player.addPotionEffect(potionEffect);
            Location location = player.getLocation().clone().add(0, 2, 0);
            player.playSound(player.getLocation(), Sound.PISTON_RETRACT, 5.0F, 0.0F);
            (new BukkitRunnable() {
                private int time = 6;
                public void run() {
                    location.setYaw(location.getYaw() + 60.0F);
                    player.teleport(location);
                    location.getWorld().getNearbyEntities(player.getLocation(), 15.0D, 15.0D, 15.0D).forEach(entity -> {
                        if (entity instanceof Player && entity != player) {
                            Player players = (Player)entity;
                            players.damage(1);
                            players.setVelocity(players.getLocation().getDirection().multiply(0.5).setY(0.3));
                        }
                    });

                    if (time <= 0)
                        cancel();

                    this.time--;
                }
            }).runTaskTimer(ArenaAPI.getApi(), 0L, 2);
        }
    }

    private final PotionEffect potionEffect = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*12, 0, false, false);

    @Override
    public void onEquip(Player player){
        player.setMaxHealth(22);
        player.setHealth(player.getMaxHealth());

        player.getInventory().addItem(new ItemCreator(Material.STICK).name("§6Retournée de l'Ours §8§l▪ §7Clic-droit").get());
    }
}
