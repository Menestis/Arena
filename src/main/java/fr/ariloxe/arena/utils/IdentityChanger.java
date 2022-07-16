package fr.ariloxe.arena.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.utils.reflection.Reflection;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * @author Ariloxe
 */
public class IdentityChanger {

    public static void changeSkin(final Player player, final Property skin) {

        final CraftPlayer craftPlayer = (CraftPlayer) player;
        final EntityPlayer entity = ((CraftPlayer) player).getHandle();
        final GameProfile profile = craftPlayer.getProfile();
        final int id = craftPlayer.getEntityId();
        final PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(id);
        sendPacket(destroy);
        final PacketPlayOutPlayerInfo remove = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entity);
        sendPacket(remove);
        profile.getProperties().removeAll("textures");
        profile.getProperties().put("textures", skin);
        new BukkitRunnable() {
            public void run() {
                final PacketPlayOutPlayerInfo tabInfoAdd = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entity);
                sendPacket(tabInfoAdd);
                final PacketPlayOutNamedEntitySpawn spawn = new PacketPlayOutNamedEntitySpawn(entity);
                sendPacket(spawn, player);
                for (int i = 1; i <= 4; ++i) {
                    final PacketPlayOutEntityEquipment equip = new PacketPlayOutEntityEquipment(id, i, entity.getEquipment(i));
                    sendPacket(equip);
                }
            }
        }.runTaskLater(ArenaAPI.getApi(), 10L);
    }

    public static void changeSkinForPlayer(final Player player, final Property skin, final Player forPlayer, final Property original) {
        final CraftPlayer craftPlayer = (CraftPlayer) player;
        final EntityPlayer entity = ((CraftPlayer) player).getHandle();
        final GameProfile profile = craftPlayer.getProfile();
        final int id = craftPlayer.getEntityId();
        final PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(id);
        Reflection.sendPacket(forPlayer, destroy);
        final PacketPlayOutPlayerInfo remove = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entity);
        Reflection.sendPacket(forPlayer, remove);
        profile.getProperties().removeAll("textures");
        profile.getProperties().put("textures", skin);
        new BukkitRunnable() {
            public void run() {
                final PacketPlayOutPlayerInfo tabInfoAdd = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entity);
                Reflection.sendPacket(forPlayer, tabInfoAdd);
                final PacketPlayOutNamedEntitySpawn spawn = new PacketPlayOutNamedEntitySpawn(entity);
                Reflection.sendPacket(forPlayer, spawn);
                for (int i = 1; i <= 4; ++i) {
                    final PacketPlayOutEntityEquipment equip = new PacketPlayOutEntityEquipment(id, i, entity.getEquipment(i));
                    Reflection.sendPacket(forPlayer, equip);
                }
                if (original != null) {
                    profile.getProperties().removeAll("textures");
                    profile.getProperties().put("textures", original);
                }
            }
        }.runTaskLater(ArenaAPI.getApi(), 10L);
    }

    public static void changePlayerName(final Player player, final String name) {
        final GameProfile profile = ((CraftPlayer) player).getProfile();
        try {
            updateVariablePrivateFinal(profile, "name", name);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void sendPacket(final Packet<?> packet) {
        sendPacket(packet, null);
    }

    private static void sendPacket(final Packet<?> packet, final Player except) {
        for (final Player players : Bukkit.getOnlinePlayers()) {
            if (except != null && players == except) {
                continue;
            }
            ((CraftPlayer) players).getHandle().playerConnection.sendPacket(packet);
        }
    }

    private static void updateVariablePrivateFinal(final Object obj, final String variableName, final Object newValue) throws Exception {
        final Field field = obj.getClass().getDeclaredField(variableName);
        field.setAccessible(true);
        final Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & 0xFFFFFFEF);
        modifiersField.setAccessible(false);
        field.set(obj, newValue);
        field.setAccessible(false);
    }

    public static Property getPlayerTextures(final Player player) {
        return new ArrayList<Property>(((CraftPlayer) player).getHandle().getProfile().getProperties().get("textures")).get(0);
    }
}
