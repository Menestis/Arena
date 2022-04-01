package com.pyralia.arena.uis.perks;

import com.pyralia.api.PyraliaAPI;
import com.pyralia.api.player.ICorePlayer;
import com.pyralia.arena.ArenaAPI;
import com.pyralia.arena.perks.DeathMessagePerks;
import com.pyralia.arena.player.KPlayer;
import com.pyralia.api.utils.ItemCreator;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionAttachment;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Ariloxe
 */
public class PerksDeathMessageInventory {

    private KInventory kInventory;
    private final ArenaAPI arenaAPI;
    private final List<Integer> integerList = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 45, 46, 47, 48, 49, 50, 51, 52, 53);

    public PerksDeathMessageInventory(ArenaAPI instance){
        this.arenaAPI = instance;
        this.kInventory = new KInventory(KItem.DEFAULT, 54, "§8│ §6§lMessages de Mort");

        KItem glassPane = new KItem(new ItemCreator(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7)).name("§c").get());
        integerList.forEach(integer -> this.kInventory.setElement(integer, glassPane));

        KItem retour = new KItem(new ItemCreator(Material.ARROW).name("§8§l» §7Fermer ce menu §8§l«").lore("", "§8§l» §7Cette option vous permet de §cfermer§7 ce menu.", "§7Et ainsi retourner au menu principal.", "", "§8§l» §7Cliquez pour fermer").get());
        retour.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> ArenaAPI.getApi().getGuiManager().getPerksMainInventory().open(player));
        this.kInventory.setElement(53, retour);
    }

    public void open(Player aPlayer){
        AtomicInteger p = new AtomicInteger(9);
        arenaAPI.getPerksManager().getPerksList().stream().filter(perks -> perks instanceof DeathMessagePerks).forEach(perks -> {
            KItem kItem = new KItem(new ItemCreator(perks.getItemStack()).name("§8§l» §7§l" + perks.getName() + " §8§l«").lore(perks.getDescription()).get());
            kItem.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
                if(perks.getPermission() != null){
                    if(!player.hasPermission(perks.getPermission())){
                        ICorePlayer pyraliaPlayer = PyraliaAPI.getInstance().getPlayerManager().getPlayer(player);
                        if(pyraliaPlayer.getCredits() >= perks.getPrice()){
                            player.sendMessage("§6§lPyralia §8§l» §aVous avez acheté le cosmétique " + perks.getName());
                            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);

                            pyraliaPlayer.setCredits(pyraliaPlayer.getCredits() - perks.getPrice());

                            pyraliaPlayer.addPermission(perks.getPermission());
                            PermissionAttachment attachment = player.addAttachment(arenaAPI);
                            attachment.setPermission(perks.getPermission(), true);
                        }

                        return;
                    }
                }

                KPlayer kPlayer = ArenaAPI.getkPlayer(player);
                if(kPlayer.getkPlayerPerks().getDeathMessagePerks() == perks){
                    player.sendMessage("§6§lPyralia §8§l» §cVous avez déjà ce message de mort activé.");
                    return;
                }

                kPlayer.getkPlayerPerks().setDeathMessagePerks((DeathMessagePerks)perks);
                player.sendMessage("§6§lPyralia §8§l» §7Vous avez activé le cri " + perks.getName());
            });
            this.kInventory.setElement(p.get(), kItem);
            p.getAndIncrement();
        });

        this.kInventory.open(aPlayer);
    }

}
