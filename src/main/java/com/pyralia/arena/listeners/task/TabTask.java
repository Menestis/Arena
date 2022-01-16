package com.pyralia.arena.listeners.task;

import com.pyralia.arena.ArenaAPI;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ariloxe
 */
public class TabTask extends BukkitRunnable {

    public TabTask() {
        runTaskTimerAsynchronously(ArenaAPI.getApi(), 50, 20);
    }

    private void updateTab(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(ArenaAPI.getApi(),()-> {
            List<String> header = new ArrayList<>();
            String footer;

            int ping = ((CraftPlayer) player).getHandle().ping;
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            double tpsNumber = MinecraftServer.getServer().recentTps[0];
            String tps = String.valueOf(decimalFormat.format(tpsNumber));
            if(tpsNumber > 20)
                tps = "20*";

            header.add(" ");
            header.add("§8§l» §6§lPYRALIA NETWORK §8§l«");
            header.add(" ");
            header.add("     §7Discord §8§l» §epyralia.com/discord");

            footer = "\n§7Ping: §a" + ping + "ms §8| §7Connecté(s): §e" + Bukkit.getOnlinePlayers().size() + " §8| §7TPS: §a" + tps;

            StringBuilder h = new StringBuilder();
            for (String s : header)
                h.append(s).append("\n");


            TabList.send(player, h.toString(), footer);
        });

    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            updateTab(player);
        }
    }

    public static class TabList {

        public static void send(Player player, String header, String footer){
            CraftPlayer craftplayer = (CraftPlayer) player;
            PlayerConnection connection = craftplayer.getHandle().playerConnection;
            IChatBaseComponent headerJSON = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + header +"\"}");
            IChatBaseComponent footerJSON = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + footer +"\"}");
            PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();

            try {
                Field headerField = packet.getClass().getDeclaredField("a");
                headerField.setAccessible(true);
                headerField.set(packet, headerJSON);
                headerField.setAccessible(!headerField.isAccessible());

                Field footerField = packet.getClass().getDeclaredField("b");
                footerField.setAccessible(true);
                footerField.set(packet, footerJSON);
                footerField.setAccessible(!footerField.isAccessible());
            } catch (Exception e) {
                e.printStackTrace();
            }

            connection.sendPacket(packet);
        }
    }
}
