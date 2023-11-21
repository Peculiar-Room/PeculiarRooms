package com.peculiarrooms.server.blocks.blockentity;

import com.peculiarrooms.PeculiarRooms;
import com.peculiarrooms.server.registries.PRBlockEntityRegistry;
import com.peculiarrooms.server.registries.PRBlockRegistry;
import net.minecraft.ResourceLocationException;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringUtil;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockRotProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.neoforged.neoforge.common.Tags;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class PeculiarDoorBlockEntity extends BlockEntity {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    Random ran = new Random();
    ResourceLocation[] structures = {
            ResourceLocation.tryParse("peculiar_rooms:office_main"),
            ResourceLocation.tryParse("peculiar_rooms:office_corridor"),
            ResourceLocation.tryParse("peculiar_rooms:office_main")};
    public final ResourceLocation structureName = structures[ran.nextInt(3)];
    public PeculiarDoorBlockEntity(BlockPos pos, BlockState state) {
        super(PRBlockEntityRegistry.PECULIAR_DOOR.get(), pos, state);
    }
    public void generateRoom(BlockPos pos, BlockState state){
        BlockPos doorPos = pos;
        int groundPos = pos.below().getY();

        LevelChunk chunk = level.getChunkAt(pos.relative(state.getValue(FACING)));
        ChunkPos chunkPos = chunk.getPos();
        if (!level.isClientSide){
            loadStructure((ServerLevel) level, pos.relative(state.getValue(FACING)), state);
        }

        level.setBlock(chunkPos.getMiddleBlockPosition(pos.getY()).offset(level.random.nextInt(-2, 3), 0, level.random.nextInt(-2, 3)), PRBlockRegistry.PECULIAR_CRYSTAL.get().defaultBlockState(), 3);

//        int minX = chunkPos.getMinBlockX();
//        int maxX = chunkPos.getMaxBlockX();
//        int minZ = chunkPos.getMinBlockZ();
//        int maxZ = chunkPos.getMaxBlockZ();



//        if (minX - 1 != pos.getX() && minX != pos.getX()){
//            int doorZ = level.random.nextInt(minZ + 1, maxZ - 1);
//            BlockPos newDoorPos = new BlockPos(minX, groundPos + 1, doorZ);
//            level.setBlock(newDoorPos, PRBlockRegistry.PECULIAR_DOOR.get().defaultBlockState().setValue(FACING, Direction.WEST).setValue(HALF, DoubleBlockHalf.LOWER), 3);
//            level.setBlock(newDoorPos.above(), PRBlockRegistry.PECULIAR_DOOR.get().defaultBlockState().setValue(FACING, Direction.WEST).setValue(HALF, DoubleBlockHalf.UPPER), 3);
//
//        }
//        if (maxX + 1 != pos.getX() && maxX != pos.getX()){
//            int doorZ = level.random.nextInt(minZ + 1, maxZ - 1);
//            BlockPos newDoorPos = new BlockPos(maxX, groundPos + 1, doorZ);
//            level.setBlock(newDoorPos, PRBlockRegistry.PECULIAR_DOOR.get().defaultBlockState().setValue(FACING, Direction.EAST).setValue(HALF, DoubleBlockHalf.LOWER), 3);
//            level.setBlock(newDoorPos.above(), PRBlockRegistry.PECULIAR_DOOR.get().defaultBlockState().setValue(FACING, Direction.EAST).setValue(HALF, DoubleBlockHalf.UPPER), 3);
//        }
//        if (minZ - 1 != pos.getZ() && minZ != pos.getZ()){
//            int doorX = level.random.nextInt(minX + 1, maxX - 1);
//            BlockPos newDoorPos = new BlockPos(doorX, groundPos + 1, minZ);
//            level.setBlock(newDoorPos, PRBlockRegistry.PECULIAR_DOOR.get().defaultBlockState().setValue(FACING, Direction.NORTH).setValue(HALF, DoubleBlockHalf.LOWER), 3);
//            level.setBlock(newDoorPos.above(), PRBlockRegistry.PECULIAR_DOOR.get().defaultBlockState().setValue(FACING, Direction.NORTH).setValue(HALF, DoubleBlockHalf.UPPER), 3);
//        }
//        if (maxZ + 1 != pos.getZ() && maxZ != pos.getZ()){
//            int doorX = level.random.nextInt(minX + 1, maxX - 1);
//            BlockPos newDoorPos = new BlockPos(doorX, groundPos + 1, maxZ);
//            level.setBlock(newDoorPos, PRBlockRegistry.PECULIAR_DOOR.get().defaultBlockState().setValue(FACING, Direction.SOUTH).setValue(HALF, DoubleBlockHalf.LOWER), 3);
//            level.setBlock(newDoorPos.above(), PRBlockRegistry.PECULIAR_DOOR.get().defaultBlockState().setValue(FACING, Direction.SOUTH).setValue(HALF, DoubleBlockHalf.UPPER), 3);
//        }
    }

    public boolean loadStructure(ServerLevel level, BlockPos pos, BlockState state) {
        return this.loadStructure(level, true, pos, state);
    }

    public static RandomSource createRandom(long p_222889_) {
        return p_222889_ == 0L ? RandomSource.create(Util.getMillis()) : RandomSource.create(p_222889_);
    }

    public boolean loadStructure(ServerLevel p_59845_, boolean p_59846_, BlockPos pos, BlockState state) {
        if (this.structureName != null) {
            StructureTemplateManager structuretemplatemanager = p_59845_.getStructureManager();
            Optional<StructureTemplate> optional;
            optional = structuretemplatemanager.get(this.structureName);
            System.out.println(optional.isEmpty());

            return this.loadStructure(p_59845_, p_59846_, optional.get(), pos, state);
        } else {
            return false;
        }
    }

    public boolean loadStructure(ServerLevel p_59848_, boolean p_59849_, StructureTemplate p_59850_, BlockPos pos, BlockState state) {
        ChunkPos chunkPos = level.getChunkAt(pos).getPos();
        BlockPos blockpos = new BlockPos(chunkPos.getMinBlockX(), pos.below().getY(), chunkPos.getMinBlockZ());
        BlockPos rotationPivot = new BlockPos(8,0,8);
            StructurePlaceSettings structureplacesettings = new StructurePlaceSettings()
                    .setMirror(Mirror.NONE)
                    .setRotation(Rotate(state))
                    .setIgnoreEntities(false)
                    .setRotationPivot(rotationPivot);
            if (1.0F < 1.0F) {
                structureplacesettings.clearProcessors()
                        .addProcessor(new BlockRotProcessor(Mth.clamp(1f, 0.0F, 1.0F)))
                        .setRandom(createRandom(0));
            }

            BlockPos blockpos1 = Offset(blockpos, state);
            p_59850_.placeInWorld(p_59848_, blockpos1, blockpos1, structureplacesettings, createRandom(0), 2);
            return true;
        }
        public Rotation Rotate(BlockState state){
            Direction facing = state.getValue(FACING);
            return switch (facing) {
                case WEST -> Rotation.CLOCKWISE_90;
                case EAST -> Rotation.COUNTERCLOCKWISE_90;
                case NORTH -> Rotation.CLOCKWISE_180;
                default -> Rotation.NONE;
            };
        }

        public BlockPos Offset(BlockPos pos, BlockState state){
            Direction facing = state.getValue(FACING);
            return switch (facing) {
                case WEST -> pos.offset(-1, 0, 0);
                case EAST -> pos.offset(0, 0, -1);
                case NORTH -> pos.offset(-1, 0, -1);
                default -> pos;
            };
        }
}
