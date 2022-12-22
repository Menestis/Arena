package fr.ariloxe.arena.kits.release;

import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.kits.KitSchedule;
import fr.ariloxe.arena.kits.KitType;
import fr.ariloxe.arena.player.KPlayer;
import fr.ariloxe.arena.utils.ItemCreator;
import fr.ariloxe.arena.utils.particle.DoubleCircleEffect;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author Ariloxe
 */
public class MiphaKit extends KitSchedule {
    public MiphaKit() {
        super("Mipha", KitType.HEALER, new ItemStack(Material.STICK),
                "§8» §7Mode : §bBreath of the Wild",
                "§8» §7Pouvoirs:",
                "§f- §7Vous êtes soigné(e) d’une manière différente selon votre",
                "    §7vie actuelle :",
                "    §7Plus de 5 coeurs : §c2",
                "    §7Entre 5 et 3 : §c3",
                "    §7Entre 5 et 1 : §c4",
                "    §7Moins de 1 : §c6",
                "§8(§639 secondes de cooldown§8)"
        );
        super.setSecondsDelay(39);
    }

    private void safeHeal(Player player, int s){
        player.setHealth(Math.min(player.getHealth() + s, player.getMaxHealth()));
    }

    @Override
    public void power(KPlayer kPlayer) {
        Player player = kPlayer.getBukkitPlayer();

        if(player != null){
            if (player.getHealth() > 10) {
                safeHeal(player, 4);
            } else if (player.getHealth() > 6) {
                safeHeal(player, 6);
            } else if (player.getHealth() > 2) {
                safeHeal(player, 8);
            } else {
                safeHeal(player, 12);
            }


        }
    }

    @Override
    public void onEquip(Player player) {
        player.getInventory().setItem(1, new ItemCreator(Material.STICK).name("§cLance Zora §8§l▪ §7Clic-droit").get());
    }
}
