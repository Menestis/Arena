package fr.ariloxe.arena.kits.release;

import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.kits.KitSchedule;
import fr.ariloxe.arena.kits.KitType;
import fr.ariloxe.arena.player.KPlayer;
import fr.ariloxe.arena.utils.BlockUtils;
import fr.ariloxe.arena.utils.ItemCreator;
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
                "    §7aurez Force I. (§a53 secondes de cooldown§7)",
                "§f- §7Vous avez l'enchantement Depth Strider V sur vos bottes.",
                "§f- §7Vous avez l'enchantement Respiration V sur vos bottes."

        );
        super.setSecondsDelay(53);
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
        player.getInventory().addItem(new ItemCreator(Material.WATER_LILY).name("§3Bulle d'Eau §8§l▪ §7Clic-droit").get());

        player.getInventory().setHelmet(new ItemCreator(Material.DIAMOND_HELMET).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).enchant(Enchantment.OXYGEN, 9).unbreakable(true).get());
        player.getInventory().setBoots(new ItemCreator(Material.DIAMOND_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).enchant(Enchantment.DEPTH_STRIDER, 5).unbreakable(true).get());
    }
}
