package com.pyralia.arena.kits.release;

import com.pyralia.arena.ArenaAPI;
import com.pyralia.arena.kits.KitSchedule;
import com.pyralia.arena.kits.KitType;
import com.pyralia.arena.player.KPlayer;
import com.pyralia.arena.utils.PlayerUtils;
import com.pyralia.core.common.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

/**
 * @author Ariloxe
 */
public class FurutoKit extends KitSchedule {
    public FurutoKit() {
        super("Furûto", KitType.DEFENSIVE, new ItemStack(Material.DIAMOND),
                "§8» §7Mode : §3Démon-Slayer",
                "§8» §7Pouvoirs:",
                "§f- §7En cliquant sur un joueur avec votre diamant, vous mélangerez son inventaire.",
                "    §7(§a31 secondes de cooldown§7)"
        );
        super.setSecondsDelay(31);
    }

    @Override
    public void power(KPlayer kPlayer) {
        Player player = kPlayer.getBukkitPlayer();

        if(player != null){
            Player target = PlayerUtils.getTarget(player, 60, 1.4D, true);
            if (target != null) {
                swapInv(target);
                target.sendMessage("§6§lPyralia §8§l» §cVotre inventaire devient confus..");
                player.sendMessage("§6§lPyralia §8§l» §aVous avez mélangé l'inventaire de §l" + target.getName() + " §a!");
            } else {
                Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), ()->{
                    getkPlayerRemainsList().remove(kPlayer);
                    getPlayerIntegerMap().remove(kPlayer);
                }, 10);
            }
        }
    }

    @Override
    public void onEquip(Player player) {
        player.getInventory().setItem(1, new ItemCreator(Material.DIAMOND).name("§7Flûte de Furûto").get());
    }

    private void swapInv(Player player) {
        Random random = new Random();
        List<Integer> slots = new ArrayList<>();
        List<ItemStack> items = new ArrayList<>(Arrays.asList(player.getInventory().getContents()));
        for (int i = 0; i < 36; i++)
            slots.add(Integer.valueOf(i));
        Map<Integer, ItemStack> map = new HashMap<>();
        while (!items.isEmpty())
            map.put(slots.remove(random.nextInt(slots.size())), items.remove(random.nextInt(items.size())));
        for (Map.Entry<Integer, ItemStack> entry : map.entrySet())
            player.getInventory().setItem(entry.getKey(), entry.getValue());
    }
}
