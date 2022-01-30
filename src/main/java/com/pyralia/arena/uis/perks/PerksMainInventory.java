package com.pyralia.arena.uis.perks;

import com.pyralia.arena.ArenaAPI;
import com.pyralia.arena.kits.KitType;
import com.pyralia.arena.utils.PlayerUtils;
import com.pyralia.core.tools.skull.SkullList;
import com.pyralia.core.common.ItemCreator;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author Ariloxe
 */
public class PerksMainInventory {

    private KInventory kInventory;
    private final ArenaAPI arenaAPI;

    private final PerksDeathMessageInventory perksDeathMessageInventory = new PerksDeathMessageInventory(ArenaAPI.getApi());
    private final PerksDeathBroadcastInventory perksDeathBroadcastInventory = new PerksDeathBroadcastInventory(ArenaAPI.getApi());

    public PerksMainInventory(ArenaAPI instance){
        this.arenaAPI = instance;
        this.kInventory = new KInventory(KItem.DEFAULT, 54, "§8│ §6§lPerks");

        KItem deathMessages = new KItem(new ItemCreator(SkullList.CIBLE.getItemStack()).name("§8§l» §7§lMessages à la mort §8§l«").lore("§7Customisez l'assassinat de vos victimes avec des", "§7messages peu-communs, visibles aux yeux de tous.", "", "§8§l» §7Cliquez pour choisir").get());
        deathMessages.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> perksDeathMessageInventory.open(player));

        KItem broadcastMessages = new KItem(new ItemCreator(SkullList.CAMERA.getItemStack()).name("§8§l» §7§lCri de Guerre §8§l«").lore("§7Customisez l'assassinat de vos victimes avec des", "§7textes flottants au dessus du cadavre de ce dernier", "§7pendant quelques secondes.", "", "§8§l» §7Cliquez pour choisir").get());
        broadcastMessages.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> perksDeathBroadcastInventory.open(player));

        KItem soon = new KItem(new ItemCreator(Material.BARRIER).name("§c§kLLLL").lore("", "§c§oCe type de cosmétique arrive bientôt... :o", "").get());

        this.kInventory.setElement(21, broadcastMessages);
        this.kInventory.setElement(23, deathMessages);

        this.kInventory.setElement(28, soon);
        this.kInventory.setElement(34, soon);
    }

    public void open(Player kPlayer){
        this.kInventory.open(kPlayer);
    }


}
