package fr.ariloxe.arena.kits.release;

import fr.ariloxe.arena.utils.ItemCreator;
import fr.ariloxe.arena.kits.Kit;
import fr.ariloxe.arena.kits.KitType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author Ariloxe
 */
public class AkazaKit extends Kit {
    public AkazaKit() {
        super("Akaza", KitType.TANK, new ItemStack(Material.TRIPWIRE_HOOK),
                "§8» §7Mode : §3Démon-Slayer",
                "§8» §7Pouvoirs:",
                "§f- §7Vous avez 12 coeurs permanents aulieu de 10.",
                "§f- §7Vous avez une flèche vers le joueur le plus proche.",
                "");
    }

    @Override
    public void onEquip(Player player){
        player.setMaxHealth(24);
        player.setHealth(player.getMaxHealth());
        player.getInventory().addItem(new ItemCreator(Material.WATER_BUCKET).get());
    }

}
