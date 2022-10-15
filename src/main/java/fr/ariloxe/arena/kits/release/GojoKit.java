package fr.ariloxe.arena.kits.release;

import fr.ariloxe.arena.utils.skull.SkullList;
import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.kits.KitSchedule;
import fr.ariloxe.arena.kits.KitType;
import fr.ariloxe.arena.kits.power.Structure;
import fr.ariloxe.arena.player.KPlayer;
import fr.ariloxe.arena.utils.ParticleEffect;
import fr.ariloxe.arena.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ariloxe
 */
public class GojoKit extends KitSchedule {

    private final PotionEffect weaknessEffects = new PotionEffect(PotionEffectType.WEAKNESS,6*20, 0, false, false);
    private final PotionEffect resistanceEffects = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,6*20, 0, false, false);
    private final Structure structure = new Structure(9, 6, ParticleEffect.REDSTONE, Material.SNOW_BLOCK, Material.WOOL, Material.QUARTZ_BLOCK);

    private final List<KPlayer> kPlayerList = new ArrayList<>();

    public GojoKit(){
        super("Satoru Gojo", KitType.DPS, SkullList.LANTERNE.getItemStack(),
                "§8» §7Mode : §5JJK",
                "§8» §7Pouvoirs:",
                "§f- §7Extendez votre territoire et donnez §9Weakness I§7 pendant §b9 secondes§7 aux",
                "§7joueurs dedans en téléportant tous les joueurs dans un rayon de §e10 blocs§7 dans",
                "§7votre extension et de gagner §fRésistance I§7. (§a53s de cooldown§7)",
                "§f- §7Vous aurez §cun coeur§7 permanent supplémentaire.");
        super.setSecondsDelay(53);
    }

    @Override
    public void power(KPlayer kPlayer) {
        if(kPlayerList.contains(kPlayer)){
            kPlayerList.remove(kPlayer);
            kPlayer.getBukkitPlayer().setMaxHealth(22);
        }

        kPlayer.getBukkitPlayer().addPotionEffect(resistanceEffects);
        kPlayer.getBukkitPlayer().playSound(kPlayer.getBukkitPlayer().getLocation(), "pyralia.gojo", 5, 5);
        structure.onEnable(kPlayer);
        Bukkit.getOnlinePlayers().stream().filter(player -> player.getWorld().getName().equals(kPlayer.getBukkitPlayer().getWorld().getName())).filter(player -> player.getLocation().distance(kPlayer.getBukkitPlayer().getLocation()) < 10).filter(player -> player != kPlayer.getBukkitPlayer()).forEach(player -> {
            player.addPotionEffect(weaknessEffects);
            player.teleport(kPlayer.getBukkitPlayer().getLocation());
            player.playSound(kPlayer.getBukkitPlayer().getLocation(), "pyralia.gojo", 5, 5);
        });
    }

    @Override
    public void onEquip(Player player){
        if(!kPlayerList.contains(ArenaAPI.getkPlayer(player)))
            kPlayerList.add(ArenaAPI.getkPlayer(player));
        player.getInventory().addItem(new ItemCreator(Material.NETHER_STAR).name("§5Extension du Territoire §8§l▪ §7Clic-droit").lore("", "§fVous permet d'invoquer une extension du territoire.").get());
    }
}
