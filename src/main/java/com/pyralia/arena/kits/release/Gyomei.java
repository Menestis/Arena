package com.pyralia.arena.kits.release;

import com.pyralia.api.utils.ItemCreator;
import com.pyralia.arena.ArenaAPI;
import com.pyralia.arena.kits.KitSchedule;
import com.pyralia.arena.kits.KitType;
import com.pyralia.arena.player.KPlayer;
import com.pyralia.arena.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @author Ariloxe
 */
public class Gyomei extends KitSchedule {
    public Gyomei() {
        super("Gyomei", KitType.DPS, new ItemStack(Material.STONE_AXE),
                "§8» §7Mode : §3Démon-Slayer",
                "§8» §7Pouvoirs:",
                "§f- §7En cliquant avec votre hache, vous obtiendrez l'effet de.",
                "    §fRésistance I§7 et les joueurs autour de vous auront l'effet",
                "    §7de §anausée§7, pendant §610s§7 (§a42 secondes de cooldown§7)"
        );
        super.setSecondsDelay(42);
    }

    private final PotionEffect potionEffect = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*10, 0, false, false);
    private final PotionEffect potionEffect2 = new PotionEffect(PotionEffectType.CONFUSION, 20*10, 0, false, false);


    @Override
    public void power(KPlayer kPlayer) {
        Player player = kPlayer.getBukkitPlayer();

        if(player != null){
            player.addPotionEffect(potionEffect);
            player.getWorld().getNearbyEntities(player.getLocation(), 10, 10, 10).stream().filter(entity -> entity instanceof Player).filter(entity -> entity != player).forEach(entity -> {
               ((Player) entity).addPotionEffect(potionEffect2);
            });
        }
    }

    @Override
    public void onEquip(Player player) {
        player.getInventory().setItem(1, new ItemCreator(Material.STONE_AXE).name("§7Gyomei").get());
    }
}
