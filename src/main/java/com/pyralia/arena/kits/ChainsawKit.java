package com.pyralia.arena.kits;

import com.pyralia.arena.Main;
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
import org.bukkit.util.Vector;

/**
 * @author Ariloxe
 */
public class ChainsawKit extends KitSchedule {

    private final PotionEffect speedEffect = new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, false, false);
    private final PotionEffect strenghtEffect = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0, false, false);

    public ChainsawKit() {
        super("Kit de Chainsaw man", new ItemStack(Material.QUARTZ),
                "§8» §7Mode : §cChainsawMan-UHC",
                "§8» §7Type : §eOffensif",
                "§8» §7Pouvoirs:",
                "§f- §7Vous pourrez vous propulser d'une 10aine de blocs. (§a16s de cooldown§7)",
                "§f- §7Vous pourrez changer, quand vous voudrez, de mode:",
                "  §8» §7Mode Chainsaw:",
                "   §f- §7Vous avez une tête spéciale, donc plus de casque en diamant.",
                "   §f- §7Vous gagnez les effets §bSpeed I§7 et §5Force I§7.",
                "  §8» §7Mode Normal:",
                "   §f- §7Vous n'avez plus la tête spéciale, et donc de nouveau votre casque.",
                "   §f- §7Vous n'aurez plus vos effets.",
                ""
        );
        super.setSecondsDelay(16);
    }

    public void activate(Player player){
        if(player.getInventory().getHelmet().getType() == Material.DIAMOND_HELMET){
            Bukkit.getOnlinePlayers().forEach(player1 -> player1.playSound(player.getLocation(), "goldenuhc.chainsaw_power_activated", 1, 1));
            player.getInventory().setHelmet(new ItemCreator(Material.QUARTZ_ORE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).get());
            player.addPotionEffect(speedEffect);
            player.addPotionEffect(strenghtEffect);
        } else {
            player.getInventory().setHelmet(new ItemCreator(Material.DIAMOND_HELMET).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).get());
            player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
        }
    }

    @Override
    public void power(KPlayer kPlayer){
        Player player = kPlayer.getBukkitPlayer();
        if(player != null){
            Vector v = player.getLocation().getDirection().multiply(1.5).setY(0.5);
            player.setVelocity(v);
        }
    }

    @Override
    public void onEquip(Player player){
        player.getInventory().clear();
        player.setMaxHealth(22);
        player.setHealth(player.getMaxHealth());

        player.getInventory().setItem(0, new ItemCreator(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, 3).get());
        player.getInventory().setItem(1, new ItemCreator(Material.ENDER_PORTAL_FRAME).name("§7Activer le démon chainsaw").lore("", "§fMets force I et speed I et retire la tête.").get());
        player.getInventory().setItem(2, new ItemStack(Material.GOLDEN_APPLE, 9));
        player.getInventory().setItem(3, new ItemCreator(Material.FEATHER).name("§7Dash").lore("", "§fPermets de Dash en avant.").get());
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
