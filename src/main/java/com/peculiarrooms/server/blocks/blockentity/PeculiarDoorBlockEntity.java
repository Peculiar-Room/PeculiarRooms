package com.peculiarrooms.server.blocks.blockentity;

import com.peculiarrooms.server.registries.PRBlockEntityRegistry;
import com.peculiarrooms.server.registries.PRBlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.neoforge.common.Tags;

public class PeculiarDoorBlockEntity extends BlockEntity {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    public PeculiarDoorBlockEntity(BlockPos pos, BlockState state) {
        super(PRBlockEntityRegistry.PECULIAR_DOOR.get(), pos, state);
    }

    public void generateRoom(BlockPos pos, BlockState state){
        BlockPos doorPos = pos;
        int groundPos = pos.below().getY();
        int roofPos = pos.above(level.random.nextInt(3,8)).getY();

        LevelChunk chunk = level.getChunkAt(pos.relative(state.getValue(FACING)));
        ChunkPos chunkPos = chunk.getPos();

        int minX = chunkPos.getMinBlockX();
        int maxX = chunkPos.getMaxBlockX();
        int minZ = chunkPos.getMinBlockZ();
        int maxZ = chunkPos.getMaxBlockZ();
        for(int i = pos.getY() - 20; i < pos.getY() + 20; i++){
            if (level.getBlockState(new BlockPos(minX,i,minZ)).is(Blocks.NETHERITE_BLOCK)){
                level.setBlock(pos.relative(state.getValue(FACING)), Blocks.AIR.defaultBlockState(), 3);
                level.setBlock(pos.relative(state.getValue(FACING)).above(), Blocks.AIR.defaultBlockState(), 3);
                return;
            }
        }


        for (int i = chunkPos.getMinBlockX(); i <= chunkPos.getMaxBlockX(); i++){
            for (int j = chunkPos.getMinBlockZ(); j <= chunkPos.getMaxBlockZ(); j++) {
                BlockPos new_pos = new BlockPos(i, groundPos, j);
                if (!level.getBlockState(new_pos).is(PRBlockRegistry.PECULIAR_DOOR.get())) {
                    level.setBlock(new_pos, Blocks.NETHERITE_BLOCK.defaultBlockState(), 3);
                }
                new_pos = new BlockPos(i, roofPos, j);
                if (!level.getBlockState(new_pos).is(PRBlockRegistry.PECULIAR_DOOR.get())) {
                    level.setBlock(new_pos, Blocks.NETHERITE_BLOCK.defaultBlockState(), 3);
                }
            }
        }

        //WALLS

        for (int i = minZ; i <= maxZ; i++){
            for (int j = groundPos; j < roofPos; j++){
                BlockPos wall1 = new BlockPos(minX, j, i);
                if (!level.getBlockState(wall1).is(PRBlockRegistry.PECULIAR_DOOR.get())) {
                    level.setBlock(wall1, Blocks.NETHERITE_BLOCK.defaultBlockState(), 3);
                }
                BlockPos wall2 = new BlockPos(maxX, j, i);
                if (!level.getBlockState(wall2).is(PRBlockRegistry.PECULIAR_DOOR.get())) {
                    level.setBlock(wall2, Blocks.NETHERITE_BLOCK.defaultBlockState(), 3);
                }
            }
        }

        for (int i = minX; i <= maxX; i++){
            for (int j = groundPos; j < roofPos; j++){
                BlockPos wall1 = new BlockPos(i, j, minZ);
                if (!level.getBlockState(wall1).is(PRBlockRegistry.PECULIAR_DOOR.get())) {
                    level.setBlock(wall1, Blocks.NETHERITE_BLOCK.defaultBlockState(), 3);
                }
                BlockPos wall2 = new BlockPos(i, j, maxZ);
                if (!level.getBlockState(wall2).is(PRBlockRegistry.PECULIAR_DOOR.get())) {
                    level.setBlock(wall2, Blocks.NETHERITE_BLOCK.defaultBlockState(), 3);
                }
            }
        }


        if (minX - 1 != pos.getX() && minX != pos.getX()){
            int doorZ = level.random.nextInt(minZ + 1, maxZ - 1);
            BlockPos newDoorPos = new BlockPos(minX, groundPos + 1, doorZ);
            level.setBlock(newDoorPos, PRBlockRegistry.PECULIAR_DOOR.get().defaultBlockState().setValue(FACING, Direction.WEST).setValue(HALF, DoubleBlockHalf.LOWER), 3);
            level.setBlock(newDoorPos.above(), PRBlockRegistry.PECULIAR_DOOR.get().defaultBlockState().setValue(FACING, Direction.WEST).setValue(HALF, DoubleBlockHalf.UPPER), 3);

        }
        if (maxX + 1 != pos.getX() && maxX != pos.getX()){
            int doorZ = level.random.nextInt(minZ + 1, maxZ - 1);
            BlockPos newDoorPos = new BlockPos(maxX, groundPos + 1, doorZ);
            level.setBlock(newDoorPos, PRBlockRegistry.PECULIAR_DOOR.get().defaultBlockState().setValue(FACING, Direction.EAST).setValue(HALF, DoubleBlockHalf.LOWER), 3);
            level.setBlock(newDoorPos.above(), PRBlockRegistry.PECULIAR_DOOR.get().defaultBlockState().setValue(FACING, Direction.EAST).setValue(HALF, DoubleBlockHalf.UPPER), 3);
        }
        if (minZ - 1 != pos.getZ() && minZ != pos.getZ()){
            int doorX = level.random.nextInt(minX + 1, maxX - 1);
            BlockPos newDoorPos = new BlockPos(doorX, groundPos + 1, minZ);
            level.setBlock(newDoorPos, PRBlockRegistry.PECULIAR_DOOR.get().defaultBlockState().setValue(FACING, Direction.NORTH).setValue(HALF, DoubleBlockHalf.LOWER), 3);
            level.setBlock(newDoorPos.above(), PRBlockRegistry.PECULIAR_DOOR.get().defaultBlockState().setValue(FACING, Direction.NORTH).setValue(HALF, DoubleBlockHalf.UPPER), 3);
        }
        if (maxZ + 1 != pos.getZ() && maxZ != pos.getZ()){
            int doorX = level.random.nextInt(minX + 1, maxX - 1);
            BlockPos newDoorPos = new BlockPos(doorX, groundPos + 1, maxZ);
            level.setBlock(newDoorPos, PRBlockRegistry.PECULIAR_DOOR.get().defaultBlockState().setValue(FACING, Direction.SOUTH).setValue(HALF, DoubleBlockHalf.LOWER), 3);
            level.setBlock(newDoorPos.above(), PRBlockRegistry.PECULIAR_DOOR.get().defaultBlockState().setValue(FACING, Direction.SOUTH).setValue(HALF, DoubleBlockHalf.UPPER), 3);
        }

        level.setBlock(pos.relative(state.getValue(FACING)), Blocks.AIR.defaultBlockState(), 3);
        level.setBlock(pos.relative(state.getValue(FACING)).above(), Blocks.AIR.defaultBlockState(), 3);

    }
}
