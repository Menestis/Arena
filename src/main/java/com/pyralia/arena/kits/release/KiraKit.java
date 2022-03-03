package com.pyralia.arena.kits.release;

import com.pyralia.arena.ArenaAPI;
import com.pyralia.arena.kits.KitSchedule;
import com.pyralia.arena.kits.KitType;
import com.pyralia.arena.player.KPlayer;
import com.pyralia.arena.uis.kits.KiraInventory;
import com.pyralia.arena.utils.PlayerUtils;
import com.pyralia.arena.utils.particle.DoubleCircleEffect;
import com.pyralia.core.common.ItemCreator;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author Ariloxe
 */
public class KiraKit extends KitSchedule {
    public KiraKit() {
        super("Kira", KitType.DPS, new ItemStack(Material.RED_ROSE),
                "§8» §7Mode : §3Death§6Note",
                "§8» §7Pouvoirs:",
                "§f- §7Vous pouvez faire perdre §c3 coeurs§7 non-permanent au joueur de",
                "    §7votre choix dans un rayon de 15 blocs. Si il a moins de 5 coeurs,",
                "    §7il sera laissé à un demi-coeur. (§a43 secondes de délai§7)"
        );
        super.setSecondsDelay(43);
    }

    @Override
    public void power(KPlayer kPlayer) {
        Player player = kPlayer.getBukkitPlayer();
        if(player != null)
            new KiraInventory(kPlayer);

    }

    @Override
    public void onEquip(Player player) {
        player.getInventory().setItem(1, new ItemCreator(Material.BOOK).name("§cDeathNote").get());
    }
}
