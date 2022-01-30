package com.pyralia.arena.kits.release;

import com.pyralia.arena.kits.Kit;
import com.pyralia.arena.kits.KitType;
import com.pyralia.core.tools.skull.SkullList;
import com.pyralia.core.common.ItemCreator;
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

        player.getInventory().setItem(1, new ItemCreator(Material.WATER_BUCKET).get());
    }

}
