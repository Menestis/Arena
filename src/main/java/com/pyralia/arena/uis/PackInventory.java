package com.pyralia.arena.uis;

import com.pyralia.core.common.ItemCreator;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

/**
 * @author Ariloxe
 */
public class PackInventory {

    private final KInventory kInventory;
    public PackInventory(){
        this.kInventory = new KInventory(InventoryType.HOPPER, "§6§lPack de Texture");

        KItem kItem = new KItem(new ItemCreator(Material.PAPER).name("§7Téléchargement automatique").lore("", "§7Cliquez-ici pour télécharger le pack de texture.").get());
        kItem.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            player.closeInventory();
            player.setResourcePack("http://static.pyralia.com/PyraliaArene.zip");
        });

        this.kInventory.setElement(2, kItem);
    }

    public void open(Player player){
        this.kInventory.open(player);
    }

}
