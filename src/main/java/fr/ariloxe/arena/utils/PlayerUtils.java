package fr.ariloxe.arena.utils;

import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.player.KPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ariloxe
 */
public class PlayerUtils {

    public static void giveDefaultKit(Player player){
        player.getInventory().clear();
        player.setMaxHealth(20);
        player.setHealth(player.getMaxHealth());

        KPlayer kPlayer = ArenaAPI.getkPlayer(player);

        if(kPlayer.getPlayerInventory() == null){
            player.getInventory().setItem(0, new ItemCreator(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, 3).unbreakable(true).get());
            player.getInventory().setItem(1, new ItemCreator(Material.NETHER_STAR).get());
            player.getInventory().setItem(2, new ItemStack(Material.GOLDEN_APPLE, 9));
            player.getInventory().setItem(3, new ItemCreator(Material.DIAMOND_PICKAXE).enchant(Enchantment.DIG_SPEED, 3).get());
            player.getInventory().setItem(4, new ItemStack(Material.WATER_BUCKET));
            player.getInventory().setItem(5, new ItemStack(Material.COBBLESTONE, 64));
            player.getInventory().setItem(6, new ItemStack(Material.ARROW, 32));
            player.getInventory().setItem(7, new ItemCreator(Material.BOW).enchant(Enchantment.ARROW_DAMAGE, 2).get());
            player.getInventory().setItem(8, new ItemStack(Material.COBBLESTONE, 64));
        } else {
            player.getInventory().setContents(kPlayer.getPlayerInventory().getContents());
        }

        player.getInventory().setHelmet(new ItemCreator(Material.DIAMOND_HELMET).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).unbreakable(true).get());
        player.getInventory().setChestplate(new ItemCreator(Material.DIAMOND_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).unbreakable(true).get());
        player.getInventory().setLeggings(new ItemCreator(Material.IRON_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).unbreakable(true).get());
        player.getInventory().setBoots(new ItemCreator(Material.DIAMOND_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).unbreakable(true).get());

    }
    public static void giveDefaultKitEdit(Player player){
        player.getInventory().clear();
        player.setMaxHealth(20);
        player.setHealth(player.getMaxHealth());

        player.getInventory().setItem(0, new ItemCreator(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, 3).unbreakable(true).get());
        player.getInventory().setItem(1, new ItemCreator(Material.NETHER_STAR).name("§3✦ §bObjet Spécial §3✦").get());
        player.getInventory().setItem(2, new ItemStack(Material.GOLDEN_APPLE, 9));
        player.getInventory().setItem(3, new ItemCreator(Material.DIAMOND_PICKAXE).enchant(Enchantment.DIG_SPEED, 3).get());
        player.getInventory().setItem(4, new ItemStack(Material.WATER_BUCKET));
        player.getInventory().setItem(5, new ItemStack(Material.COBBLESTONE, 64));
        player.getInventory().setItem(6, new ItemStack(Material.ARROW, 32));
        player.getInventory().setItem(7, new ItemCreator(Material.BOW).enchant(Enchantment.ARROW_DAMAGE, 2).get());
        player.getInventory().setItem(8, new ItemStack(Material.COBBLESTONE, 64));

    }

    public static void giveDefaultInventory(Player player){
        player.getInventory().clear();

        ItemStack air = new ItemStack(Material.AIR);
        player.getInventory().setHelmet(air);
        player.getInventory().setChestplate(air);
        player.getInventory().setLeggings(air);
        player.getInventory().setBoots(air);

        player.getInventory().setItem(0, new ItemCreator(Material.REDSTONE_COMPARATOR).name("§b§lSettings §8§l▪ §7§lClic-droit").lore("", "§8» §7Cliquez ici pour changer vos options !").get());
        player.getInventory().setItem(4, new ItemCreator(Material.ENDER_PORTAL_FRAME).name("§a§lChoisir son Kit §8§l▪ §7§lClic-droit").lore("", "§8» §7Cliquez ici pour choisir un kit !").get());
        player.getInventory().setItem(8, new ItemCreator(Material.BED).name("§c§lRetourner au Lobby §8§l▪ §7§lClic-droit").lore("", "§8» §7Cliquez ici pour retourner au lobby !").get());
    }


    public static Player getTarget(Player player, int maxRange, double aiming, boolean wallHack) {
        Player target = null;
        double distance = 0.0D;
        Location playerEyes = player.getEyeLocation();
        Vector direction = playerEyes.getDirection().normalize();
        List<Player> targets = new ArrayList<>();
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online == player ||
                    !online.getWorld().equals(player.getWorld()) || online
                    .getLocation().distanceSquared(playerEyes) > (maxRange * maxRange) || online
                    .getGameMode().equals(GameMode.SPECTATOR))
                continue;
            targets.add(online);
        }
        if (targets.size() > 0) {
            Location loc = playerEyes.clone();
            Vector progress = direction.clone().multiply(0.7D);
            maxRange = 100 * maxRange / 70;
            int loop = 0;
            while (loop < maxRange) {
                loop++;
                loc.add(progress);
                Block block = loc.getBlock();
                if (!wallHack && block.getType().isSolid())
                    break;
                double lx = loc.getX();
                double ly = loc.getY();
                double lz = loc.getZ();
                for (Player possibleTarget : targets) {
                    if (possibleTarget == player)
                        continue;
                    Location testLoc = possibleTarget.getLocation().add(0.0D, 0.85D, 0.0D);
                    double px = testLoc.getX();
                    double py = testLoc.getY();
                    double pz = testLoc.getZ();
                    boolean dX = (Math.abs(lx - px) < 0.7D * aiming);
                    boolean dY = (Math.abs(ly - py) < 1.7D * aiming);
                    boolean dZ = (Math.abs(lz - pz) < 0.7D * aiming);
                    if (dX && dY && dZ) {
                        target = possibleTarget;
                        break;
                    }
                }
                if (target != null) {
                    distance = (loop * 70 / 100);
                    break;
                }
            }
        }
        if (target != null)
            return target;
        return null;
    }

    public static String getCardinalDirection(Player player) {
        double rotation = (player.getLocation().getYaw() - 180) % 360;
        if (rotation < 0)
            rotation += 360.0;

        if (0 <= rotation && rotation < 22.5)
            return "Nord";
        else if (22.5 <= rotation && rotation < 67.5)
            return "Nord";
        else if (67.5 <= rotation && rotation < 112.5)
            return "Est";
        else if (112.5 <= rotation && rotation < 157.5)
            return "Sud";
        else if (157.5 <= rotation && rotation < 202.5)
            return "Sud";
        else if (202.5 <= rotation && rotation < 247.5)
            return "Sud";
        else if (247.5 <= rotation && rotation < 292.5)
            return "Ouest";
        else if (292.5 <= rotation && rotation < 337.5)
            return "Nord";
        else if (337.5 <= rotation && rotation < 360.0)
            return "Nord";
        else
            return "n/A";

    }

    public static Player getNearestPlayer(Player checkNear) {
        Player nearest = null;
        for (Player p : checkNear.getWorld().getPlayers()) {
            if(p == checkNear)
                continue;
            if (p.getLocation().getBlockY() > 100)
                continue;

            if (nearest == null)
                nearest = p;
            else if (p.getLocation().distance(checkNear.getLocation()) < nearest.getLocation().distance(checkNear.getLocation()))
                nearest = p;
        }
        return nearest;
    }
}
