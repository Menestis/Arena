package fr.ariloxe.arena.listeners;

import fr.ariloxe.arena.utils.ItemCreator;
import fr.ariloxe.arena.utils.skull.SkullList;
import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.kits.release.GuepKit;
import fr.ariloxe.arena.kits.release.LibeKit;
import fr.ariloxe.arena.kits.release.MuzanKit;
import fr.ariloxe.arena.player.KPlayer;
import fr.ariloxe.arena.uis.PackInventory;
import fr.ariloxe.arena.utils.IdentityChanger;

import fr.blendman.magnet.api.MagnetApi;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * @author Ariloxe
 */
public class PlayersListener implements Listener {

    private final ArenaAPI instance;
    private final PackInventory packInventory = new PackInventory();

    public PlayersListener(ArenaAPI instance){
        this.instance = instance;
    }

    @EventHandler
    public void onLiquid(BlockFromToEvent blockSpreadEvent) {
        blockSpreadEvent.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent playerDropItemEvent){
        if(playerDropItemEvent.getPlayer().getWorld().getName().contains("Spawn")){
            playerDropItemEvent.setCancelled(true);
            return;
        }
        if(playerDropItemEvent.getPlayer().getLocation().getY() > 100)
            playerDropItemEvent.setCancelled(true);
    }

    @EventHandler
    public void onWeather(WeatherChangeEvent weatherChangeEvent){
        weatherChangeEvent.setCancelled(true);
    }

    @EventHandler
    public void onSpawn(EntitySpawnEvent entitySpawnEvent){
        entitySpawnEvent.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent entityDamageByEntityEvent){
        if(entityDamageByEntityEvent.getEntity().getWorld().getName().equals("Spawn")){
            entityDamageByEntityEvent.setCancelled(true);
            return;
        }

        if(entityDamageByEntityEvent.getEntity().getLocation().getY() > 100)
            entityDamageByEntityEvent.setCancelled(true);

        if(entityDamageByEntityEvent.getEntity() instanceof Player && !ArenaAPI.getkPlayer(((Player) entityDamageByEntityEvent.getEntity())).isDamageable())
            entityDamageByEntityEvent.setCancelled(true);
        else if(entityDamageByEntityEvent.getDamager() instanceof Player && !ArenaAPI.getkPlayer(((Player) entityDamageByEntityEvent.getDamager())).isDamageable())
            entityDamageByEntityEvent.setCancelled(true);
    }


    @EventHandler
    public void onObsidian(BlockFromToEvent blockFromToEvent){
        if(blockFromToEvent.getBlock().getType() == Material.OBSIDIAN)
            instance.getGameManager().getLocationList().add(blockFromToEvent.getToBlock().getLocation());
    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent foodLevelChangeEvent){
        foodLevelChangeEvent.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent playerInteractEvent){
        if(playerInteractEvent.getItem() == null)
            return;

        if(playerInteractEvent.getPlayer().getLocation().getWorld().getName().contains("Spawn")){
            if(playerInteractEvent.getItem().getType() == Material.ENDER_PORTAL_FRAME)
                instance.getGuiManager().getSelectKitInventory().open(playerInteractEvent.getPlayer());
            else if(playerInteractEvent.getItem().getType() == Material.BED)
                MagnetApi.MagnetStore.getApi().getPlayerHandle().movePlayerToServer(playerInteractEvent.getPlayer().getUniqueId(), "lobby");
            else if(playerInteractEvent.getItem().getType() == Material.SKULL_ITEM)
                packInventory.open(playerInteractEvent.getPlayer());
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent blockPlaceEvent){
        if(blockPlaceEvent.getBlockPlaced().getLocation().getY() > 110){
            blockPlaceEvent.setCancelled(true);
            blockPlaceEvent.getPlayer().sendMessage("§3§lMenestis §f» §cVous ne pouvez pas placer de block si haut !");
            return;
        }

        if(blockPlaceEvent.getPlayer().getInventory().getItemInHand().hasItemMeta()){
            blockPlaceEvent.setCancelled(true);
            return;
        }

        instance.getGameManager().getLocationList().add(blockPlaceEvent.getBlock().getLocation());
    }

    @EventHandler
    public void onBreak(BlockBreakEvent blockBreakEvent){
        if(blockBreakEvent.getPlayer().getGameMode() == GameMode.CREATIVE)
            return;


        if(!instance.getGameManager().getLocationList().contains(blockBreakEvent.getBlock().getLocation())){
            blockBreakEvent.getPlayer().sendMessage("§3§lMenestis §f» §cVous ne pouvez pas casser un bloc qui n'a pas été posé par un joueur.");
            blockBreakEvent.setCancelled(true);
            return;
        }

        instance.getGameManager().getLocationList().remove(blockBreakEvent.getBlock().getLocation());
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent playerRespawnEvent){
        Player player = playerRespawnEvent.getPlayer();

        player.teleport(instance.getGameManager().getWorldManager().getLobbyLocation());

        player.getInventory().clear();
        ItemStack air = new ItemStack(Material.AIR);
        player.getInventory().setHelmet(air);
        player.getInventory().setChestplate(air);
        player.getInventory().setLeggings(air);
        player.getInventory().setBoots(air);

        player.getInventory().setHeldItemSlot(4);

        player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));

