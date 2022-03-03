package com.pyralia.arena.kits.release;

import com.pyralia.arena.kits.KitSchedule;
import com.pyralia.arena.kits.KitType;
import com.pyralia.arena.player.KPlayer;
import com.pyralia.core.common.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @author Ariloxe
 */
public class ErenKit extends KitSchedule {

    private final PotionEffect resistanceEffect = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 23*20, 0, false, false);
    private final PotionEffect slownessEffect = new PotionEffect(PotionEffectType.SLOW, 2*20, 200, false, false);
    private final PotionEffect jumpEffect = new PotionEffect(PotionEffectType.JUMP, 2*20, 240, false, false);

    public ErenKit(){
        super("Eren Jeager", KitType.DPS, new ItemStack(Material.DEAD_BUSH),
                "§8» §7Mode : §eAOTv3",
                "§8» §7Pouvoirs:",
                "§f- §7Gagnez les effets §fRésistance 1§7 pendant §e23 secondes§7. (§a48s de cooldown§7)",
                "§f- §7Quand vous utiliserez vos effets, vous §cempêcherez§7 les joueurs dans un rayon de 10 blocs",
                " §7de bouger pendant §e2 secondes§7, et vous serez §dsoigné§7 de 3 coeurs.");
        super.setSecondsDelay(48);
    }

    @Override
    public void power(KPlayer kPlayer) {
        Player player = kPlayer.getBukkitPlayer();
        if(player != null){
            Bukkit.getOnlinePlayers().forEach(player1 -> player1.playSound(player.getLocation(), "pyralia.eren", 5, 5));

            player.getWorld().strikeLightningEffect(player.getLocation());
            player.addPotionEffect(resistanceEffect);
            if(player.getHealth() + 6 < player.getMaxHealth())
                player.setHealth(player.getHealth() + 6);
            else
                player.setHealth(20);

            Bukkit.getOnlinePlayers().stream().filter(target -> target.getWorld().getName().equals(player.getWorld().getName())).filter(target -> target.getLocation().distance(player.getLocation()) < 10).filter(target -> target != player).forEach(target -> {
                target.addPotionEffect(slownessEffect);
                target.addPotionEffect(jumpEffect);
            });
        }
    }

    @Override
    public void onEquip(Player player){
        player.getInventory().setItem(1, new ItemCreator(Material.DEAD_BUSH).name("§7Transformation titanesque").lore("", "§fVous permet de vous transfomer", "§fTransfomation toutes les 62 secondes.").get());
    }
}
