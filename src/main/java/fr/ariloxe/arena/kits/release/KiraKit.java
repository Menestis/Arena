package fr.ariloxe.arena.kits.release;

import fr.ariloxe.arena.kits.KitSchedule;
import fr.ariloxe.arena.kits.KitType;
import fr.ariloxe.arena.player.KPlayer;
import fr.ariloxe.arena.uis.kits.KiraInventory;
import fr.ariloxe.arena.utils.ItemCreator;
import org.bukkit.Material;
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
                "    §7votre choix dans un rayon de 15 blocs. Si il a moins de 3 coeurs,",
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
        player.getInventory().addItem(new ItemCreator(Material.BOOK).name("§cDeathNote §8§l▪ §7Clic-droit").get());
    }
}
