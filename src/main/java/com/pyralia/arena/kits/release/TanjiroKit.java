package com.pyralia.arena.kits.release;

import com.pyralia.arena.ArenaAPI;
import com.pyralia.arena.kits.KitSchedule;
import com.pyralia.arena.kits.KitType;
import com.pyralia.arena.player.KPlayer;
import com.pyralia.arena.utils.PlayerUtils;
import com.pyralia.arena.utils.skull.SkullList;
import com.pyralia.core.common.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @author Ariloxe
 */
public class TanjiroKit extends KitSchedule {

    private final PotionEffect speedEffect = new PotionEffect(PotionEffectType.SPEED, 20*30, 1, false, false);
    private final PotionEffect strenghtEffect = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*30, 0, false, false);

    public TanjiroKit() {
        super("Tanjiro", KitType.DPS, SkullList.TANJIRO.getItemStack(),
                "§8» §7Mode : §3Démon-Slayer",
                "§8» §7Pouvoirs:",
                "§f- §7Gagnez les effets §bSpeed II §7et §5Force I§7 pendant 30 secondes. (§a55s de cooldown§7)",
                "§f- §7Quand votre pouvoir sera fini, vous perdrez §c0 coeur permanent§7.");
        super.setSecondsDelay(55);
    }

    @Override
    public void power(KPlayer kPlayer){
        Player player = kPlayer.getBukkitPlayer();
        if(player != null){
            player.addPotionEffect(speedEffect);
            player.addPotionEffect(strenghtEffect);
            player.playSound(player.getLocation(), Sound.BLAZE_BREATH, 1, 1);
            player.sendMessage("§6§lPyralia §8§l» §7Vous avez activé votre §6Danse du dieu du feu§7 ! Vous perdrez §61 coeurs permanents§7 à la fin de votre pouvoir.");
            Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), ()-> player.setMaxHealth(player.getMaxHealth() - 2), 20*30);
        }
    }

    @Override
    public void onEquip(Player player){
        player.getInventory().setItem(1, new ItemCreator(Material.BLAZE_ROD).name("§7Danse du dieu du feu").lore("", "§fVous permet d'activer le pouvoir de la danse", "§fdu dieu du feu toutes les 55s.").get());
    }
}
