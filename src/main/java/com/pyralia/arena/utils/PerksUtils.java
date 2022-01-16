package com.pyralia.arena.utils;

import com.pyralia.arena.perks.DeathMessagePerks;
import com.pyralia.arena.perks.Perks;
import com.pyralia.arena.perks.PerksType;

/**
 * @author Ariloxe
 */
public class PerksUtils {

    public static PerksType getType(Perks perks){
        if(perks instanceof DeathMessagePerks)
            return PerksType.DEATHMESSAGE;
        else
            return null;


    }


}
