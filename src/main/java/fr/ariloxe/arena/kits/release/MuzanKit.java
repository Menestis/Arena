package fr.ariloxe.arena.kits.release;

import fr.ariloxe.arena.utils.ItemCreator;
import fr.ariloxe.arena.utils.skull.SkullList;
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
public class MuzanKit extends KitSchedule {
    public MuzanKit() {
        super("Muzan", KitType.TANK, new ItemStack(SkullList.MUZAN.getItemStack()),
                "§8» §7Mode : §3Démon-Slayer",
                "§8» §7Pouvoirs:",
                "§f- §7Lors de chaque élimination, vous aurez §c1/3§7 chance d'obtenir un demi-coeur",
                "    §7permanent supplémentaire.",
                "§f- §7Vous aurez l'effet  §bForce 1 pendant 12 seconde. (§a36 secondes de cooldown§7)");

        super.setSecondsDelay(44);
    }

    private final PotionEffect potionEffect = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*12, 0, false, false);


    @Override
    public void power(KPlayer kPlayer) {
        Player player = kPlayer.getBukkitPlayer();

        if(player != null){
            player.addPotionEffect(potionEffect);
        }
    }

    @Override
    public void onEquip(Player player) {
        player.getInventory().addItem(new ItemCreator(Material.FEATHER).name("§4Griffe du Démon §8§l▪ §7Clic-droit").get());
    }
}
