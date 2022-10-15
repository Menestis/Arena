package fr.ariloxe.arena.kits.release;

import fr.ariloxe.arena.kits.Kit;
import fr.ariloxe.arena.kits.KitType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

/**
 * @author Ariloxe
 */
public class WitchKit extends Kit {
    public WitchKit() {
        super("Sorcière", KitType.HEALER, new ItemStack(Material.GLASS_BOTTLE),
                "§8» §7Mode : §9LG-UHC",
                "§8» §7Pouvoirs:",
                "§f- §7Vous avez §d6 potions §7d'instant heal.",
                "");
    }

    @Override
    public void onEquip(Player player){
        Potion pa = new Potion(PotionType.INSTANT_HEAL, 1).splash();
        ItemStack itema = pa.toItemStack(6);
        player.getInventory().addItem(itema);
    }

}
