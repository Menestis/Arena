package com.pyralia.arena.kits;

import com.pyralia.arena.Main;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author Ariloxe
 */
public abstract class Kit {

    private final String name;
    private final ItemStack itemStack;
    private final List<String> description;

    public Kit(String name, ItemStack itemStack, String... description) {
        this.name = name;
        this.itemStack = itemStack;
        this.description = Arrays.asList(description);
    }

    public void onEquip(Player player){
        player.teleport(Main.getInstance().getGameManager().getLocationList().get(new Random().nextInt(Main.getInstance().getGameManager().getLocationList().size())));
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
