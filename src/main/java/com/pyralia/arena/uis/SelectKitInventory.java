package com.pyralia.arena.uis;

import com.pyralia.arena.ArenaAPI;
import com.pyralia.core.common.ItemCreator;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Ariloxe
 */
public class SelectKitInventory {

    private KInventory kInventory;
    private final ArenaAPI arenaAPI;

    public SelectKitInventory(ArenaAPI instance){
        this.arenaAPI = instance;
        this.kInventory = new KInventory(KItem.DEFAULT, 27, "§7Choisir son Kit");
    }

    public void open(Player kPlayer){
        AtomicInteger p = new AtomicInteger(0);
        arenaAPI.getKitManager().getKitList().forEach(kit -> {
            KItem kItem = new KItem(new ItemCreator(kit.getItemStack()).name(kit.getName()).lore(kit.getDescription()).get());
            kItem.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
                ArenaAPI.getkPlayer(player).setKit(kit);
                player.sendMessage("§6§lPyralia §8§l» §7Vous avez §béquipé§7 le " + kit.getName());
            });
            this.kInventory.setElement(p.get(), kItem);
            p.getAndIncrement();
        });

        this.kInventory.open(kPlayer);
    }


}
