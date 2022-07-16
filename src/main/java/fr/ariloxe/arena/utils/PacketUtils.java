package fr.ariloxe.arena.utils;

import fr.ariloxe.arena.utils.reflection.Reflection;
import org.bukkit.entity.Player;

/**
 * @author Ariloxe
 */
public class PacketUtils {

    public static void send(Player player, Object packet) {
        if (player == null || packet == null)
            return;
        try {
            Object craftPlayer = Reflection.PackageType.CRAFTBUKKIT_ENTITY.getClass("CraftPlayer").cast(player);
            Object handle = player.getClass().getMethod("getHandle", new Class[0]).invoke(craftPlayer, new Object[0]);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", new Class[] { Reflection.PackageType.MINECRAFT_SERVER.getClass("Packet") }).invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
