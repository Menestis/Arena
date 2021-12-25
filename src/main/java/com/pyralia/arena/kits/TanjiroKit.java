package com.pyralia.arena.kits;

import com.pyralia.arena.ArenaAPI;
import com.pyralia.arena.player.KPlayer;
import com.pyralia.arena.utils.PlayerUtils;
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
        super("Kit de Tanjiro", new ItemStack(Material.BLAZE_ROD),
                "§8» §7Mode : §3Démon-Slayer",
                "§8» §7Type : §eOffensif",
                "§8» §7Pouvoirs:",
                "§f- §7Gagnez les effets §bSpeed II §7et §5Force I§7 pendant 30 secondes. (§a55s de cooldown§7)",
                "§f- §7Quand votre pouvoir sera fini, vous perdrez §c2 coeurs permanents§7.");
        super.setSecondsDelay(55);
    }

    @Override
    public void power(KPlayer kPlayer){
        Player player = kPlayer.getBukkitPlayer();
        if(player != null){
            player.addPotionEffect(speedEffect);
            player.addPotionEffect(strenghtEffect);
            player.playSound(player.getLocation(), Sound.BLAZE_BREATH, 1, 1);
            player.sendMessage("§6§lPyralia §8§l» §7Vous avez activé votre §6Danse du dieu du feu§7 ! Vous perdrez §62 coeurs permanents§7 à la fin de votre pouvoir.");
            Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), ()-> player.setMaxHealth(player.getMaxHealth() - 4), 20*30);
        }
    }

    @Override
    public void onEquip(Player player){
        PlayerUtils.teleportPlayer(player);

        Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), ()-> {
            player.getInventory().clear();
            player.setMaxHealth(20);
            player.setHealth(player.getMaxHealth());

            player.getInventory().setItem(0, new ItemCreator(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, 2).get());
            player.getInventory().setItem(1, new ItemCreator(Material.BLAZE_ROD).name("§7Danse du dieu du feu").lore("", "§fVous permet d'activer le pouvoir de la danse", "§fdu dieu du feu toutes les 55s.").get());
            player.getInventory().setItem(2, new ItemStack(Material.GOLDEN_APPLE, 9));
            player.getInventory().setItem(3, new ItemCreator(Material.DIAMOND_PICKAXE).enchant(Enchantment.DIG_SPEED, 3).get());
            player.getInventory().setItem(4, new ItemStack(Material.WATER_BUCKET));
            player.getInventory().setItem(5, new ItemStack(Material.COBBLESTONE, 64));
            player.getInventory().setItem(6, new ItemStack(Material.ARROW, 32));
            player.getInventory().setItem(7, new ItemCreator(Material.BOW).enchant(Enchantment.ARROW_DAMAGE, 2).get());
            player.getInventory().setItem(8, new ItemStack(Material.COBBLESTONE, 64));

            player.getInventory().setHelmet(new ItemCreator(Material.DIAMOND_HELMET).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).get());
            player.getInventory().setChestplate(new ItemCreator(Material.DIAMOND_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).get());
            player.getInventory().setLeggings(new ItemCreator(Material.IRON_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).get());
            player.getInventory().setBoots(new ItemCreator(Material.DIAMOND_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).get());

        }, 3);
    }
}
