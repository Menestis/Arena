package fr.ariloxe.arena.kits.release;

import fr.ariloxe.arena.kits.KitSchedule;
import fr.ariloxe.arena.kits.KitType;
import fr.ariloxe.arena.player.KPlayer;
import fr.ariloxe.arena.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @author Ariloxe
 */
public class CharetteKit extends KitSchedule {

    private final PotionEffect speedEffect = new PotionEffect(PotionEffectType.SPEED, 7*20, 2, false, false);

    public CharetteKit(){
        super("Titan Charette", KitType.DEFENSIVE, new ItemStack(Material.NETHER_STAR),
                "§8» §7Mode : §eAOTv3",
                "§8» §7Pouvoirs:",
                "§f- §7Gagnez les effets §fSpeed 3§7 pendant §e7 secondes§7. (§a47s de cooldown§7)");
        super.setSecondsDelay(47);
    }

    @Override
    public void power(KPlayer kPlayer) {
        Player player = kPlayer.getBukkitPlayer();
        if(player != null){
            player.getWorld().strikeLightningEffect(player.getLocation());
            player.addPotionEffect(speedEffect);
        }
    }

    @Override
    public void onEquip(Player player){
        player.getInventory().addItem(new ItemCreator(Material.NETHER_STAR).name("§aTransformation §8§l▪ §7Clic-droit").get());
    }
}
