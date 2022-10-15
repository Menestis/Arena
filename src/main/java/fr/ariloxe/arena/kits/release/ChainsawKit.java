package fr.ariloxe.arena.kits.release;

import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.kits.KitSchedule;
import fr.ariloxe.arena.kits.KitType;
import fr.ariloxe.arena.player.KPlayer;
import fr.ariloxe.arena.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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
        super("Chainsaw-Man", KitType.DPS, new ItemStack(Material.QUARTZ_BLOCK),
                "§8» §7Mode : §cChainsawMan-UHC",
                "§8» §7Pouvoirs:",
                "§f- §7Vous pourrez vous propulser d'une 10aine de blocs. (§a16s de cooldown§7)",
                "§f- §7Vous pourrez changer, quand vous voudrez, de mode:",
                "  §8» §7Mode Chainsaw:",
                "   §f- §7Vous avez une tête spéciale, donc plus de casque en diamant.",
                "   §f- §7Vous gagnez les effets §bSpeed I§7 et §5Force I§7.",
                "  §8» §7Mode Normal:",
                "   §f- §7Vous n'avez plus la tête spéciale, et donc de nouveau votre casque.",
                "   §f- §7Vous n'aurez plus vos effets.",
                "",
                "§f- §aLe pack de texture de GoldenUHC fonctionne.",
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
        Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), ()-> {
            player.setMaxHealth(22);
            player.setHealth(player.getMaxHealth());

            player.getInventory().addItem(new ItemCreator(Material.ENDER_PORTAL_FRAME).name("§eChangement de Forme §8§l▪ §7Clic-droit").get());
        }, 3);
    }
}
