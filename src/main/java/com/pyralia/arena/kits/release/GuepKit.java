package com.pyralia.arena.kits.release;

import com.pyralia.arena.kits.Kit;
import com.pyralia.arena.kits.KitType;
import com.pyralia.arena.player.KPlayer;
import com.pyralia.core.common.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ariloxe
 */
public class GuepKit extends Kit {

    private final Map<KPlayer, Entity> kPlayerEntityMap = new HashMap<>();

    public GuepKit() {
        super("Guep", KitType.DEFENSIVE, new ItemStack(Material.TRIPWIRE_HOOK),
                "§8» §7Mode : §6/",
                "§8» §7Pouvoirs:",
                "§f- §7Faites un clic-droit sur un joueur avec votre arc pour le",
                "    §7cibler. Vos flèches seront à tête chercheuse vers lui.",
                "");
    }

    @Override
    public void onEquip(Player player){
        player.getInventory().setItem(1, new ItemCreator(Material.WATER_BUCKET).get());
    }

    public Map<KPlayer, Entity> getkPlayerEntityMap() {
        return kPlayerEntityMap;
    }
}