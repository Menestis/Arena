package fr.ariloxe.arena.uis.settings;

import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import fr.menestis.commons.bukkit.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

/**
 * @author Ariloxe
 */
public class SettingsInventory {

    private final KInventory kInventory = new KInventory(InventoryType.HOPPER, "§6Paramètres de l'arène.");
    private final ConfigInvInventory configInvInventory = new ConfigInvInventory(this);

    public SettingsInventory(){
        KItem kItem = new KItem(new ItemCreator(Material.CHEST).name("§bGestion de votre inventaire").lore(
                "",
                "§fVous permet de modifier les paramètres",
                "§fde votre inventaire par défaut.",
                "",
                "§8• §7Cliquez pour §aaccéder §7à ce menu.").get());
        kItem.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> configInvInventory.open(player));
        kInventory.setElement(0, kItem);

        KItem kItem2 = new KItem(new ItemCreator(Material.FIREWORK).name("§dGestion des cosmétiques").lore(
                "",
                "§fVous permet de modifier les paramètres",
                "§fde votre inventaire par défaut.",
                "",
                "§8• §cBientôt...").get());
        kInventory.setElement(1, kItem2);

        KItem kItem3 = new KItem(new ItemCreator(Material.ARROW).name("§cFermer cet inventaire").get());
        kItem3.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> player.closeInventory());
        kInventory.setElement(4, kItem3);
    }

    public void open(Player player){
        this.kInventory.open(player);
    }

}
