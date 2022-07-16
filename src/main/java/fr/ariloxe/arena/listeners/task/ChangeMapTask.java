package fr.ariloxe.arena.listeners.task;

import fr.ariloxe.arena.utils.Tasks;
import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.commands.VoteCommand;
import fr.ariloxe.arena.maps.SpecialWorld;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Ariloxe
 */
public class ChangeMapTask extends BukkitRunnable {

    public ChangeMapTask() {
        runTaskTimer(ArenaAPI.getApi(), 20*10*60, 20*10*60);
    }

    @Override
    public void run() {
        Bukkit.broadcastMessage("§3§lMenestis §f» §7Vote pour pour changer d'arène: §8(§6Automatique§8)");
        Bukkit.broadcastMessage("§7Cliquez sur le nom de la carte pour laquelle vous voulez voter.");
        Bukkit.broadcastMessage("§7Fin du vote dans: §e1 minute");
        Bukkit.broadcastMessage("");
        VoteCommand.getSpecialMap().clear();
        VoteCommand.getUuidList().clear();

        ArenaAPI.getApi().getGameManager().getWorldManager().getSpecialWorlds().forEach(specialWorld -> VoteCommand.getSpecialMap().put(specialWorld, 0));

        Bukkit.getOnlinePlayers().forEach(player -> {
            ArenaAPI.getApi().getGameManager().getWorldManager().getSpecialWorlds().forEach(specialWorld -> {
                ClickEvent click = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/vote " + specialWorld.getRealName());
                HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Cliquer sur ce message vous permet de voter pour la carte " + specialWorld.getName()).create());
                TextComponent text = new TextComponent("§8» " + specialWorld.getName());
                text.setClickEvent(click);
                text.setHoverEvent(hoverEvent);
                player.spigot().sendMessage(text);
            });
        });
        Bukkit.broadcastMessage("");

        Tasks.runLater(()->{
            Bukkit.broadcastMessage("§7Fin du vote dans: §e30 secondes.");
            Tasks.runLater(()->{
                Bukkit.broadcastMessage("§3§lMenestis §f» §7Vote pour le changement d'arène §cterminé§7.");
                Bukkit.broadcastMessage("");
                SpecialWorld specialWorld = getHighestWorld();
                if(specialWorld == null){
                    Bukkit.broadcastMessage("§cAucune carte n'a été choisie.");
                    return;
                }

                Bukkit.broadcastMessage("§7La carte choisie est: " + specialWorld.getName() + "§7, avec un montant total de §c" + getHighestVote() + "§7 votes !");
                Bukkit.broadcastMessage("");

                ArenaAPI.getApi().getGameManager().getWorldManager().switchWorld(specialWorld);
            }, 20*30);
        }, 20*30);

    }

    public int getHighestVote() {
        int highest = 0;
        if (!VoteCommand.getSpecialMap().isEmpty())
            for (SpecialWorld key : VoteCommand.getSpecialMap().keySet()) {
                if (VoteCommand.getSpecialMap().get(key) >= highest)
                    highest = VoteCommand.getSpecialMap().get(key);
            }
        return highest;
    }

    public SpecialWorld getHighestWorld() {
        SpecialWorld mapName = null;
        if (!VoteCommand.getSpecialMap().isEmpty())
            for (SpecialWorld maps : VoteCommand.getSpecialMap().keySet()) {
                if ((VoteCommand.getSpecialMap().get(maps)) == getHighestVote())
                    mapName = maps;
            }
        return mapName;
    }

}
