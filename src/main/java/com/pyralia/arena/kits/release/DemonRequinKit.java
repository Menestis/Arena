package com.pyralia.arena.kits.release;

import com.pyralia.arena.ArenaAPI;
import com.pyralia.arena.kits.KitSchedule;
import com.pyralia.arena.kits.KitType;
import com.pyralia.arena.player.KPlayer;
import com.pyralia.arena.utils.BlockUtils;
import com.pyralia.core.common.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ariloxe
 */
public class DemonRequinKit extends KitSchedule {

    public DemonRequinKit() {
        super("Démon Requin", KitType.TANK, new ItemStack(Material.WATER_BUCKET),
                "§8» §7Mode : §cChainsawMan-UHC",
                "§8» §7Pouvoirs:",
                "§f- §7Vous pouvez créer une sphère d'§3eau§7 pendant §b25 secondes§7. Dedans, vous",
                "    §7aurez Force I. (§a81 secondes de cooldown§7)",
                "§f- §7Vous avez l'enchantement Depth Strider V sur vos bottes.",
                "§f- §7Vous avez l'enchantement Respiration V sur vos bottes.",
                "§f- §7Vous avez une épée en fer Sharpness III à la place."

        );
        super.setSecondsDelay(81);
    }

    private final PotionEffect strenghtEffect = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 25*20, 0, false, false);
    private final Map<KPlayer, List<Block>> kPlayerListMap = new HashMap<>();

    @Override
    public void power(KPlayer kPlayer) {
        Player player = kPlayer.getBukkitPlayer();
        if(player != null){
            kPlayer.getBukkitPlayer().addPotionEffect(strenghtEffect);
            kPlayerListMap.put(kPlayer, new ArrayList<>());

            final Location location = kPlayer.getBukkitPlayer().getLocation();

            for(Block block : BlockUtils.circle(location, 10.0, 10.0, false, true, 3)){
                if(block.getType() == Material.AIR){
                    kPlayerListMap.get(kPlayer).add(block);
                    block.setType(Material.STATIONARY_WATER);
                }
            }

            Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), ()->{
                for(Block block : kPlayerListMap.get(kPlayer)){
                    block.setType(Material.AIR);
                }
            }, 20*25);
        }
    }

    @Override
    public void onEquip(Player player) {
        player.getInventory().setItem(0, new ItemCreator(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, 3).get());
        player.getInventory().setItem(1, new ItemCreator(Material.WATER_LILY).name("§7Pacte du §cdémon-Requin").lore("", "§fVous permet d'activer le pouvoir du pacte fort", "§fdu démon requin toutes les 61s.").get());

        player.getInventory().setHelmet(new ItemCreator(Material.DIAMOND_HELMET).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).enchant(Enchantment.OXYGEN, 9).unbreakable(true).get());
       player.getInventory().setBoots(new ItemCreator(Material.DIAMOND_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).enchant(Enchantment.DEPTH_STRIDER, 5).unbreakable(true).get());
    }
}
