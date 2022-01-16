package com.pyralia.arena.kits.release;

import com.pyralia.arena.ArenaAPI;
import com.pyralia.arena.kits.KitSchedule;
import com.pyralia.arena.kits.KitType;
import com.pyralia.arena.player.KPlayer;
import com.pyralia.arena.utils.PlayerUtils;
import com.pyralia.core.common.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Ariloxe
 */
public class OursKit extends KitSchedule {

    public OursKit() {
        super("Kit Ours", KitType.TANK, new ItemStack(Material.STICK),
                "§8» §7Mode : §bElityBox §7(§fHommage§7)",
                "§8» §7Pouvoirs:",
                "§f- §7Effectuez une rotation qui infligera des dégats à tous les joueurs",
                "    §7autour de vous. (§a21 secondes de cooldown§7)",
                "§f- §7Obtenez l'effet de Résistance permanent.");
        super.setSecondsDelay(21);
    }

    @Override
    public void power(KPlayer kPlayer){
        Player player = kPlayer.getBukkitPlayer();
        if(player != null){
            Location location = player.getLocation().clone().add(0, 2, 0);
            player.playSound(player.getLocation(), Sound.PISTON_RETRACT, 5.0F, 0.0F);
            (new BukkitRunnable() {
                private int time = 6;
                public void run() {
                    location.setYaw(location.getYaw() + 60.0F);
                    player.teleport(location);
                    location.getWorld().getNearbyEntities(player.getLocation(), 15.0D, 15.0D, 15.0D).forEach(entity -> {
                        if (entity instanceof Player && entity != player) {
                            Player players = (Player)entity;
                            players.damage(1);
                        }
                    });

                    if (time <= 0)
                        cancel();

                    this.time--;
                }
            }).runTaskTimer(ArenaAPI.getApi(), 0L, 4);
        }
    }

    private final PotionEffect potionEffect = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0, false, false);

    @Override
    public void onEquip(Player player){
        player.setMaxHealth(22);
        player.setHealth(player.getMaxHealth());

        player.addPotionEffect(potionEffect);
        player.getInventory().setItem(1, new ItemCreator(Material.STICK).name("§7Retourné de l'Ours").lore("", "§fVous permet de faire un 3/6 noscope !").get());
    }
}