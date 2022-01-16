package com.pyralia.arena.perks;

import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * @author Ariloxe
 */
public abstract class DeathMessagePerks extends Perks {

    private String deathMessage;

    protected DeathMessagePerks(String name, String permission, int price, ItemStack itemStack, List<String> description) {
        super(name, permission, price, itemStack, description);
    }

    public void setDeathMessage(String deathMessage) {
        this.deathMessage = deathMessage;
    }

    public String getDeathMessage() {
        return deathMessage;
    }
}
