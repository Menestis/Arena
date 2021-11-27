package com.pyralia.arena.scoreboard;

import com.pyralia.arena.Main;
import com.pyralia.arena.scoreboard.tools.ObjectiveSign;
import com.pyralia.core.spigot.CorePlugin;
import com.pyralia.core.spigot.player.PyraliaPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.UUID;

/*
 * This file is part of Hub.
 *
 * Hub is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Hub is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Hub.  If not, see <http://www.gnu.org/licenses/>.
 */
public class PersonalScoreboard {
    private final Main hub;
    private final UUID player;
    private final ObjectiveSign objectiveSign;

    PersonalScoreboard(Main hub, Player player) {
        this.hub = hub;
        this.player = player.getUniqueId();

        this.objectiveSign = new ObjectiveSign("UHCHost", "UHCHost");
        this.objectiveSign.addReceiver(player);
    }

    public void onLogout() {
        this.objectiveSign.removeReceiver(this.hub.getServer().getOfflinePlayer(this.player));
    }


    public void setLines(String ip) {
        Player player = Bukkit.getPlayer(this.player);

        this.objectiveSign.setDisplayName("§6§lPyralia - Arène");
        this.objectiveSign.setLine(0, "§a");
        this.objectiveSign.setLine(1, "§8» §7Kills : §f" + player.getStatistic(Statistic.PLAYER_KILLS));
        this.objectiveSign.setLine(2, "§8» §7Morts : §f" + player.getStatistic(Statistic.DEATHS));
        this.objectiveSign.setLine(3, "§c");
        this.objectiveSign.setLine(4, "§8» §7Connecté(s) : §f" + Bukkit.getOnlinePlayers().size());
        this.objectiveSign.setLine(5, "§d");
        this.objectiveSign.setLine(6, "§8§l❯ §6" + ip);

        this.objectiveSign.updateLines();
    }
}
