package fr.ariloxe.arena.kits;

import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.uis.kits.SelectKitCreator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ariloxe
 */
public enum KitType {

    TANK("§c§lTank", "§7Les kits de catégorie §ctank§7 permettent à", "§7leurs utilisateurs d'être plus résistant face aux", "§7ennemis, au détriment de leurs dégâts.", "", "§8§l» §7Cliquez pour ouvrir"),
    DPS("§6§lDPS", "§7Les kits de catégorie §6DamagePerSecond§7 permettent", "§7à leurs utilisateurs d'infliger plus de dégats", "§7aux ennemis. Ils sont également plutôt polyvalents", "", "§8§l» §7Cliquez pour ouvrir"),
    DEFENSIVE("§b§lDefensif" ,"§7Les kits de la catégorie §bdéfensive§7 permettent", "§7à leurs utilisateurs de contrer leurs dégâts", "§7ou pouvoirs spéciaux plus facilement.", "§7Néanmoins, ils ont une moins grande aptitude au combat.", "", "§8§l» §7Cliquez pour ouvrir"),
    HEALER("§d§lHealer", "§7Les kits de la catégorie §dhealer§7 permettant à", "§7leurs utilisateurs de se soigner face aux dégats", "§7infligés par les adversaires.", "", "§8§l» §7Cliquez pour ouvrir");


    private final String name;
    private final List<String> description;
    private final Map<KitType, SelectKitCreator> selectKitCreatorMap = new HashMap<>();

    KitType(String name, String... desc){
        this.name = name;
        this.description = Arrays.asList(desc);
        selectKitCreatorMap.put(this, new SelectKitCreator(ArenaAPI.getApi(), this));
    }

    public String getName() {
        return name;
    }

    public List<String> getDescription() {
        return description;
    }

    public SelectKitCreator getSelectKitCreator() {
        return selectKitCreatorMap.get(this);
    }
}
