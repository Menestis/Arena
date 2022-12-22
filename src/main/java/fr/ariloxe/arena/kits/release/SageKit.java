package fr.ariloxe.arena.kits.release;

import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.kits.KitSchedule;
import fr.ariloxe.arena.kits.KitType;
import fr.ariloxe.arena.player.KPlayer;
import fr.ariloxe.arena.utils.ItemCreator;
import fr.menestis.commons.bukkit.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

/**
 * @author Ariloxe
 */
public class SageKit extends KitSchedule {

    private final PotionEffect speedEffect = new PotionEffect(PotionEffectType.SPEED, 7, 2, false, false);

    public SageKit(){
        super("Sage", KitType.DEFENSIVE, new ItemStack(Material.PACKED_ICE),
                "§8» §7Jeu : §4Valorant",
                "§8» §7Pouvoirs:",
                "§f- §7Vous permet de créer un mur de glace là où vous regardez. (§a46s de cooldown§7)",
                "§f- §7Lors de chaque élimination, votre tueur sera glacé pendant §f3 secondes§7."
        );
        super.setSecondsDelay(31);
    }

    @Override
    public void power(KPlayer kPlayer) {
        Player player = kPlayer.getBukkitPlayer();
        if(player != null){

            Location loc = player.getLocation().add(player.getLocation().getDirection().multiply(2));
            World world = loc.getWorld();

            Location first = new Location(world, loc.getBlockX() + 2, loc.getBlockY() + 5, loc.getBlockZ());
            Location second = new Location(world, loc.getBlockX() - 2, loc.getBlockY(), loc.getBlockZ());

            Cuboid cuboid = new Cuboid(first, second);
            cuboid.getBlocks().forEach(block -> {
                if (block.getType() == Material.AIR)
                    block.setType(Material.PACKED_ICE);

            });

            Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), () -> {
                Cuboid newCuboid = new Cuboid(first, second);
                newCuboid.getBlocks().forEach(block -> {
                    if (block.getType() == Material.PACKED_ICE) {
                        block.setType(Material.AIR);
                    }
                });
            }, 10 * 20);
        }
    }

    @Override
    public void onEquip(Player player){
        player.getInventory().addItem(new ItemCreator(Material.PACKED_ICE).name("§bCréation du Mur §8§l▪ §7Clic-droit").get());
    }
}
