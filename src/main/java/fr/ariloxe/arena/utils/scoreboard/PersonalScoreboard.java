package fr.ariloxe.arena.utils.scoreboard;

import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.player.KPlayer;
import fr.ariloxe.arena.utils.scoreboard.tools.ObjectiveSign;
import org.bukkit.Bukkit;
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
    private final ArenaAPI hub;
    private final UUID player;
    private final ObjectiveSign objectiveSign;

    PersonalScoreboard(ArenaAPI hub, Player player) {
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
        KPlayer kPlayer = ArenaAPI.getkPlayer(player);

        this.objectiveSign.setDisplayName("§3§lMenestis");
        this.objectiveSign.setLine(0, "§a");
        this.objectiveSign.setLine(1, " §8┃ §cCrossteam interdit");
        this.objectiveSign.setLine(5, "§b");
        this.objectiveSign.setLine(6, " §8┃ §7Kit : §f" + kPlayer.getKit().getName());
        this.objectiveSign.setLine(7, " §8┃ §7Carte : §a" + hub.getGameManager().getSpecialWorld().getName());
        this.objectiveSign.setLine(8, "§c");
        this.objectiveSign.setLine(9, " §8┃ §7Connecté(s) : §f" + Bukkit.getOnlinePlayers().size());
        this.objectiveSign.setLine(10, "§d");
        this.objectiveSign.setLine(11, "§8§l❯ §b" + ip);

        this.objectiveSign.updateLines();
    }
}
