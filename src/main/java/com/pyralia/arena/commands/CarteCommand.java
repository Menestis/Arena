package com.pyralia.arena.commands;

import com.pyralia.api.PyraliaAPI;
import com.pyralia.api.player.ICorePlayer;
import com.pyralia.api.rank.Rank;
import com.pyralia.arena.ArenaAPI;
import com.pyralia.arena.uis.SelectMapInventory;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Ariloxe
 */
public class CarteCommand extends Command {

    private final SelectMapInventory selectMapInventory;
    private boolean canChange = true;

    public CarteCommand() {
        super("carte");
        this.selectMapInventory = new SelectMapInventory(ArenaAPI.getApi());
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        Player player = ((Player) commandSender);
        ICorePlayer pyraliaPlayer = PyraliaAPI.getInstance().getPlayerManager().getPlayer(player);
        if(pyraliaPlayer.getRank() == Rank.JOUEUR){
            player.sendMessage("§6§lPyralia §8§l» §7Vous devez disposer d'un grade supérieur afin d'user de cette commande (interdit aux joueurs et stars)");
            return true;
        }

        if(canChange){
            selectMapInventory.open(player);
            canChange = false;
            Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), ()-> canChange = true, 20*60);
        } else
            player.sendMessage("§6§lPyralia §8§l» §7Merci d'attendre une minute pour user de cette commande.");

        return true;
    }
}
