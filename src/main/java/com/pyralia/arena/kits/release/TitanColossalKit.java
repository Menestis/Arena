package com.pyralia.arena.kits.release;

import com.pyralia.arena.ArenaAPI;
import com.pyralia.arena.kits.KitSchedule;
import com.pyralia.arena.kits.KitType;
import com.pyralia.arena.player.KPlayer;
import com.pyralia.arena.utils.ParticleEffect;
import com.pyralia.core.common.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ariloxe
 */
public class TitanColossalKit extends KitSchedule {

    private final PotionEffect resistanceEffect = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 7*20, 0, false, false);

    public TitanColossalKit() {
        super("Titan Colossal", KitType.TANK, new ItemStack(Material.BRICK),
                "§8» §7Mode : §eAOT",
                "§8» §7Pouvoirs:",
                "§f- §7Crée une explosion et rejette ainsi dans les airs les joueurs à moins de",
                "    §715 blocs de lui, et leur donne un effet de feu. (§a39 de cooldown§7)");

        setSecondsDelay(39);
    }

    @Override
    public void power(KPlayer kPlayer){
        Player player = kPlayer.getBukkitPlayer();
        if(player != null){
            ParticleEffect.FLAME.display(0, 0, 0, 0.3F, 15, player.getLocation(), 30);
            ParticleEffect.EXPLOSION_LARGE.display(0, 0, 0, 0.05F, 1, player.getLocation(), 30);
            kPlayer.getBukkitPlayer().playSound(kPlayer.getBukkitPlayer().getLocation(), Sound.EXPLODE, 1, 1);
            player.addPotionEffect(resistanceEffect);
            Bukkit.getOnlinePlayers().forEach(player1 -> player1.playSound(player.getLocation(), "pyralia.armin", 10, 10));
            player.getWorld().getNearbyEntities(player.getLocation(), 15, 15, 15).stream().filter(entity -> entity instanceof Player).filter(entity -> entity != player).forEach(entity -> {
                Vector v = getVectorForPoints(entity.getLocation(), player.getLocation());
                v = v.clone().multiply(-2);
                v = v.clone().setY(1D);
                entity.setVelocity(v);
                entity.setFireTicks(20*10);
                ((Player) entity).playSound(entity.getLocation(), Sound.EXPLODE, 1, 1);
            });
        }
    }

    @Override
    public void onEquip(Player player){
        player.getInventory().setItem(1, new ItemCreator(Material.NETHER_STAR).name("§7Souffle Colossal").lore("").get());
    }

    private Vector getVectorForPoints(Location l1, Location l2) {
        double vX = (1.0D + 0.07D * l2.distance(l1)) * (l2.getX() - l1.getX()) / l2.distance(l1);

        double vY = 0.5D;
        double vZ = (1.0D + 0.07D * l2.distance(l1)) * (l2.getZ() - l1.getZ()) / l2.distance(l1);
        return new Vector(vX, vY, vZ);
    }
}
