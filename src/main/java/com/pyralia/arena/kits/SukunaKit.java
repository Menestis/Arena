package com.pyralia.arena.kits;

import com.pyralia.arena.Main;
import com.pyralia.arena.kits.supply.Structure;
import com.pyralia.arena.player.KPlayer;
import com.pyralia.arena.utils.ParticleEffect;
import com.pyralia.arena.utils.PlayerUtils;
import com.pyralia.core.common.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @author Ariloxe
 */
public class SukunaKit extends KitSchedule {

    private final PotionEffect weaknessEffects = new PotionEffect(PotionEffectType.WEAKNESS,6*20, 1, false, false);
    private final Structure structure = new Structure(7, 6, ParticleEffect.REDSTONE, Material.NETHER_BRICK, Material.NETHERRACK, Material.NETHERRACK);

    public SukunaKit(){
        super("Sukuna", new ItemStack(Material.NETHER_STAR),
                "§8» §7Mode : §5JJK",
                "§8» §7Type : §eOffensif",
                "§8» §7Pouvoirs:",
                "§f- §7Extendez votre territoire et donnez §9Weakness II§7 pendant §b6 secondes§7 aux",
                "§7joueurs dedans en téléportant tous les joueurs dans un rayon de §e10 blocs§7 dans",
                "§7votre extension. (§a53s de cooldown§7)");
        super.setSecondsDelay(53);
    }

    @Override
    public void power(KPlayer kPlayer) {
        structure.onEnable(kPlayer);
        Bukkit.getOnlinePlayers().stream().filter(player -> player.getLocation().distance(kPlayer.getBukkitPlayer().getLocation()) < 10).filter(player -> player != kPlayer.getBukkitPlayer()).forEach(player -> {
            player.addPotionEffect(weaknessEffects);
            player.teleport(kPlayer.getBukkitPlayer().getLocation());
        });
    }

    @Override
    public void onEquip(Player player){
        PlayerUtils.teleportPlayer(player);

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), ()-> {
            player.getInventory().clear();
            player.setMaxHealth(20);
            player.setHealth(player.getMaxHealth());

            player.getInventory().setItem(0, new ItemCreator(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, 2).get());
            player.getInventory().setItem(1, new ItemCreator(Material.NETHER_STAR).name("§7Extension du Territoire").lore("", "§fVous permet d'invoquer une extension du territoire.").get());
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
