package com.pyralia.arena.kits.release;

import com.pyralia.arena.ArenaAPI;
import com.pyralia.arena.kits.KitSchedule;
import com.pyralia.arena.kits.KitType;
import com.pyralia.arena.player.KPlayer;
import com.pyralia.core.common.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @author Ariloxe
 */
public class ObanaiKit extends KitSchedule {
    public ObanaiKit() {
        super("Kit de Obanai", KitType.DEFENSIVE, new ItemStack(Material.SUGAR), "§8» §7Mode : §3Démon-Slayer",
                "§8» §7Type : §eDiscret",
                "§8» §7Pouvoir: Toutes les 43 secondes, vous pouvez vous rendre",
                "§7invisible pendant 23 secondes contre un effet de faiblesse.");
        super.setSecondsDelay(43);
    }

    @Override
    public void onEquip(Player player) {
        player.getInventory().setItem(1, new ItemCreator(Material.SUGAR).name("§b§lInvisibilité").lore("§7Devenez §2invisible §7durant", "§223 secondes§7.").get());
    }


    @Override
    public void power(KPlayer kPlayer) {
        Player player = kPlayer.getBukkitPlayer();
        player.sendMessage("§6§lPyralia §8» §2Vous êtes maintenant invisible !");
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 23*20, 1, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 23*20, 0, false, false));
        Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), ()->{
            Location ly = player.getLocation();

            if(player.getLocation() == null)
                return;

            if(ly.getBlockY() < 100 && kPlayer.getKit() instanceof ObanaiKit){
                player.getInventory().setHelmet(new ItemCreator(Material.DIAMOND_HELMET).unbreakable(true).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).get());
                player.getInventory().setChestplate(new ItemCreator(Material.DIAMOND_CHESTPLATE).unbreakable(true).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).get());
                player.getInventory().setLeggings(new ItemCreator(Material.IRON_LEGGINGS).unbreakable(true).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).get());
                player.getInventory().setBoots(new ItemCreator(Material.DIAMOND_BOOTS).unbreakable(true).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).get());
                player.sendMessage("§6§lPyralia §8» §7Attention, §cvous êtes redevenu(e) visible§7.");
                player.updateInventory();
            }

        }, 20*23);
    }
}
