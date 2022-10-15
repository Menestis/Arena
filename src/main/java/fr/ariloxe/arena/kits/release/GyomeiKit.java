package fr.ariloxe.arena.kits.release;

import fr.ariloxe.arena.utils.ItemCreator;
import fr.ariloxe.arena.kits.KitSchedule;
import fr.ariloxe.arena.kits.KitType;
import fr.ariloxe.arena.player.KPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @author Ariloxe
 */
public class GyomeiKit extends KitSchedule {
    public GyomeiKit() {
        super("Gyomei", KitType.DPS, new ItemStack(Material.STONE_AXE),
                "§8» §7Mode : §3Démon-Slayer",
                "§8» §7Pouvoirs:",
                "§f- §7En cliquant avec votre hache, vous obtiendrez l'effet de.",
                "    §fRésistance I§7 et les joueurs autour de vous auront l'effet",
                "    §7de §anausée§7, pendant §610s§7 (§a42 secondes de cooldown§7)"
        );
        super.setSecondsDelay(42);
    }

    private final PotionEffect potionEffect = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*10, 0, false, false);
    private final PotionEffect potionEffect2 = new PotionEffect(PotionEffectType.CONFUSION, 20*10, 0, false, false);


    @Override
    public void power(KPlayer kPlayer) {
        Player player = kPlayer.getBukkitPlayer();

        if(player != null){
            player.addPotionEffect(potionEffect);
            player.getWorld().getNearbyEntities(player.getLocation(), 10, 10, 10).stream().filter(entity -> entity instanceof Player).filter(entity -> entity != player).forEach(entity -> {
               ((Player) entity).addPotionEffect(potionEffect2);
            });
        }
    }

    @Override
    public void onEquip(Player player) {
        player.getInventory().addItem(new ItemCreator(Material.STONE_AXE).name("§8Confusion §8§l▪ §7Clic-droit").get());
    }
}
