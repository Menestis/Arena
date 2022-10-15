package fr.ariloxe.arena.utils.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author Ariloxe
 */
public class InvUtils {

    public static void removeFromInventory(Player player, Material material){
        for (ItemStack content : player.getInventory().getContents()) {
            if(content == null || content.getType() == Material.AIR)
                continue;

            if(content.getType() == material)
                player.getInventory().remove(content);
        }
    }

    public static void removeFromInventory(Player player, Material material, int amount){
        for (ItemStack content : player.getInventory().getContents()) {
            if(content == null || content.getType() == Material.AIR)
                continue;

            if(content.getType() == material){
                if(content.getAmount() > amount)
                    content.setAmount(content.getAmount() - amount);
                else
                    player.getInventory().remove(content);
            }
        }
    }

    public static int getNumberOfItemFromInventory(Player player, Material material){
        int contentValue = 0;
        for (ItemStack content : player.getInventory().getContents()) {
            if(content == null || content.getType() == Material.AIR)
                continue;

            if(content.getType() == material)
                contentValue = contentValue + content.getAmount();

        }

        return contentValue;
    }

    public static boolean hasItemWithName(Player player, Material material, String name){
        for (ItemStack content : player.getInventory().getContents()) {
            if(content == null || content.getType() == Material.AIR || content.getType() != material)
                continue;

            if(content.hasItemMeta() && content.getItemMeta().hasDisplayName() && content.getItemMeta().getDisplayName().equals(name))
                return true;
        }

        return false;
    }

}