package fr.ariloxe.arena.kits.release;

import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.kits.KitSchedule;
import fr.ariloxe.arena.kits.KitType;
import fr.ariloxe.arena.player.KPlayer;
import fr.ariloxe.arena.utils.particle.WingsEffect;
import fr.ariloxe.arena.utils.ItemCreator;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author Ariloxe
 */
public class DemonBatKit extends KitSchedule {
    public DemonBatKit() {
        super("Démon Bat", KitType.DEFENSIVE, new ItemStack(Material.DRAGON_EGG),
                "§8» §7Mode : §cChainsawMan-UHC",
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
            new WingsEffect(20 * 10, EnumParticle.SMOKE_NORMAL).start(player);
        }
    }

    @Override
    public void onEquip(Player player) {
        player.setMaxHealth(16);
        player.getInventory().addItem(new ItemCreator(Material.FEATHER).name("§fFly §8§l▪ §7Clic-droit").get());
    }
}
