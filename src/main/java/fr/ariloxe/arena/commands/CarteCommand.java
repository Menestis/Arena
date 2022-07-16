package fr.ariloxe.arena.commands;

import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.uis.SelectMapInventory;
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
        if(!player.hasPermission("menestis.mod")){
            player.sendMessage("§3§lMenestis §f» §7Vous devez disposer d'un grade supérieur afin d'user de cette commande (interdit aux joueurs et stars)");
            return true;
        }

        if(canChange){
            selectMapInventory.open(player);
            canChange = false;
            Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), ()-> canChange = true, 20*60);
        } else
            player.sendMessage("§3§lMenestis §f» §7Merci d'attendre une minute pour user de cette commande.");

        return true;
    }
}
