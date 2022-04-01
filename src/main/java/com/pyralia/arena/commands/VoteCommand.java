package com.pyralia.arena.commands;

import com.pyralia.api.PyraliaAPI;
import com.pyralia.api.player.ICorePlayer;
import com.pyralia.arena.ArenaAPI;
import com.pyralia.arena.maps.SpecialWorld;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * @author Ariloxe
 */
public class VoteCommand extends Command {

    private final static Map<SpecialWorld, Integer> specialMap = new HashMap<>();
    public static Map<SpecialWorld, Integer> getSpecialMap() { return specialMap; }

    private static List<UUID> uuidList = new ArrayList<>();
    public static List<UUID> getUuidList() { return uuidList; }

    public VoteCommand() { super("vote"); }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        Player player = ((Player) commandSender);
        ICorePlayer pyraliaPlayer = PyraliaAPI.getInstance().getPlayerManager().getPlayer(player);
        if(strings.length == 0){
            player.sendMessage("§cMerci de préciser un nom de carte !");
            return true;
        }

        if(getUuidList().contains(player.getUniqueId())){
            player.sendMessage("§cVous avez déjà voté.");
            return true;
        }

        SpecialWorld specialWorld = ArenaAPI.getApi().getGameManager().getWorldManager().getSpecialWorlds().stream().filter(specialWorld1 -> specialWorld1.getRealName().equals(strings[0])).findFirst().get();
        if(specialWorld == null){
            player.sendMessage("§cMerci de préciser un nom de carte valable");
            return true;
        }

        getSpecialMap().replace(specialWorld, getSpecialMap().get(specialWorld), getSpecialMap().get(specialWorld) + 1);
        getUuidList().add(player.getUniqueId());
        player.sendMessage("§6§lPyralia §8§l» §7Vous avez voté pour la carte " + specialWorld.getName());
        return true;
    }
}
