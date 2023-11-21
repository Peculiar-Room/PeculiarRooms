package com.peculiarrooms.util.waves;

import net.minecraft.world.entity.EntityType;

import java.util.HashMap;

public class WaveInfo {
    // OFFICE
    public static Wave OFFICE_1 = new Wave(1,0,5).addMob(EntityType.ZOMBIE, 2).addMob(EntityType.SKELETON, 3);
    public static Wave OFFICE_2 = new Wave(1,2,7).addMob(EntityType.SKELETON, 3).addMob(EntityType.WITCH, 1);
    public static Wave OFFICE_3 = new Wave(1,4,8).addMob(EntityType.WITCH, 2).addMob(EntityType.STRAY, 2);
    public static Wave[] OFFICE_WAVES = {OFFICE_1, OFFICE_2, OFFICE_3};
    public static HashMap<String, Wave[]> WAVES = new HashMap<String, Wave[]>();

    public static void init(){
        WAVES.put("office", OFFICE_WAVES);
    }

    public static void output(){
        System.out.println("testing");;
    }
}
