package com.peculiarrooms.server.blocks.blockentity;

import com.peculiarrooms.server.registries.PRBlockEntityRegistry;
import com.peculiarrooms.util.waves.Wave;
import com.peculiarrooms.util.waves.WaveInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PeculiarCrystalBlockEntity extends BlockEntity {
    private final TargetingConditions minionTargetting = TargetingConditions.forNonCombat()
            .range(16.0)
            .ignoreLineOfSight();
    public String type = "office";
    @OnlyIn(Dist.CLIENT)
    public int tickCount = 0;
    public int wave = 0;
    public int max_wave;
    public boolean is_completed = false;
    public int completion_timer = 0;
    public int crystal_rotation_client = 0;
    public List<LivingEntity> mobList = new ArrayList<LivingEntity>();

    public PeculiarCrystalBlockEntity(BlockPos pos, BlockState state) {
        super(PRBlockEntityRegistry.PECULIAR_CRYSTAL.get(), pos, state);
    }

    public PeculiarCrystalBlockEntity(BlockPos pos, BlockState state, int max_wave) {
        super(PRBlockEntityRegistry.PECULIAR_CRYSTAL.get(), pos, state);
        this.max_wave = max_wave;
    }




    public static void serverTick(Level level, BlockPos pos, BlockState state, PeculiarCrystalBlockEntity entity) {
        entity.tickCount++;
        if (entity.tickCount == 30) {
            entity.nextWave(level,entity);
        }
        if(entity.wave <= entity.max_wave) {
            if (entity.tickCount % 40 == 0 && !entity.checkMobs(level, entity)) {
                entity.nextWave(level, entity);
            }
        }
        else{
            if (!entity.checkMobs(level,entity)) {
                entity.is_completed = true;
            }
        }


        if (entity.is_completed){
            //TODO SUMMON LOOT
            entity.completion_timer++;
        }
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, PeculiarCrystalBlockEntity entity) {
        ++entity.crystal_rotation_client;
        RandomSource rand = level.random;
        if (rand.nextFloat() < 0.78f) {
            level.addParticle(ParticleTypes.ENCHANT, pos.getX() + (rand.nextFloat()), pos.getY() + 1f + (rand.nextFloat() * 1.3), pos.getZ() + (rand.nextFloat()), (rand.nextFloat()) - 0.5, (rand.nextFloat()) - 0.5, (rand.nextFloat()) - 0.5);
        }

    }

    public void getWave(PeculiarCrystalBlockEntity e, int waveNumber) {
        Wave[] baseWaveList = WaveInfo.WAVES.get(e.type);
        List<Wave> possibleWaves = new ArrayList<Wave>();
        for (Wave w : baseWaveList) {
            if (w.getMinimum_wave() <= e.wave && e.wave <= w.getMaximum_wave()) {
                possibleWaves.add(w);
            }
        }
        int length = possibleWaves.size();
        try {
            Wave randomWave = possibleWaves.get(e.getLevel().random.nextInt(0, length));
            spawnWave(e, randomWave);
        } catch (Exception exception) {
            System.out.println("No valid waves to spawn! :(");
        }
    }

    public void spawnWave(PeculiarCrystalBlockEntity e, Wave w) {
        ArrayList<EntityType<LivingEntity>> mobs = w.getMobs();
        ArrayList<Integer> amounts = w.getAmounts();
        for (EntityType type : mobs) {
            int index = mobs.indexOf(type);
            for (int i = 0; i < amounts.get(index); i++) {
                BlockPos pos = getNearbyPos(e);
                LivingEntity mob = (LivingEntity) type.create(e.level);
                mob.moveTo(pos.getCenter());
                e.level.addFreshEntity(mob);
                e.mobList.add(mob);
            }
        }
    }

    public BlockPos getNearbyPos(PeculiarCrystalBlockEntity e) {
        BlockPos myPos = e.getBlockPos();
        BlockPos newPos = myPos.offset(e.level.random.nextInt(-2, 3), 0, e.level.random.nextInt(-2, 3));
        return newPos;
    }

    public boolean checkMobs(Level l, PeculiarCrystalBlockEntity e) {
        for (int i = 0; i < mobList.size(); i++){
            if (mobList.get(i).isAlive()) return true;
        }
        return false;
    }

    public void nextWave(Level l, PeculiarCrystalBlockEntity e) {
        e.wave++;
        e.getWave(e, e.wave);
    }
}
