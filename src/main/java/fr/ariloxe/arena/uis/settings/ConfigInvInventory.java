package fr.ariloxe.arena.uis.settings;

import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.uis.KInventoryGui;
import fr.ariloxe.arena.utils.PlayerUtils;
import fr.blendman.magnet.api.server.ServerCacheHandler;
import fr.blendman.skynet.models.ServerLoginPlayerInfo;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import fr.menestis.commons.bukkit.ItemCreator;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Map;

/**
 * @author Ariloxe
 */
public class ConfigInvInventory implements KInventoryGui {

    private final KInventory kInventory = new KInventory(54, "§6Configuration de l'inventaire");

    public ConfigInvInventory(SettingsInventory settingsInventory){
        KItem retour = new KItem(new ItemCreator(Material.ARROW).name("§7▎ §cRetour §7▎").lore("", "§fVous permet de retourner au", "§fmenu §cprécédent§f.", "").get());
        retour.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) ->settingsInventory.open(player));
        kInventory.setElement(48, retour);

        KItem delete = new KItem(player -> new ItemCreator(Material.BARRIER).name("§cSupprimer votre configuration").lore(
                        "",
                        "§fVous permet de supprimer votre",
                        "§finventaire par défaut.",
                        "",
                        "" + (ArenaAPI.getkPlayer(player).getPlayerInventory() != null ? "§8• §7Cliquez pour §csupprimer §7votre inventaire." : "§4• §cVous n'avez §lpas§c d'inventaire par défaut.")).get());
        delete.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            player.closeInventory();

            if(player.getLocation() != null){
                player.sendMessage("§cça arrive bientôt (promis)");
                return;
            }

            if(ArenaAPI.getkPlayer(player).getPlayerInventory() != null){
                ArenaAPI.getkPlayer(player).setPlayerInventory(null);

                ServerLoginPlayerInfo playerInfo = ServerCacheHandler.ServerCacheHandlerStore.getServerCacheHandler().getInfo(player.getUniqueId());
                if(playerInfo.getProperties().containsKey("ARENA_STUFF")){
                    Map<String, String> properties = ServerCacheHandler.ServerCacheHandlerStore.getServerCacheHandler().getInfo(player.getUniqueId()).getProperties();
                    properties.remove("ARENA_STUFF");
                    ServerCacheHandler.ServerCacheHandlerStore.getServerCacheHandler().getInfo(player.getUniqueId()).setProperties(properties);
                }

                player.sendMessage("");
                player.sendMessage("§b§lMenestis §f» §7Vous demandé la suppression de votre inventaire.");
                player.sendMessage("   §8➥ §7Ce dernier §aa bien§7 été écrasé.");
                player.sendMessage("");

                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
            }
        });

        KItem config = new KItem(player -> new ItemCreator(Material.CHEST).name("§eChanger votre configuration").lore(
                "",
                "§fVous permet de changer votre",
                "§finventaire par défaut.",
                "",
                "§8┃ " + (ArenaAPI.getkPlayer(player).getPlayerInventory() != null ? "§aconfiguré §2(§a§l✔§2)" : "§cnon-configuré §4(§c§l✘§4)"),
                "",
                "§8• §7Cliquez pour §eéditer §7votre inventaire.").get());

        config.addCallback((kInventoryRepresentation, itemStack, player, kInventoryClickContext) -> {
            player.closeInventory();

            if(player.getLocation() != null){
                player.sendMessage("§cça arrive bientôt (promis)");
                return;
            }

            player.sendMessage("");
            player.sendMessage("§8» §6Vous configurez actuellement votre inventaire par défaut.");
            player.sendMessage("");

            ClickEvent click = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/arena save");
            HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Cliquez pour §e§oenregistrer§7 l'inventaire.").create());
            TextComponent text = new TextComponent("§8» §a§l[ENREGISTRER]");
            text.setClickEvent(click);
            text.setHoverEvent(hoverEvent);
            player.spigot().sendMessage(text);

            ClickEvent click2 = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/arena cancel");
            HoverEvent hoverEvent2 = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Cliquez pour §c§oannuler§7 votre configuration.").create());
            TextComponent text2 = new TextComponent("§8» §c§l[ANNULER]");
            text2.setClickEvent(click2);
            text2.setHoverEvent(hoverEvent2);
            player.spigot().sendMessage(text2);

            PlayerUtils.giveDefaultKitEdit(player);
            ArenaAPI.getApi().getGameManager().getEditingList().add(player.getUniqueId());
        });

        KItem soon = new KItem(new ItemCreator(Material.FLOWER_POT_ITEM).name("§aProchainement...").lore("", "§a§kLLLLLLLLLLLLL§r", "").get());

        kInventory.setElement(20, delete);
        kInventory.setElement(22, config);
        kInventory.setElement(24, soon);
    }

    @Override
    public void open(Player player) {
        this.kInventory.open(player);
    }
}
