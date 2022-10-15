package fr.ariloxe.arena.uis;

import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import fr.menestis.commons.bukkit.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ariloxe
 */
public interface KInventoryGui {
    final List<Integer> list = Arrays.asList(0, 1, 7, 8, 9, 17, 36, 44, 45, 46, 52, 53);
    final KItem glass = new KItem(new ItemCreator(Material.STAINED_GLASS_PANE, 1, (short) 11).name("§c").get());

    default void fill(KInventory kInventory){
        for(int i = 0; i < 54; i++){
            if(list.contains(i))
                kInventory.setElement(i, glass);
        }
    }

    void open(Player player);
}
