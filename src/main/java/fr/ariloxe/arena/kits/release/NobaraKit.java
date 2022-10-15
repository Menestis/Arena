package fr.ariloxe.arena.kits.release;

import fr.ariloxe.arena.utils.ItemCreator;
import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.kits.KitSchedule;
import fr.ariloxe.arena.kits.KitType;
import fr.ariloxe.arena.player.KPlayer;
import fr.ariloxe.arena.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ariloxe
 */
public class NobaraKit extends KitSchedule {

    public NobaraKit() {
        super("Nobara", KitType.DPS, new ItemStack(Material.EMERALD),
                "§8» §7Mode : §5JujutsuKaisen",
                "§8» §7Pouvoirs:",
                "§f- §7En cliquant sur votre poupée vaudou sur un joueur, vous infligerez des",
                "   §7dégâts en fonction du nombre de coups qu'il vous a mis :",
                "",
                "  §8• §fEntre 1 et 5 coups, cela infligera un demi-coeur de dégât.",
                "  §8• §fEntre 5 et 10 coups, cela infligera un coeur de dégât.",
                "  §8• §fEntre 10 et 15 coups, cela infligera un coeur et demi de dégât.",
                "  §8• §fEntre 15 et 20 coups, cela infligera deux coeurs de dégât.",
                "  §8• §fEntre 20 et 30 coups, cela infligera un trois coeurs de dégât.",
                "  §8• §fPlus de 30 coups, cela infligera quatre coeurs de dégat.",
                "    §7(§a47 secondes de cooldown§7)"
        );
        super.setSecondsDelay(47);
    }

    private final Map<KPlayer, Map<KPlayer, Integer>> map = new HashMap<>();

    public Map<KPlayer, Map<KPlayer, Integer>> getPlayerPlayerIntegerMap() {
        return map;
    }

    @Override
    public void power(KPlayer kPlayer) {
        Player player = kPlayer.getBukkitPlayer();

        if(player != null){
            Player target = PlayerUtils.getTarget(player, 60, 1.4D, true);
            if (target != null) {
                if(!getPlayerPlayerIntegerMap().get(kPlayer).containsKey(ArenaAPI.getkPlayer(player))){
                    kPlayer.sendMessage("§3§lMenestis §f» §7Le joueur §b" + target.getName() + "§7 n'est pas attaquable: vous ne l'avez jamais frappé !");


                    Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), ()->{
                        getkPlayerRemainsList().remove(kPlayer);
                        getPlayerIntegerMap().remove(kPlayer);
                    }, 10);
                }

                int value = getPlayerPlayerIntegerMap().get(kPlayer).get(ArenaAPI.getkPlayer(player));
                int damageValue = 0;
                if(value > 0 && value < 6)
                    damageValue = 1;
                else if(value > 5 && value < 11)
                    damageValue = 2;
                else if(value > 11 && value < 16)
                    damageValue = 3;
                else if(value > 16 && value < 21)
                    damageValue = 4;
                else if(value > 21 && value < 31)
                    damageValue = 5;
                else if(value > 30)
                    damageValue = 6;

                target.damage(damageValue);
                target.sendMessage("§3§lMenestis §f» §5Nobara§7 use de ses techniques vaudou contre vous ! Vous perdez donc §c§l" + (double) damageValue / 2 + " §ccoeurs7.");
                kPlayer.sendMessage("§3§lMenestis §f» §7Vous avez infligé §c§l" + (double) damageValue / 2 + " §ccoeurs7 au joueur §e§n" + target.getName() + "§7 !");

            } else {
                Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), ()->{
                    getkPlayerRemainsList().remove(kPlayer);
                    getPlayerIntegerMap().remove(kPlayer);
                }, 10);
            }
        }
    }

    @Override
    public void onEquip(Player player) {
        getPlayerPlayerIntegerMap().put(ArenaAPI.getkPlayer(player), new HashMap<>());
        player.getInventory().addItem(new ItemCreator(Material.FLOWER_POT_ITEM).name("§ePoupée Vaudou §8§l▪ §7Clic-droit").get());
    }
}
