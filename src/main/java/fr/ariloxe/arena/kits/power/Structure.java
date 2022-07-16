package fr.ariloxe.arena.kits.power;

import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.player.KPlayer;
import fr.ariloxe.arena.utils.BlockUtils;
import fr.ariloxe.arena.utils.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.*;

/**
 * @author Ariloxe
 */
public class Structure {

    private final Map<KPlayer, List<Block>> kPlayerListMap = new HashMap<>();
    private final Map<Block, Material> storageList = new HashMap<>();
    private final ParticleEffect particleEffect;
    private final List<Material> materialList;
    private final int secondsDelay;
    private final int radius;

    public Structure(int secondsDelay, int radius, ParticleEffect patricleEffect, Material... materials){
        this.secondsDelay = secondsDelay;
        this.radius = radius;
        this.particleEffect = patricleEffect;
        materialList = Arrays.asList(materials);
    }

    public void onEnable(KPlayer kPlayer){
        kPlayerListMap.put(kPlayer, new ArrayList<>());

        for(Block block : BlockUtils.circle(kPlayer.getBukkitPlayer().getLocation(), 4.5, 5.0, true, true, 2)){
            if(block.getType() != Material.AIR)
                continue;

            if(storageList.containsKey(block))
                continue;
            if(kPlayerListMap.get(kPlayer).contains(block))
                continue;

            Material material;
            if(materialList.size() > 1)
                material = materialList.get(new Random().nextInt(materialList.size()));
            else
                material = this.materialList.get(0);

            this.particleEffect.display(new ParticleEffect.OrdinaryColor(Color.RED), block.getLocation().add(0, 1, 0), 50);
            storageList.put(block, block.getType());
            kPlayerListMap.get(kPlayer).add(block);
            block.setType(material);

        }

        Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), ()->{
            kPlayerListMap.get(kPlayer).forEach(block -> {
                block.setType(storageList.get(block));
                storageList.remove(block);
            });

            kPlayerListMap.remove(kPlayer);
        }, 20L *this.secondsDelay);

    }



}
