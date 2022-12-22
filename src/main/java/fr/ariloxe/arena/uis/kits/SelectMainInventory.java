package fr.ariloxe.arena.uis.kits;

import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.kits.Kit;
import fr.ariloxe.arena.kits.KitType;
import fr.ariloxe.arena.utils.PlayerUtils;
import fr.ariloxe.arena.utils.skull.SkullList;
import fr.ariloxe.arena.utils.ItemCreator;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author Ariloxe
 */
public class SelectMainInventory {

    private KInventory kInventory;
    private final ArenaAPI arenaAPI;

    public SelectMainInventory(ArenaAPI instance){
        this.arenaAPI = instance;
        this.kInventory = new KInventory(KItem.DEFAULT, 54, "§8│ §6§lCatégories");

        List<Integer> integerList = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 45, 46, 47, 48, 49, 50, 51, 52, 53);
        KItem glassPane = new KItem(new ItemCreator(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7)).name("§c").get());
        integerList.forEach(integer -> this.kInventory.setElement(integer, glassPane));

        KItem reUse = new KItem(new ItemCreator(SkullList.BLACK_BALL.getItemStack()).name("§8§l» §7Ré-utiliser §8§l«").lore("", "§8§l» §7Cette option vous permet d'utiliser à nouveau votre", "§7kit actuel.", "", "§8§l» §7Cliquez pour choisir.").get());
        reUse.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            player.sendMessage("§3§lMenestis §f» §7Vous avez §e§léquipé avec succès§7 le kit " + ArenaAPI.getkPlayer(player).getKit().getName());
            Location location = ArenaAPI.getApi().getGameManager().getSpecialWorld().getArenaLocations().get(new Random().nextInt(ArenaAPI.getApi().getGameManager().getSpecialWorld().getArenaLocations().size()));

            PlayerUtils.giveDefaultKit(player);
            ArenaAPI.getApi().getGameManager().joinArena(player);
            player.teleport(location);
        });
        this.kInventory.setElement(3, reUse);

        KItem randomUse = new KItem(new ItemCreator(SkullList.WHITE_BALL.getItemStack()).name("§8§l» §7Kit Aléatoire §8§l«").lore("", "§8§l» §7Cette option vous permet d'utiliser un kit aléatoire", "§7dans tous ceux disponible.", "", "§8§l» §7Cliquez pour choisir.").get());
        randomUse.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            Kit kit = ArenaAPI.getApi().getKitManager().getKitList().get(new Random().nextInt(ArenaAPI.getApi().getKitManager().getKitList().size()));

            player.sendMessage("§3§lMenestis §f» §7Vous avez §e§léquipé avec succès§7 le kit " + kit.getName());
            Location location = ArenaAPI.getApi().getGameManager().getSpecialWorld().getArenaLocations().get(new Random().nextInt(ArenaAPI.getApi().getGameManager().getSpecialWorld().getArenaLocations().size()));

            PlayerUtils.giveDefaultKit(player);
            ArenaAPI.getApi().getGameManager().joinArena(player);
            player.teleport(location);
        });
        this.kInventory.setElement(5, randomUse);

        KItem retour = new KItem(new ItemCreator(Material.ARROW).name("§8§l» §7Fermer ce menu §8§l«").lore("", "§8§l» §7Cette option vous permet de §cfermer§7 ce menu.", "", "§8§l» §7Cliquez pour fermer").get());
        retour.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> player.closeInventory());
        this.kInventory.setElement(53, retour);


        KItem tank = new KItem(new ItemCreator(SkullList.RED_BALL.getItemStack()).name("§8§l» §c§lTank §8§l«").lore(KitType.TANK.getDescription()).get());
        tank.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> KitType.TANK.getSelectKitCreator().open(player));

        KItem dps = new KItem(new ItemCreator(SkullList.ORANGE_BALL.getItemStack()).name("§8§l» §6§lDPS §8§l«").lore(KitType.DPS.getDescription()).get());
        dps.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> KitType.DPS.getSelectKitCreator().open(player));

        KItem defensif = new KItem(new ItemCreator(SkullList.BLUE_BALL.getItemStack()).name("§8§l» §b§lDéfensif §8§l«").lore(KitType.DEFENSIVE.getDescription()).get());
        defensif.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> KitType.DEFENSIVE.getSelectKitCreator().open(player));

        KItem healer = new KItem(new ItemCreator(SkullList.PINK_BALL.getItemStack()).name("§8§l» §d§lHealer §8§l«").lore(KitType.HEALER.getDescription()).get());
        healer.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> KitType.HEALER.getSelectKitCreator().open(player));

        this.kInventory.setElement(21, tank);
        this.kInventory.setElement(23, dps);

        this.kInventory.setElement(28, defensif);
        this.kInventory.setElement(34, healer);
    }

    public void open(Player kPlayer){
        this.kInventory.open(kPlayer);
    }


}
