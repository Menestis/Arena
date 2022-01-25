package com.pyralia.arena.kits.release;

import com.pyralia.arena.ArenaAPI;
import com.pyralia.arena.kits.KitSchedule;
import com.pyralia.arena.kits.KitType;
import com.pyralia.arena.player.KPlayer;
import com.pyralia.arena.utils.BlockUtils;
import com.pyralia.core.common.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ariloxe
 */
public class MadaraKit extends KitSchedule {
    public MadaraKit() {
        super("Madara", KitType.TANK, new ItemStack(Material.BEDROCK),
                "§8» §7Mode : §eNaruto UHC",
                "§8» §7Pouvoirs:",
                "§f- §7Crée une météorite au dessus de vous, qui infligera",
                "    §75 coeurs de dégâts. (§a48s de cooldown§7)");

        setSecondsDelay(48);
    }

    @Override
    public void power(KPlayer kPlayer){
        Player player = kPlayer.getBukkitPlayer();
        if(player != null){
            spawnMeteore(kPlayer.getBukkitPlayer().getLocation(), 5);
            player.playSound(player.getLocation(), Sound.WITHER_DEATH, 1, 1);
        }
    }

    @Override
    public void onEquip(Player player){
        player.getInventory().setItem(1, new ItemCreator(Material.BEDROCK).name("§7Météorite").lore("").get());
    }


    public static final String METEORE_KEY = "MeteoreFallingBlock";


    public void spawnMeteore(Location loc, int radius) {


        Location center = loc.clone().add(0, 20, 0);
        List<Location> blockLocations = BlockUtils.generateSphere(center, radius, false);

        for (Location blockLocation : blockLocations) {
            FallingBlock fallingBlock = center.getWorld().spawnFallingBlock(blockLocation, Material.BEDROCK, (byte) 0);
            fallingBlock.setDropItem(false);
            fallingBlock.setHurtEntities(true);
            fallingBlock.setCustomName(METEORE_KEY);
        }
    }

}
