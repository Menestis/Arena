package fr.ariloxe.arena.uis.kits;

import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.kits.KitType;
import fr.ariloxe.arena.utils.PlayerUtils;
import fr.ariloxe.arena.utils.ItemCreator;
import fr.ariloxe.arena.utils.inventory.InvUtils;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Ariloxe
 */
public class SelectKitCreator {

    private KInventory kInventory;
    private final ArenaAPI arenaAPI;
    private final List<Integer> integerList = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 45, 46, 47, 48, 49, 50, 51, 52, 53);
    private final KitType kitType;

    public SelectKitCreator(ArenaAPI instance, KitType kitType){
        this.arenaAPI = instance;
        this.kitType = kitType;
        this.kInventory = new KInventory(KItem.DEFAULT, 54, "§8│ " + kitType.getName());

        KItem glassPane = new KItem(new ItemCreator(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7)).name("§c").get());
        integerList.forEach(integer -> this.kInventory.setElement(integer, glassPane));

        KItem retour = new KItem(new ItemCreator(Material.ARROW).name("§8§l» §7Fermer ce menu §8§l«").lore("", "§8§l» §7Cette option vous permet de §cfermer§7 ce menu.", "§7Et ainsi retourner au menu principal.", "", "§8§l» §7Cliquez pour fermer").get());
        retour.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> ArenaAPI.getApi().getGuiManager().getSelectKitInventory().open(player));
        this.kInventory.setElement(53, retour);
    }

    public void open(Player kPlayer){
        AtomicInteger p = new AtomicInteger(9);
        arenaAPI.getKitManager().getKitList().stream().filter(kit -> kit.getKitType() == this.kitType).forEach(kit -> {
            KItem kItem = new KItem(new ItemCreator(kit.getItemStack()).name("§8§l» §7§l" + kit.getName() + " §8§l«").lore(kit.getDescription()).get());
            kItem.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
                ArenaAPI.getkPlayer(player).setKit(kit);
                player.sendMessage("§3§lMenestis §f» §7Vous avez §e§léquipé avec succès§7 le kit " + ArenaAPI.getkPlayer(player).getKit().getName());
                Location location = ArenaAPI.getApi().getGameManager().getSpecialWorld().getArenaLocations().get(new Random().nextInt(ArenaAPI.getApi().getGameManager().getSpecialWorld().getArenaLocations().size()));

                PlayerUtils.giveDefaultKit(player);
                ArenaAPI.getApi().getGameManager().joinArena(player);
                player.teleport(location);
            });
            this.kInventory.setElement(p.get(), kItem);
            p.getAndIncrement();
        });

        this.kInventory.open(kPlayer);
    }

}
