package fr.ariloxe.arena.kits.release;

import com.mojang.authlib.properties.Property;
import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.kits.Kit;
import fr.ariloxe.arena.kits.KitType;
import fr.ariloxe.arena.kits.power.LibeSkin;
import fr.ariloxe.arena.player.KPlayer;
import fr.ariloxe.arena.utils.IdentityChanger;
import fr.ariloxe.arena.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ariloxe
 */
public class LibeKit extends Kit {

    private final LibeSkin libeSkin = new LibeSkin();
    private final Map<KPlayer, Property> kPlayerPropertyMap = new HashMap<>();

    public LibeKit() {
        super("Libe_", KitType.DPS, new ItemStack(Material.DIAMOND_SWORD),
                "§8» §7Mode : §c/",
                "§8» §7Pouvoirs:",
                "§f- §7Lorsque vous frapperez vers le §bSud§7, vous aurez",
                "    §7une reach augmentée après le premier coup.");
    }

    @Override
    public void onEquip(Player player){
        kPlayerPropertyMap.put(ArenaAPI.getkPlayer(player), IdentityChanger.getPlayerTextures(player));
        player.getInventory().addItem(new ItemCreator(Material.WATER_BUCKET).get());
        Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), ()->{
            IdentityChanger.changeSkin(player, libeSkin);
        }, 20);
    }

    public Map<KPlayer, Property> getSkinsMap() {
        return kPlayerPropertyMap;
    }
}
