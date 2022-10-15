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
public class NamiKit extends KitSchedule {

    private final PotionEffect potionEffect = new PotionEffect(PotionEffectType.SLOW, 20*5, 0, false, false);

    public NamiKit(){
        super("Nami", KitType.DEFENSIVE, new ItemStack(Material.DEAD_BUSH),
                "§8» §7Mode : §bOnePiece",
                "§8» §7Pouvoirs:",
                "§f- §7Permet de faire tomber la foudre sur ses adversaires, infligeant deux coeurs de dégâts",
                "    §7ainsi que Slowness I pendant 5 secondes.");
        super.setSecondsDelay(48);
    }

    @Override
    public void power(KPlayer kPlayer) {
        Player player = kPlayer.getBukkitPlayer();

        if(player != null){
            Bukkit.getOnlinePlayers().stream().filter(target -> target.getWorld().getName().equals(player.getWorld().getName()) && target.getLocation().distance(player.getLocation()) < 10).filter(target -> target != player).forEach(target -> {
                target.getWorld().strikeLightningEffect(target.getLocation());
                target.addPotionEffect(potionEffect);
            });
        }
    }

    @Override
    public void onEquip(Player player){
        player.getInventory().addItem(new ItemCreator(Material.WATCH).name("§eFoudre §8§l▪ §7Clic-droit").get());
    }
}
