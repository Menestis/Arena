package com.pyralia.arena.kits.release;

import com.pyralia.arena.kits.Kit;
import com.pyralia.arena.kits.KitType;
import com.pyralia.arena.utils.skull.SkullList;
import com.pyralia.core.common.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author Ariloxe
 */
public class LibeKit extends Kit {
    public LibeKit() {
        super("Libe_", KitType.DPS, SkullList.LIBE.getItemStack(),
                "§8» §7Mode : §c/",
                "§8» §7Pouvoirs:",
                "§f- §7Lorsque vous frapperez vers le §bSud§7, vous aurez",
                "    §7une reach augmentée après le premier coup.");
    }

    @Override
    public void onEquip(Player player){
        player.getInventory().setItem(1, new ItemCreator(Material.WATER_BUCKET).get());
    }

}
