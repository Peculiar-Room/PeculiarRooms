package com.peculiarrooms.server.blocks;

import com.peculiarrooms.server.blocks.blockentity.PeculiarCrystalBlockEntity;
import com.peculiarrooms.server.registries.PRBlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.GameMasterBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class PeculiarCrystalBlock extends BaseEntityBlock implements GameMasterBlock {

    public PeculiarCrystalBlock(Properties properties) {
        super(properties);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_53840_) {
        return RenderShape.MODEL;
    }

    @Override
    public void onPlace(BlockState p_60566_, Level p_60567_, BlockPos p_60568_, BlockState p_60569_, boolean p_60570_) {
        super.onPlace(p_60566_, p_60567_, p_60568_, p_60569_, p_60570_);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        Random rand = new Random();
        return new PeculiarCrystalBlockEntity(pos, state, 3);
    }


    @javax.annotation.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_152180_, BlockState p_152181_, BlockEntityType<T> p_152182_) {
        return p_152180_.isClientSide ? createTickerHelper(p_152182_, PRBlockEntityRegistry.PECULIAR_CRYSTAL.get(), PeculiarCrystalBlockEntity::clientTick) : createTickerHelper(p_152182_, PRBlockEntityRegistry.PECULIAR_CRYSTAL.get(), PeculiarCrystalBlockEntity::serverTick);
    }



}
