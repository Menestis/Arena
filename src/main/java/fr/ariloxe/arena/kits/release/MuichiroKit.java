package fr.ariloxe.arena.kits.release;

import fr.ariloxe.arena.utils.ItemCreator;
import fr.ariloxe.arena.kits.KitSchedule;
import fr.ariloxe.arena.kits.KitType;
import fr.ariloxe.arena.player.KPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @author Ariloxe
 */
public class MuichiroKit extends KitSchedule {
    public MuichiroKit() {
        super("Muichiro", KitType.DEFENSIVE, new ItemStack(Material.FEATHER),
                "§8» §7Mode : §3Démon-Slayer",
                "§8» §7Pouvoirs:",
                "§f- §7En cliquant avec votre hache, les joueurs autour de vous.",
                "    §7auront l'effet d'§8aveuglement§7 pendant §c9s§7.",
                "    §7(§a44 secondes de cooldown§7)"
        );
        super.setSecondsDelay(44);
    }

    private final PotionEffect potionEffect = new PotionEffect(PotionEffectType.BLINDNESS, 20*9, 0, false, false);


    @Override
    public void power(KPlayer kPlayer) {
        Player player = kPlayer.getBukkitPlayer();

        if(player != null){
            player.getWorld().getNearbyEntities(player.getLocation(), 10, 10, 10).stream().filter(entity -> entity instanceof Player).filter(entity -> entity != player).forEach(entity -> {
                ((Player) entity).addPotionEffect(potionEffect);
            });
        }
    }

    @Override
    public void onEquip(Player player) {
        player.getInventory().addItem(new ItemCreator(Material.FEATHER).name("§bSouffle de la Brume §8§l▪ §7Clic-droit").get());
    }
}
