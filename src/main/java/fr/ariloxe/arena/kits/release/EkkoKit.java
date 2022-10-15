package fr.ariloxe.arena.kits.release;

import fr.ariloxe.arena.utils.ItemCreator;
import fr.ariloxe.arena.kits.KitSchedule;
import fr.ariloxe.arena.kits.KitType;
import fr.ariloxe.arena.player.KPlayer;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @author Ariloxe
 */
public class EkkoKit extends KitSchedule {

    public EkkoKit() {
        super("Ekko", KitType.DPS, new ItemStack(Material.FLINT_AND_STEEL),
                "§8» §7Mode : §9ArcaneUHC",
                "§8» §7Pouvoirs:",
                "§f- §7En cliquant sur votre Briquet Zaunien, vous §cenflammerez§7 les joueurs",
                "    §7dans une zone de §f10x10§7 blocs autour de vous, et vous obtiendrez",
                "    §7l'effet de §crésistance au feu §7pendant une §e30s§7.",
                "    §7(§a37 secondes de cooldown§7)"
        );
        super.setSecondsDelay(37);
    }

    private final PotionEffect potionEffect = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 30*20, 0);

    @Override
    public void power(KPlayer kPlayer) {
        Player player = kPlayer.getBukkitPlayer();

        if(player != null){
            player.addPotionEffect(potionEffect);
            player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
            Location location = player.getLocation();
            player.getNearbyEntities(10, 10, 10).forEach(entity -> {
                if(entity instanceof Player)
                    entity.setFireTicks(20*3);
            });
        }
    }

    @Override
    public void onEquip(Player player) {
        player.getInventory().addItem(new ItemCreator(Material.FLINT).name("§cBriquet Zaunien §8§l▪ §7Clic-droit").get());
    }
}
