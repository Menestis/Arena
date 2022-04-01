package com.pyralia.arena.kits.release;

import com.pyralia.arena.kits.Kit;
import com.pyralia.arena.kits.KitType;
import com.pyralia.api.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author Ariloxe
 */
public class KokushiboKit extends Kit {
    public KokushiboKit() {
        super("Kokushibo", KitType.TANK, new ItemStack(Material.TRIPWIRE_HOOK),
                "§8» §7Mode : §3Démon-Slayer",
                "§8» §7Pouvoirs:",
                "§f- §7Chaque kill, vous gagnez §53% §7de force supplémentaire.",
                "§f- §7Vous avez §c1 coeur§7 permanent supplémentaire.",
                "");
    }

    @Override
    public void onEquip(Player player){
        player.setMaxHealth(22);
        player.setHealth(player.getMaxHealth());
        player.getInventory().setItem(1, new ItemCreator(Material.WATER_BUCKET).get());
    }

}
