package com.pyralia.arena.kits.release;

import com.pyralia.arena.kits.KitSchedule;
import com.pyralia.arena.kits.KitType;
import com.pyralia.arena.player.KPlayer;
import com.pyralia.api.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @author Ariloxe
 */
public class TercoMusclor extends KitSchedule {

    private final PotionEffect resistanceEffect = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 30*20, 0, false, false);
    private final PotionEffect absorptionEffect = new PotionEffect(PotionEffectType.ABSORPTION, 30*20, 1, false, false);

    public TercoMusclor(){
        super("Terco Musclor", KitType.DPS, new ItemStack(Material.DEAD_BUSH),
                "§8» §7Mode : §6Pyralien",
                "§8» §7Pouvoirs:",
                "§f- §7Gagnez les effets §fRésistance 1§7 pendant §e30 secondes§7 et",
                "    §7possède 4 coeurs d'absorption. (§a48s de cooldown§7)",
                "§f- §7Il est bloqué à 8 coeurs permanent.");
        super.setSecondsDelay(48);
    }

    @Override
    public void power(KPlayer kPlayer) {
        Player player = kPlayer.getBukkitPlayer();
        if(player != null){
            player.addPotionEffect(resistanceEffect);
            player.addPotionEffect(absorptionEffect, true);
        }
    }

    @Override
    public void onEquip(Player player){
        player.getInventory().setItem(1, new ItemCreator(Material.DEAD_BUSH).name("§7LES MUUUUSCLEES").get());
    }
}
