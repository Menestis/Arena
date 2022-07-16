package fr.ariloxe.arena.utils.packets;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Title {

    private String t;
    private String st;

    public Title(String t, String st) {
        this.t = t;
        this.st = st;
    }

    public void sendToPlayer(Player p) {
        TitleManager.sendTitle(p, 0, 30, 0, t, st);
    }

    public void sendToAll() {
        for (Player p : Bukkit.getServer().getOnlinePlayers())
            TitleManager.sendTitle(p, 0, 30, 0, t, st);

    }

}
