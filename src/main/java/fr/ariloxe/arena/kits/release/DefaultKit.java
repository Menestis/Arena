package fr.ariloxe.arena.kits.release;

import fr.ariloxe.arena.kits.Kit;
import fr.ariloxe.arena.kits.KitType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author Ariloxe
 */
public class DefaultKit extends Kit {
    public DefaultKit() {
        super("Défaut", KitType.TANK, new ItemStack(Material.BREAD),
                "§8» §7Mode : §c/",
                "§8» §7Pouvoirs:",
                "§f- §7Vous avez une canne à pêche dans votre kit.");
    }

    @Override
    public void onEquip(Player player) {
        player.getInventory().addItem(new ItemStack(Material.FISHING_ROD));
    }

}
