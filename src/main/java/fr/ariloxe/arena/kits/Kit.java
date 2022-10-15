package fr.ariloxe.arena.kits;

import fr.ariloxe.arena.utils.inventory.InvUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ariloxe
 */
public abstract class Kit {

    private final String name;
    private final ItemStack itemStack;
    private final KitType kitType;
    private final List<String> description;

    public Kit(String name, KitType kitType, ItemStack itemStack, String... description) {
        this.name = name;
        this.kitType = kitType;
        this.itemStack = itemStack;
        this.description = Arrays.asList(description);
    }

    public void onEquip(Player player){}

    public KitType getKitType() {
        return kitType;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public String getName() {
        return name;
    }

    public List<String> getDescription() {
        return description;
    }

}
