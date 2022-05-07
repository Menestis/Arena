package com.pyralia.arena.kits.release;

import com.pyralia.api.utils.ItemCreator;
import com.pyralia.arena.ArenaAPI;
import com.pyralia.arena.kits.KitSchedule;
import com.pyralia.arena.kits.KitType;
import com.pyralia.arena.player.KPlayer;
import com.pyralia.arena.utils.MathUtils;
import com.pyralia.arena.utils.PacketUtils;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

/**
 * @author Ariloxe
 */
public class VulcanKit extends KitSchedule {


    private final Map<KPlayer, Location> kPlayerLocationMap = new HashMap<>();
    private final Map<KPlayer, Vector> kPlayerVectorMap = new HashMap<>();

    public VulcanKit(){
        super("Volcan", KitType.TANK, new ItemStack(Material.NETHERRACK),
                "§8» §7Mode : §2Nuzlocke",
                "§8» §7Pouvoirs:",
                "§f- §7Vous avez §c2❤ §7supplémentaire permanents.",
                "§f- §7Permet d'invoquer une vague de chaleur qui mettra les joueurs",
                "§7en feu et fera 2 coeurs de dégats. (§a42s de cooldown§7)");
        super.setSecondsDelay(42);
    }

    @Override
    public void power(KPlayer kPlayer) {
        Player player = kPlayer.getBukkitPlayer();
        if(player != null){
            kPlayerVectorMap.put(kPlayer, player.getLocation().getDirection().normalize().multiply(0.9D).setY(0));
            kPlayerLocationMap.put(kPlayer, player.getLocation().subtract(0, 1, 0).add(kPlayerVectorMap.get(kPlayer)));

            new BukkitRunnable() {
                @Override
                public void run() {
                    onUpdate(kPlayer);
                    if(!kPlayerLocationMap.containsKey(kPlayer))
                        cancel();
                }
            }.runTaskTimer(ArenaAPI.getApi(), 0, 1);

            Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), ()-> {
                kPlayerLocationMap.remove(kPlayer);
                kPlayerVectorMap.remove(kPlayer);
                clearBlizzard(player);
            }, 25);
        }
    }

    @Override
    public void onEquip(Player player){
        player.setMaxHealth(24);
        player.setHealth(player.getMaxHealth());
        player.getInventory().setItem(1, new ItemCreator(Material.NETHERRACK).name("§eVague de Feu").lore("").get());
    }

    private final Map<Player, ArrayList<EntityArmorStand>> fakeArmorStandsMap = new HashMap<>();
    private final Map<Player, ArrayList> cooldownJumpMap = new HashMap<>();

    private void onUpdate(KPlayer kPlayer) {
        if (kPlayerLocationMap.containsKey(kPlayer)) {
            Location location = kPlayerLocationMap.get(kPlayer);

            if (location.getBlock().getType() != Material.AIR && location.getBlock().getType().isSolid())
                location.add(0.0D, 1.0D, 0.0D);

            if (location.clone().subtract(0.0D, 1.0D, 0.0D).getBlock().getType() == Material.AIR && !location.clone().getBlock().getType().toString().contains("SLAB"))
                location.add(0.0D, -1.0D, 0.0D);

            for (int i = 0; i < 8; i++)
                sendBlizzard(kPlayer.getBukkitPlayer(), location, kPlayerVectorMap.get(kPlayer));

            kPlayerLocationMap.replace(kPlayer, kPlayerLocationMap.get(kPlayer), location.add(kPlayerVectorMap.get(kPlayer)));
        }
    }


    private void sendBlizzard(Player player, Location loc, Vector v) {
        if (!this.fakeArmorStandsMap.containsKey(player))
            this.fakeArmorStandsMap.put(player, new ArrayList<>());
        if (!this.cooldownJumpMap.containsKey(player)) {
            this.cooldownJumpMap.put(player, new ArrayList<>());
        }

        List<EntityArmorStand> fakeArmorStands = this.fakeArmorStandsMap.get(player);
        List<Entity> cooldownJump = this.cooldownJumpMap.get(player);

        EntityArmorStand as = new EntityArmorStand(((CraftWorld)player.getWorld()).getHandle());
        as.setInvisible(true);
        as.setSmall(true);
        as.setGravity(false);
        as.setArms(true);
        as.setHeadPose(new Vector3f(new Random().nextInt(360), new Random().nextInt(360), new Random().nextInt(360)));
        as.setLocation(loc.getX() + MathUtils.randomDouble(-1.5D, 1.5D), loc.getY() + MathUtils.randomDouble(0.0D, 0.5D) - 0.75D, loc.getZ() + MathUtils.randomDouble(-1.5D, 1.5D), 0.0F, 0.0F);
        fakeArmorStands.add(as);
        for (Player players : player.getWorld().getPlayers()) {

            PacketUtils.send(players, new PacketPlayOutSpawnEntityLiving(as));
            PacketUtils.send(players, new PacketPlayOutEntityEquipment(as.getId(), 4, CraftItemStack.asNMSCopy(new ItemStack(Material.NETHERRACK))));
        }

       Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), () -> {
            for (Player pl : player.getWorld().getPlayers())
                PacketUtils.send(pl, new PacketPlayOutEntityDestroy(as.getId()));

            fakeArmorStands.remove(as);
        }, 20L);


        for (Entity ent : as.getBukkitEntity().getNearbyEntities(0.5D, 0.5D, 0.5D)) {
            if (!cooldownJump.contains(ent) && ent != player && ent instanceof Player) {
                ((Player) ent).damage(4);
                ent.setFireTicks(20*6);
                cooldownJump.add(ent);
                Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), () -> cooldownJump.remove(ent), 20L);
            }
        }
    }

    public void clearBlizzard(Player player) {
        if (!this.fakeArmorStandsMap.containsKey(player))
            return;
        for (EntityArmorStand as : this.fakeArmorStandsMap.get(player)) {
            for (Player pl : player.getWorld().getPlayers()) {
                PacketUtils.send(pl, new PacketPlayOutEntityDestroy(new int[] { as.getId() })); }
        }
        this.fakeArmorStandsMap.remove(player);
        this.cooldownJumpMap.remove(player);
    }



}
