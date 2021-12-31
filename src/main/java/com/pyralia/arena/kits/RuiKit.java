package com.pyralia.arena.kits;

import com.pyralia.arena.ArenaAPI;
import com.pyralia.arena.player.KPlayer;
import com.pyralia.arena.utils.PlayerUtils;
import com.pyralia.core.common.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ariloxe
 */
public class RuiKit extends KitSchedule {
    public RuiKit() {
        super("kit de Rui", new ItemStack(Material.WEB),
                "§8» §7Mode : §3Démon-Slayer",
                "§8» §7Type : §eOffensif",
                "§8» §7Pouvoirs:",
                "§f- §7Crée une sphère de Cobweb autour de soi pendant 8 secondes. (§a48s de cooldown§7)",
                "§f- §7Vous offre des cobweb dans votre kit.");

        setSecondsDelay(48);
    }

    @Override
    public void power(KPlayer kPlayer){
        Player player = kPlayer.getBukkitPlayer();
        if(player != null){
            List<Location> blockList = getSphere(kPlayer, 5, true);
            blockList.forEach(location -> {
                Block block = location.getBlock();
                if(block.getType() == Material.AIR || block.getType() == Material.LONG_GRASS)
                    block.setType(Material.WEB);
            });
            player.playSound(player.getLocation(), Sound.SPIDER_IDLE, 1, 1);

            Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), ()-> blockList.forEach(location -> {
                Block block = location.getBlock();
                if(block.getType() == Material.WEB)
                    block.setType(Material.AIR);
            }), 20*8);
        }
    }

    @Override
    public void onEquip(Player player){
        player.getInventory().setItem(1, new ItemCreator(Material.STRING).name("§7Cage de l'Araignée").lore("").get());
        player.getInventory().setItem(3, new ItemCreator(Material.WEB).amount(7).get());
    }

    private final Map<KPlayer, List<Location>> kPlayerListMap = new HashMap<>();
    private List<Location> getSphere(KPlayer kPlayer, int radius, boolean hollow){
        List<Location> circleBlocks = new ArrayList<>();

        Location centerBlock = kPlayer.getBukkitPlayer().getLocation();
        int bX = centerBlock.getBlockX();
        int bY = centerBlock.getBlockY();
        int bZ = centerBlock.getBlockZ();

        for(int x = bX - radius; x <= bX + radius; x++){
            for(int y = bY - radius; y <= bY + radius; y++){
                for(int z = bZ - radius; z <= bZ + radius; z++){
                    double distance = ((bX - x) * (bX - x) + ((bZ - z) * (bZ - z)) + ((bY - y) * (bY - y)));
                    if(distance < radius * radius && !(hollow && distance < ((radius -1) * (radius -1)))){
                        Location block = new Location(centerBlock.getWorld(), x, y, z);
                        circleBlocks.add(block);
                    }
                }
            }
        }
        return circleBlocks;
    }


    //Location tp = uhcPlayer1.getBukkitPlayer().getLocation();
    //tp.setYaw(tp.getYaw() + 180);
    //uhcPlayer1.getBukkitPlayer().teleport(tp);
}
