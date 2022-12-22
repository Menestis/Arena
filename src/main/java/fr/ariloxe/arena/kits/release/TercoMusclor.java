package fr.ariloxe.arena.kits.release;

import fr.ariloxe.arena.kits.KitSchedule;
import fr.ariloxe.arena.kits.KitType;
import fr.ariloxe.arena.player.KPlayer;
import fr.ariloxe.arena.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @author Ariloxe
 */
public class TercoMusclor extends KitSchedule {

    private final PotionEffect resistanceEffect = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 23*20, 0, false, false);
    private final PotionEffect absorptionEffect = new PotionEffect(PotionEffectType.ABSORPTION, 4*20, 0, false, false);

    public TercoMusclor(){
        super("Terco Musclor", KitType.DPS, new ItemStack(Material.DEAD_BUSH),
                "§8» §7Mode : §6Pyralien",
                "§8» §7Pouvoirs:",
                "§f- §7Gagnez les effets §fRésistance 1§7 pendant §e23 secondes§7 et",
                "    §7possède 2 coeurs d'absorption. (§a48s de cooldown§7)");
        super.setSecondsDelay(48);
    }

    @Override
    public void power(KPlayer kPlayer) {
        Player player = kPlayer.getBukkitPlayer();
        if(player != null){
            player.addPotionEffect(resistanceEffect);
            player.addPotionEffect(absorptionEffect, true);
        }
    }

    @Override
    public void onEquip(Player player){
        player.getInventory().addItem(new ItemCreator(Material.DEAD_BUSH).name("§2Les Muscles §8§l▪ §7Clic-droit").get());
    }
}
