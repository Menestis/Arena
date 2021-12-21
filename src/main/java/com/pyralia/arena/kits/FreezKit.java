package com.pyralia.arena.kits;

import com.pyralia.arena.Main;
import com.pyralia.arena.player.KPlayer;
import com.pyralia.arena.utils.MathUtils;
import com.pyralia.arena.utils.PacketUtils;
import com.pyralia.arena.utils.PlayerUtils;
import com.pyralia.core.common.ItemCreator;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
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
public class FreezKit extends KitSchedule {


    private final Map<KPlayer, Location> kPlayerLocationMap = new HashMap<>();
    private final Map<KPlayer, Vector> kPlayerVectorMap = new HashMap<>();

    public FreezKit(){
        super("Freeze", new ItemStack(Material.SNOW_BALL),
                "§8» §7Mode : §2Nuzlocke",
                "§8» §7Type : §eOffensif",
                "§8» §7Pouvoirs:",
                "§f- §7Permet d'invoquer une vague de froid qui donnera Slowness II pendant 10s",
                "§7et fera 3 coeurs de dégats. (§a42s de cooldown§7)");
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
            }.runTaskTimer(Main.getInstance(), 0, 1);

            Bukkit.getScheduler().runTaskLater(Main.getInstance(), ()-> {
                kPlayerLocationMap.remove(kPlayer);
                kPlayerVectorMap.remove(kPlayer);
                clearBlizzard(player);
            }, 25);
        }
    }

    @Override
    public void onEquip(Player player){
        PlayerUtils.teleportPlayer(player);

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), ()-> {
            player.getInventory().clear();
            player.setMaxHealth(20);
            player.setHealth(player.getMaxHealth());

            player.getInventory().setItem(0, new ItemCreator(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, 2).get());
            player.getInventory().setItem(1, new ItemCreator(Material.PACKED_ICE).name("§bVague de Froid").lore("").get());
            player.getInventory().setItem(2, new ItemStack(Material.GOLDEN_APPLE, 9));
            player.getInventory().setItem(3, new ItemCreator(Material.DIAMOND_PICKAXE).enchant(Enchantment.DIG_SPEED, 3).get());
            player.getInventory().setItem(4, new ItemStack(Material.WATER_BUCKET));
            player.getInventory().setItem(5, new ItemStack(Material.COBBLESTONE, 64));
            player.getInventory().setItem(6, new ItemStack(Material.ARROW, 32));
            player.getInventory().setItem(7, new ItemCreator(Material.BOW).enchant(Enchantment.ARROW_DAMAGE, 2).get());
            player.getInventory().setItem(8, new ItemStack(Material.COBBLESTONE, 64));

            player.getInventory().setHelmet(new ItemCreator(Material.DIAMOND_HELMET).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).get());
            player.getInventory().setChestplate(new ItemCreator(Material.DIAMOND_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).get());
            player.getInventory().setLeggings(new ItemCreator(Material.IRON_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).get());
            player.getInventory().setBoots(new ItemCreator(Material.DIAMOND_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).get());
        }, 3);
    }

    private final Map<Player, ArrayList<EntityArmorStand>> fakeArmorStandsMap = new HashMap<>();
    private final Map<Player, ArrayList> cooldownJumpMap = new HashMap<>();
    private final PotionEffect potionEffect = new PotionEffect(PotionEffectType.SLOW, 200, 1, false, false);

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
            PacketUtils.send(players, new PacketPlayOutEntityEquipment(as.getId(), 4, CraftItemStack.asNMSCopy(new ItemStack(Material.PACKED_ICE))));
        }

       Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            for (Player pl : player.getWorld().getPlayers())
                PacketUtils.send(pl, new PacketPlayOutEntityDestroy(as.getId()));

            fakeArmorStands.remove(as);
        }, 20L);


        for (Entity ent : as.getBukkitEntity().getNearbyEntities(0.5D, 0.5D, 0.5D)) {
            if (!cooldownJump.contains(ent) && ent != player && ent instanceof Player) {
                ((Player) ent).damage(6);
                ((Player) ent).addPotionEffect(potionEffect);
                cooldownJump.add(ent);
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> cooldownJump.remove(ent), 20L);
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
