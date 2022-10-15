package fr.ariloxe.arena.commands;

import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.player.KPlayer;
import fr.ariloxe.arena.utils.PlayerUtils;
import fr.ariloxe.arena.utils.inventory.PlayerInventory;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Ariloxe
 */
public class ArenaCommand extends Command {

    public ArenaCommand() {
        super("arena");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        Player player = ((Player) commandSender);
        KPlayer kPlayer = ArenaAPI.getkPlayer(player);

        if(strings.length == 0){
            player.sendMessage("§c/arena <join|leave|settings>");
            return false;
        } else if(strings[0].equalsIgnoreCase("cancel") && ArenaAPI.getApi().getGameManager().getEditingList().contains(player.getUniqueId())){
            ArenaAPI.getApi().getGameManager().getEditingList().remove(player.getUniqueId());
            player.sendMessage("");
            player.sendMessage("§b§lMenestis §f» §7Vous avez quitté le mode édition de votre inventaire.");
            player.sendMessage("   §8➥ §7Ce dernier §cn'a pas§7 été sauvegardé.");
            player.sendMessage("");

            player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
            PlayerUtils.giveDefaultInventory(player);


        } else if(strings[0].equalsIgnoreCase("save") && ArenaAPI.getApi().getGameManager().getEditingList().contains(player.getUniqueId())){
            ArenaAPI.getApi().getGameManager().getEditingList().remove(player.getUniqueId());
            player.sendMessage("");
            player.sendMessage("§b§lMenestis §f» §7Vous avez quitté le mode édition de votre inventaire.");
            player.sendMessage("   §8➥ §7Ce dernier §aa bien§7 été sauvegardé.");
            player.sendMessage("");

            PlayerInventory playerInventory = new PlayerInventory(player.getInventory().getContents());
            playerInventory.setChanged(true);
            kPlayer.setPlayerInventory(playerInventory);

            player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1, 1);
            PlayerUtils.giveDefaultInventory(player);
        }

        return false;
    }
}
