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
public class NamiKit extends KitSchedule {

    private final PotionEffect potionEffect = new PotionEffect(PotionEffectType.SLOW, 20*5, 0, false, false);

    public NamiKit(){
        super("Nami", KitType.DEFENSIVE, new ItemStack(Material.DEAD_BUSH),
                "§8» §7Mode : §bOnePiece",
                "§8» §7Pouvoirs:",
                "§f- §7Permet de faire tomber la foudre sur ses adversaires, infligeant deux coeurs de dégâts",
                "    §7ainsi que Slowness I pendant 5 secondes.",
                " §7de bouger pendant §e2 secondes§7, et vous serez §dsoigné§7 de 3 coeurs.");
        super.setSecondsDelay(48);
    }

    @Override
    public void power(KPlayer kPlayer) {
        Player player = kPlayer.getBukkitPlayer();
        if(player != null){
            Bukkit.getOnlinePlayers().stream().filter(target -> target.getLocation().distance(player.getLocation()) < 10).filter(target -> target != player).forEach(target -> {
                target.getWorld().strikeLightningEffect(target.getLocation());
                target.addPotionEffect(potionEffect);
            });
        }
    }

    @Override
    public void onEquip(Player player){
        player.getInventory().setItem(1, new ItemCreator(Material.DEAD_BUSH).name("§7Transformation titanesque").lore("", "§fVous permet de vous transfomer", "§fTransfomation toutes les 62 secondes.").get());
    }
}