        //     player.getInventory().setItem(2, new ItemCreator(SkullList.CAMERA.getItemStack()).name("§e§lPack de Texture §8§l▪ §7§lClic-droit").lore("", "§8» §7Cliquez ici pour télécharger le pack !").get());
        player.getInventory().setItem(4, new ItemCreator(Material.ENDER_PORTAL_FRAME).name("§a§lChoisir son Kit §8§l▪ §7§lClic-droit").lore("", "§8» §7Cliquez ici pour choisir un kit !").get());
        player.getInventory().setItem(8, new ItemCreator(Material.BED).name("§c§lRetourner au Lobby §8§l▪ §7§lClic-droit").lore("", "§8» §7Cliquez ici pour retourner au lobby !").get());

        player.setMaxHealth(20);
        player.setHealth(player.getMaxHealth());
        player.setAllowFlight(false);
        player.setFoodLevel(20);
    }

    private final Map<KPlayer, Integer> killStreakMap = new HashMap<>();

    @EventHandler
    public void onDeath(PlayerDeathEvent playerDeathEvent){
        playerDeathEvent.setDeathMessage(null);
        playerDeathEvent.getEntity().getWorld().playEffect(playerDeathEvent.getEntity().getLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);

        Player player = playerDeathEvent.getEntity();
        KPlayer kPlayer = ArenaAPI.getkPlayer(player);
        kPlayer.setDeaths(ArenaAPI.getkPlayer(player).getDeaths() + 1);
        if(kPlayer.getKit() instanceof LibeKit)
            IdentityChanger.changeSkin(player, ((LibeKit) kPlayer.getKit()).getSkinsMap().get(kPlayer));

        if(killStreakMap.get(kPlayer) != null)
            killStreakMap.replace(kPlayer, killStreakMap.get(kPlayer), 0);

        if(player.getKiller() != null){
            KPlayer kAttacker = ArenaAPI.getkPlayer(player.getKiller());
            killStreakMap.putIfAbsent(kAttacker, 0);
            killStreakMap.put(kAttacker, killStreakMap.get(kAttacker) + 1);
            int p = killStreakMap.get(kAttacker);
            if(p == 5){
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("§3§lMenestis §f» §b" + kAttacker.getBukkitPlayer().getName() + "§7 possède un KillStreak de §c" + p + "§7 kills !");
                Bukkit.broadcastMessage("");
            } else if(p == 10){
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("§3§lMenestis §f» §7Incroyable ! §b" + kAttacker.getBukkitPlayer().getName() + "§7 est en furie avec §c" + p + "§7 kills consécutifs !");
                Bukkit.broadcastMessage("");
            } else if(p == 15){
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("§3§lMenestis §f» §7Impossible ! §b" + kAttacker.getBukkitPlayer().getName() + "§7 fait un carnage avec §c" + p + "§7 kills d'affilés !");
                Bukkit.broadcastMessage("");
            } else if(p == 25){
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("§3§lMenestis §f» §7Hors-Normes ! §b" + kAttacker.getBukkitPlayer().getName() + "§7 fait un massacre avec §c" + p + "§7 kills sans mourir !");
                Bukkit.broadcastMessage("");
            }


            Bukkit.broadcastMessage("§3§lMenestis §f» §c" + player.getName() + "§7 est mort par §a" + player.getKiller().getName() + " §8[§f" + ((int) player.getKiller().getHealth()) / 2 + "§f ❤§8]");

            ArenaAPI.getkPlayer(player.getKiller()).setKills(ArenaAPI.getkPlayer(player.getKiller()).getKills() + 1);
            player.getKiller().getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 3));
            player.getKiller().getInventory().addItem(new ItemStack(Material.COBBLESTONE, 20));
            if (ArenaAPI.getkPlayer(player.getKiller()).getKit() instanceof GuepKit)
                player.getKiller().getInventory().addItem(new ItemStack(Material.ARROW, 16));
            else if (ArenaAPI.getkPlayer(player.getKiller()).getKit() instanceof MuzanKit && new Random().nextInt(3) == 1)
                player.getKiller().setMaxHealth(player.getKiller().getMaxHealth() + 1);

            player.getKiller().setHealth(Math.min(player.getKiller().getHealth() + 4, player.getKiller().getMaxHealth()));
        } else
            Bukkit.broadcastMessage("§3§lMenestis §f» §3" + player.getName() + "§7 est mort tout seul !");

        playerDeathEvent.getDrops().clear();
        Bukkit.getScheduler().runTaskLater(instance, ()->{
            player.spigot().respawn();

            player.teleport(instance.getGameManager().getWorldManager().getLobbyLocation());
            player.getInventory().clear();
            ItemStack air = new ItemStack(Material.AIR);
            player.getInventory().setHelmet(air);
            player.getInventory().setChestplate(air);
            player.getInventory().setLeggings(air);
            player.getInventory().setBoots(air);

            player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));

            //     player.getInventory().setItem(2, new ItemCreator(SkullList.CAMERA.getItemStack()).name("§e§lPack de Texture §8§l▪ §7§lClic-droit").lore("", "§8» §7Cliquez ici pour télécharger le pack !").get());
            player.getInventory().setItem(4, new ItemCreator(Material.ENDER_PORTAL_FRAME).name("§a§lChoisir son Kit §8§l▪ §7§lClic-droit").lore("", "§8» §7Cliquez ici pour choisir un kit !").get());
            player.getInventory().setItem(8, new ItemCreator(Material.BED).name("§c§lRetourner au Lobby §8§l▪ §7§lClic-droit").lore("", "§8» §7Cliquez ici pour retourner au lobby !").get());

            player.setMaxHealth(20);
            player.setHealth(player.getMaxHealth());
            player.setAllowFlight(false);

            player.setFoodLevel(20);

            if(kPlayer.getKit() instanceof LibeKit){
                IdentityChanger.changeSkin(player, ((LibeKit) kPlayer.getKit()).getSkinsMap().get(kPlayer));
                ((LibeKit) kPlayer.getKit()).getSkinsMap().remove(kPlayer);
            }

        }, 3);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent playerJoinEvent){
        playerJoinEvent.setJoinMessage(null);
        Player player = playerJoinEvent.getPlayer();
        Bukkit.broadcastMessage("§3§lMenestis §f» §a" + player.getName() + "§7 a rejoint l'Arène.");
        ArenaAPI.registerPlayer(player);

        player.sendMessage("§3§lMenestis §f» §7Il y a §b10€§7 à gagner pour le §a1er§7 au classement !");

        Bukkit.getScheduler().runTaskLater(instance, ()->{
            player.teleport(instance.getGameManager().getWorldManager().getLobbyLocation());
            player.setGameMode(GameMode.SURVIVAL);
            player.getInventory().clear();
            ItemStack air = new ItemStack(Material.AIR);
            player.getInventory().setHelmet(air);
            player.getInventory().setChestplate(air);
            player.getInventory().setLeggings(air);
            player.getInventory().setBoots(air);

            player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));

    //        player.getInventory().setItem(0, new ItemCreator(SkullList.CAMERA.getItemStack()).name("§e§lPack de Texture §8§l▪ §7§lClic-droit").lore("", "§8» §7Cliquez ici pour télécharger le pack !").get());
            player.getInventory().setItem(4, new ItemCreator(Material.ENDER_PORTAL_FRAME).name("§a§lChoisir son Kit §8§l▪ §7§lClic-droit").lore("", "§8» §7Cliquez ici pour choisir un kit !").get());
            player.getInventory().setItem(8, new ItemCreator(Material.BED).name("§c§lRetourner au Lobby §8§l▪ §7§lClic-droit").lore("", "§8» §7Cliquez ici pour retourner au lobby !").get());

            player.setMaxHealth(20);
            player.setHealth(player.getMaxHealth());
            player.setAllowFlight(false);
            player.setFoodLevel(20);
        }, 3);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent playerQuitEvent){
        playerQuitEvent.setQuitMessage(null);
        Player player = playerQuitEvent.getPlayer();
        Bukkit.broadcastMessage("§3§lMenestis §f» §c" + player.getName() + "§7 a quitté l'Arène.");
    }

    private int strengthRate = 30;
    private int resistanceRate = 15;

    @EventHandler
    private void onPatchPotion(EntityDamageByEntityEvent event) {
        event.setDamage(event.getDamage() * 0.90);

        if (!(event.getEntity() instanceof Player))
            return;

        if (!(event.getDamager() instanceof Player))
            return;
        Player damager = (Player) event.getDamager();
        Player player = (Player) event.getEntity();

        if (damager.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {

            if (damager.getActivePotionEffects().stream().filter(potionEffect -> potionEffect.getType().equals(PotionEffectType.INCREASE_DAMAGE)).map(PotionEffect::getAmplifier).findFirst().orElse(-1) == 0) {
                event.setDamage(event.getDamage() / 2.3f *
                        (1 + strengthRate / 100f));
            } else event.setDamage(event.getDamage() *
                    (1 + strengthRate / 100f));
        }
        if (player.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
            if (resistanceRate >= 100) {
                event.setCancelled(true);
            }
            event.setDamage(event.getDamage() * (100 - resistanceRate) / 80f);
        }
    }

    @EventHandler
    public void onPlayerDamaging(EntityDamageByEntityEvent entityDamageByEntityEvent){
        if(entityDamageByEntityEvent.getDamager() instanceof Arrow && entityDamageByEntityEvent.getEntity() instanceof Player && ((Arrow)entityDamageByEntityEvent.getDamager()).getShooter() instanceof Player){
            Player player = ((Player) ((Arrow) entityDamageByEntityEvent.getDamager()).getShooter());
            Player victim = ((Player) entityDamageByEntityEvent.getEntity());
            Bukkit.getScheduler().runTaskLater(instance, ()-> player.sendMessage("§3§lMenestis §f» §7" + victim.getName() + " §8§l» " + makePercentColor(victim.getHealth()) + "% §8[§6" + makePercent(entityDamageByEntityEvent.getDamage()) + "%§8]"), 2);
        }
    }

    private String makePercentColor(double health) {
        double hearts = health / 2;
        double percent = hearts * 10;

        if (percent >= 66) {
            return "§a" + ((int) percent);
        } else if (percent >= 33) {
            return "§e" + ((int) percent);
        } else if (percent == 0) {
            return "§0" + (0);
        } else {
            return "§c" + ((int) percent);
        }
    }

    private String makePercent(double health) {
        double hearts = health / 2;
        double percent = hearts * 10;

        if (percent == 0) {
            return "" + (0);
        } else {
            return "" + ((int) percent);
        }
    }

    @EventHandler
    public void onResourcePackStatusEvent(PlayerResourcePackStatusEvent e) {
        Player player = e.getPlayer();
        if (e.getStatus().equals(PlayerResourcePackStatusEvent.Status.ACCEPTED)) {
            player.sendMessage("§3§lMenestis §f» §7Téléchargement du pack en cours...");
        } else if (e.getStatus() == PlayerResourcePackStatusEvent.Status.DECLINED) {
            player.sendMessage("§3§lMenestis §f» §7Le resource pack est conseillé pour une meilleure expérience de jeu !");
            ComponentBuilder message = new ComponentBuilder("");
            message.append(" §7Téléchargement du pack ");
            message.append("§aCliquez-ici").event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder("§7Télécharger le pack")).create())).event(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://static.pyralia.com/PyraliaArene.zip"));
            message.append("\n ");
            player.spigot().sendMessage(message.create());
        } else if (e.getStatus().equals(PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED)) {
            player.sendMessage("§3§lMenestis §f» §7Téléchargement du pack terminé !");
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if(event.getEntity().getWorld().getName().equals("Spawn"))
            event.setCancelled(true);
    }


}
