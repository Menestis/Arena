package com.pyralia.arena.kits;

import com.pyralia.arena.Main;
import com.pyralia.arena.player.KPlayer;
import com.pyralia.arena.utils.PlayerUtils;
import com.pyralia.core.common.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @author Ariloxe
 */
public class ErenKit extends KitSchedule {

    private final PotionEffect strenghtEffect = new PotionEffect(PotionEffectType.INCREASE_DAMAGE,30*20, 0, false, false);
    private final PotionEffect resistanceEffect = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 30*20, 0, false, false);
    private final PotionEffect slownessEffect = new PotionEffect(PotionEffectType.SLOW, 5*20, 200, false, false);
    private final PotionEffect jumpEffect = new PotionEffect(PotionEffectType.JUMP, 5*20, 240, false, false);

    public ErenKit(){
        super("Eren", new ItemStack(Material.DEAD_BUSH), "Eren dans SNK");
        super.setSecondsDelay(62);
    }

    @Override
    public void power(KPlayer kPlayer) {
        Player player = kPlayer.getBukkitPlayer();
        if(player != null){
            player.getWorld().strikeLightningEffect(player.getLocation());
            player.addPotionEffect(strenghtEffect);
            player.addPotionEffect(resistanceEffect);
            player.setHealth(20);

            Bukkit.getOnlinePlayers().stream().filter(target -> target.getLocation().distance(player.getLocation()) < 10).filter(target -> target != player).forEach(target -> {
                target.addPotionEffect(slownessEffect);
                target.addPotionEffect(jumpEffect);
            });
        }
    }

    @Override
    public void onEquip(Player player){
        player.getInventory().clear();
        player.setMaxHealth(20);
        player.setHealth(player.getMaxHealth());

        player.getInventory().setItem(0, new ItemCreator(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, 2).get());
        player.getInventory().setItem(1, new ItemCreator(Material.DEAD_BUSH).name("§7Transformation titanesque").lore("", "§fVous permet de vous transfomer", "§fTransfomation toutes les 62 secondes.").get());
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

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), ()-> PlayerUtils.teleportPlayer(player), 3);
    }



}
