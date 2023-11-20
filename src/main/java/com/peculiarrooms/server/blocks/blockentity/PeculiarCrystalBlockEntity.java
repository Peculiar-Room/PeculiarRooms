package com.peculiarrooms.server.blocks.blockentity;

import com.peculiarrooms.server.registries.PRBlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.Random;

public class PeculiarCrystalBlockEntity extends BlockEntity {

    public int ticks_existed = 0;
    public PeculiarCrystalBlockEntity(BlockPos pos, BlockState state) {
        super(PRBlockEntityRegistry.PECULIAR_CRYSTAL.get(), pos, state);

    }




    public static void serverTick(Level level, BlockPos pos, BlockState state, PeculiarCrystalBlockEntity entity) {
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, PeculiarCrystalBlockEntity entity) {
        ++entity.ticks_existed;
        RandomSource rand = level.random;
        if (rand.nextFloat() < 0.78f){
            level.addParticle(ParticleTypes.ENCHANT, pos.getX() + (rand.nextFloat()), pos.getY() + 1f  + (rand.nextFloat() * 1.3), pos.getZ()+ (rand.nextFloat()) , (rand.nextFloat()) - 0.5, (rand.nextFloat()) - 0.5,(rand.nextFloat()) - 0.5);
        }

    }
}
