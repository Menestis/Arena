package com.pyralia.arena.perks;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ariloxe
 */
public abstract class Perks {

    private final String name;
    private final String permission;
    private final int price;
    private final ItemStack itemStack;
    private final List<String> description;

    protected Perks(String name, String permission, int price, ItemStack itemStack, List<String> description) {
        this.name = name;
        this.permission = permission;
        this.price = price;
        this.itemStack = itemStack;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getPrice() {
        return price;
    }

    public List<String> getDescription() {
        List<String> desc = new ArrayList<>(description);
        desc.add("");
        desc.add("§7Prix §f§l» §e" + price + " ⛃");
        desc.add("");
        desc.add("§8§l» §7Cliquez pour jouer avec ce kit.");

        return description;
    }
}
