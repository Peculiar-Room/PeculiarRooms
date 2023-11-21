package com.peculiarrooms.util.waves;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.LinkedList;

public class Wave {
    ArrayList<EntityType<LivingEntity>> mobs = new ArrayList<EntityType<LivingEntity>>();
    ArrayList<Integer> amounts = new ArrayList<Integer>();

    private int difficulty = 1;
    private int minimum_wave = 0;
    private int maximum_wave = 100;

    public Wave(int difficulty, int minimum_wave, int maximum_wave){
        this.difficulty = difficulty;
        this.minimum_wave = minimum_wave;
        this.maximum_wave = maximum_wave;
    }

    public Wave addMob(EntityType mobType, int amount){
        this.mobs.add(mobType);
        this.amounts.add(amount);
        return this;
    }


    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getMinimum_wave() {
        return minimum_wave;
    }

    public void setMinimum_wave(int minimum_wave) {
        this.minimum_wave = minimum_wave;
    }

    public int getMaximum_wave() {
        return maximum_wave;
    }

    public void setMaximum_wave(int maximum_wave) {
        this.maximum_wave = maximum_wave;
    }

    public ArrayList<EntityType<LivingEntity>> getMobs() {
        return mobs;
    }

    public ArrayList<Integer> getAmounts() {
        return amounts;
    }

}
