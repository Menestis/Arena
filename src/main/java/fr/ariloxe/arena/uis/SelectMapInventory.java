package fr.ariloxe.arena.uis;

import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.utils.ItemCreator;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Ariloxe
 */
public class SelectMapInventory {

    private KInventory kInventory;
    private final ArenaAPI arenaAPI;

    public SelectMapInventory(ArenaAPI instance){
        this.arenaAPI = instance;
        this.kInventory = new KInventory(KItem.DEFAULT, 27, "§7Choisir la carte");
    }

    public void open(Player kPlayer){
        AtomicInteger p = new AtomicInteger(0);
        arenaAPI.getGameManager().getWorldManager().getSpecialWorlds().forEach(specialWorld -> {
            KItem kItem = new KItem(new ItemCreator(Material.GRASS).name("§7§l" + specialWorld.getName()).lore("", "", "§8> §7Cliquez pour appliquer cette map").get());
            kItem.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
                arenaAPI.getGameManager().getWorldManager().switchWorld(specialWorld);
                player.closeInventory();
            });
            this.kInventory.setElement(p.get(), kItem);
            p.getAndIncrement();
        });

        this.kInventory.open(kPlayer);
    }

}
