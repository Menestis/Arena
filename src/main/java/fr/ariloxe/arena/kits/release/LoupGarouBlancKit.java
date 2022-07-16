package fr.ariloxe.arena.kits.release;

import fr.ariloxe.arena.utils.ItemCreator;
import fr.ariloxe.arena.kits.Kit;
import fr.ariloxe.arena.kits.KitType;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @author Ariloxe
 */
public class LoupGarouBlancKit extends Kit {
    public LoupGarouBlancKit() {
        super("Kit de Loup-Garou Blanc", KitType.TANK, new ItemStack(Material.REDSTONE),
                "§8» §7Mode : §9Loup-Garou UHC",
                "§8» §7Pouvoirs:",
                "§f- §7Vous possedez l'effet force 1 de façon permanente, ainsi que 15 coeurs",
                "§f- §c§lNéanmoins§7, seul votre plastron et vos bottes seront en diamant.");
    }

    @Override
    public void onEquip(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0, false, false));
        player.setMaxHealth(30);
        player.setHealth(player.getMaxHealth());
        player.getInventory().setHelmet(new ItemCreator(Material.IRON_HELMET).unbreakable(true).enchant(Enchantment.PROTECTION_ENVIRONMENTAL,2).get());
        player.getInventory().setItem(1, new ItemStack(Material.WATER_BUCKET));
    }

}
