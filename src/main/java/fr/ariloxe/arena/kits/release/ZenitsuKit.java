package fr.ariloxe.arena.kits.release;

import fr.ariloxe.arena.utils.ItemCreator;
import fr.ariloxe.arena.utils.skull.SkullList;
import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.kits.KitSchedule;
import fr.ariloxe.arena.kits.KitType;
import fr.ariloxe.arena.player.KPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @author Ariloxe
 */
public class ZenitsuKit extends KitSchedule {
    public ZenitsuKit() {
        super("Zenitsu", KitType.DEFENSIVE, new ItemStack(SkullList.ZENITSU.getItemStack()),
                "§8» §7Mode : §3Démon-Slayer",
                "§8» §7Pouvoirs:",
                "§f- §c9 coeurs§7 permanent maximum.",
                "§f- §7Vous aurez §bSpeed II§7 pendant 15 secondes. (§a49 secondes de cooldown§7)",
                "§f- §7Puis, vous aurez §9Slowness II§7 pendant §b5 secondes§7 à la fin de votre sprint.",
                "");

        super.setSecondsDelay(49);
    }

    private final PotionEffect speedEffect = new PotionEffect(PotionEffectType.SPEED, 20*15, 1, false, false);
    private final PotionEffect slowEffect = new PotionEffect(PotionEffectType.SLOW, 20*5, 1, false, false);

    @Override
    public void power(KPlayer kPlayer) {
        kPlayer.getBukkitPlayer().addPotionEffect(speedEffect);
        Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), ()-> {
            if(kPlayer.getBukkitPlayer() != null)
                kPlayer.getBukkitPlayer().addPotionEffect(slowEffect);
        }, 20*11);
    }

    @Override
    public void onEquip(Player player) {
        player.setMaxHealth(18);
        player.getInventory().addItem(new ItemCreator(Material.FEATHER).name("§eSprint Légendaire §8§l▪ §7Clic-droit").get());
    }
}
