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

/**
 * @author Ariloxe
 */
public class DemonBatKit extends KitSchedule {
    public DemonBatKit() {
        super("Kit de Démon Bat", new ItemStack(Material.DRAGON_EGG),
                "§8» §7Mode : §cChainsawMan-UHC",
                "§8» §7Type : §eOffensif",
                "§8» §7Pouvoirs:",
                "§f- §7Vous n'avez que §c8 coeurs permanents§7 aulieu de 10.",
                "§f- §7Vous pourrez voler pendant §e13 secondes§7. (§a55 secondes de cooldown§7)"
        );
        super.setSecondsDelay(55);
    }

    @Override
    public void power(KPlayer kPlayer) {
        Player player = kPlayer.getBukkitPlayer();

        if(player != null){
            player.setAllowFlight(true);
            player.playSound(player.getLocation(), Sound.BAT_HURT, 1, 1);
            Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), ()-> player.setAllowFlight(false), 13*20);
        }
    }

    @Override
    public void onEquip(Player player) {
        player.setMaxHealth(16);
        player.getInventory().setItem(1, new ItemCreator(Material.FEATHER).name("§7Pacte du §cdémon-Bat").lore("", "§fVous permet d'activer le pouvoir du pacte fort", "§fdu démon bat toutes les 45s.").get());
    }
}
