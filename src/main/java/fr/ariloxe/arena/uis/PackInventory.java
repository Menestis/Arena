package fr.ariloxe.arena.uis;

import fr.ariloxe.arena.utils.ItemCreator;
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

        KItem kItem = new KItem(new ItemCreator(Material.PAPER).name("§7Pack de Son").lore("", "§7Ce pack de texture rajoute §bdivers bruits", "§7ainsi que des éléments sonores.", "", "§7Cliquez-ici pour télécharger.").get());
        kItem.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            player.closeInventory();
            player.setResourcePack("http://static.pyralia.com/PyraliaArene.zip");
        });

        this.kInventory.setElement(1, kItem);

        KItem kItem2 = new KItem(new ItemCreator(Material.WOOL).name("§7Pack Nakime").lore("", "§7Ce pack de texture est §cextrêmement", "§cconseillé§7 pour jouer sur le carte", "§7de jeu §c§lNakime§4§lV2", "", "§7Cliquez-ici pour télécharger.").get());
        kItem2.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            player.closeInventory();
            player.setResourcePack("http://static.pyralia.com/NakimePack.zip");
        });

        this.kInventory.setElement(3, kItem2);
    }

    public void open(Player player){
        this.kInventory.open(player);
    }

}
