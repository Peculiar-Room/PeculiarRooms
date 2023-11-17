package com.peculiarrooms.server.blocks;

import com.peculiarrooms.server.blocks.blockentity.PeculiarDoorBlockEntity;
import com.peculiarrooms.server.registries.PRBlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class PeculiarDoorBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final EnumProperty<DoorHingeSide> HINGE = BlockStateProperties.DOOR_HINGE;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    protected static final float AABB_DOOR_THICKNESS = 3.0F;
    protected static final VoxelShape SOUTH_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 3.0);
    protected static final VoxelShape NORTH_AABB = Block.box(0.0, 0.0, 13.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape WEST_AABB = Block.box(13.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape EAST_AABB = Block.box(0.0, 0.0, 0.0, 3.0, 16.0, 16.0);
    private final BlockSetType type;

    public PeculiarDoorBlock(BlockBehaviour.Properties p_273303_, BlockSetType p_272854_) {
        super(p_273303_.sound(p_272854_.soundType()));
        this.type = p_272854_;
        this.registerDefaultState(
                this.stateDefinition
                        .any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(OPEN, Boolean.valueOf(false))
                        .setValue(HINGE, DoorHingeSide.LEFT)
                        .setValue(POWERED, Boolean.valueOf(false))
                        .setValue(HALF, DoubleBlockHalf.LOWER)
        );
    }

    public BlockSetType type() {
        return this.type;
    }

    @Override
    public VoxelShape getShape(BlockState p_52807_, BlockGetter p_52808_, BlockPos p_52809_, CollisionContext p_52810_) {
        Direction direction = p_52807_.getValue(FACING);
        boolean flag = !p_52807_.getValue(OPEN);
        boolean flag1 = p_52807_.getValue(HINGE) == DoorHingeSide.RIGHT;
        switch(direction) {
            case EAST:
            default:
                return flag ? EAST_AABB : (flag1 ? NORTH_AABB : SOUTH_AABB);
            case SOUTH:
                return flag ? SOUTH_AABB : (flag1 ? EAST_AABB : WEST_AABB);
            case WEST:
                return flag ? WEST_AABB : (flag1 ? SOUTH_AABB : NORTH_AABB);
            case NORTH:
                return flag ? NORTH_AABB : (flag1 ? WEST_AABB : EAST_AABB);
        }
    }

    @Override
    public BlockState updateShape(BlockState p_52796_, Direction p_52797_, BlockState p_52798_, LevelAccessor p_52799_, BlockPos p_52800_, BlockPos p_52801_) {
        DoubleBlockHalf doubleblockhalf = p_52796_.getValue(HALF);
        if (p_52797_.getAxis() != Direction.Axis.Y || doubleblockhalf == DoubleBlockHalf.LOWER != (p_52797_ == Direction.UP)) {
            return doubleblockhalf == DoubleBlockHalf.LOWER && p_52797_ == Direction.DOWN && !p_52796_.canSurvive(p_52799_, p_52800_)
                    ? Blocks.AIR.defaultBlockState()
                    : super.updateShape(p_52796_, p_52797_, p_52798_, p_52799_, p_52800_, p_52801_);
        } else {
            return p_52798_.is(this) && p_52798_.getValue(HALF) != doubleblockhalf
                    ? p_52796_.setValue(FACING, p_52798_.getValue(FACING))
                    .setValue(OPEN, p_52798_.getValue(OPEN))
                    .setValue(HINGE, p_52798_.getValue(HINGE))
                    .setValue(POWERED, p_52798_.getValue(POWERED))
                    : Blocks.AIR.defaultBlockState();
        }
    }

    @Override
    public void playerWillDestroy(Level p_52755_, BlockPos p_52756_, BlockState p_52757_, Player p_52758_) {
        if (!p_52755_.isClientSide && p_52758_.isCreative()) {
            preventCreativeDropFromBottomPart(p_52755_, p_52756_, p_52757_, p_52758_);
        }

        super.playerWillDestroy(p_52755_, p_52756_, p_52757_, p_52758_);
    }

    protected static void preventCreativeDropFromBottomPart(Level p_52904_, BlockPos p_52905_, BlockState p_52906_, Player p_52907_) {
        DoubleBlockHalf doubleblockhalf = p_52906_.getValue(HALF);
        if (doubleblockhalf == DoubleBlockHalf.UPPER) {
            BlockPos blockpos = p_52905_.below();
            BlockState blockstate = p_52904_.getBlockState(blockpos);
            if (blockstate.is(p_52906_.getBlock()) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER) {
                BlockState blockstate1 = blockstate.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
                p_52904_.setBlock(blockpos, blockstate1, 35);
                p_52904_.levelEvent(p_52907_, 2001, blockpos, Block.getId(blockstate));
            }
        }
    }
    @Override
    public boolean isPathfindable(BlockState p_52764_, BlockGetter p_52765_, BlockPos p_52766_, PathComputationType p_52767_) {
        switch(p_52767_) {
            case LAND:
                return p_52764_.getValue(OPEN);
            case WATER:
                return false;
            case AIR:
                return p_52764_.getValue(OPEN);
            default:
                return false;
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_52739_) {
        BlockPos blockpos = p_52739_.getClickedPos();
        Level level = p_52739_.getLevel();
        if (blockpos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockpos.above()).canBeReplaced(p_52739_)) {
            boolean flag = level.hasNeighborSignal(blockpos) || level.hasNeighborSignal(blockpos.above());
            return this.defaultBlockState()
                    .setValue(FACING, p_52739_.getHorizontalDirection())
                    .setValue(HINGE, this.getHinge(p_52739_))
                    .setValue(POWERED, Boolean.valueOf(flag))
                    .setValue(OPEN, Boolean.valueOf(flag))
                    .setValue(HALF, DoubleBlockHalf.LOWER);
        } else {
            return null;
        }
    }

    @Override
    public void setPlacedBy(Level p_52749_, BlockPos p_52750_, BlockState p_52751_, LivingEntity p_52752_, ItemStack p_52753_) {
        p_52749_.setBlock(p_52750_.above(), p_52751_.setValue(HALF, DoubleBlockHalf.UPPER), 3);
    }

    private DoorHingeSide getHinge(BlockPlaceContext context) {
        BlockGetter blockgetter = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        Direction direction = context.getHorizontalDirection();
        BlockPos blockpos1 = blockpos.above();
        Direction direction1 = direction.getCounterClockWise();
        BlockPos blockpos2 = blockpos.relative(direction1);
        BlockState blockstate = blockgetter.getBlockState(blockpos2);
        BlockPos blockpos3 = blockpos1.relative(direction1);
        BlockState blockstate1 = blockgetter.getBlockState(blockpos3);
        Direction direction2 = direction.getClockWise();
        BlockPos blockpos4 = blockpos.relative(direction2);
        BlockState blockstate2 = blockgetter.getBlockState(blockpos4);
        BlockPos blockpos5 = blockpos1.relative(direction2);
        BlockState blockstate3 = blockgetter.getBlockState(blockpos5);
        int i = (blockstate.isCollisionShapeFullBlock(blockgetter, blockpos2) ? -1 : 0)
                + (blockstate1.isCollisionShapeFullBlock(blockgetter, blockpos3) ? -1 : 0)
                + (blockstate2.isCollisionShapeFullBlock(blockgetter, blockpos4) ? 1 : 0)
                + (blockstate3.isCollisionShapeFullBlock(blockgetter, blockpos5) ? 1 : 0);
        boolean flag = blockstate.is(this) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER;
        boolean flag1 = blockstate2.is(this) && blockstate2.getValue(HALF) == DoubleBlockHalf.LOWER;
        if ((!flag || flag1) && i <= 0) {
            if ((!flag1 || flag) && i >= 0) {
                int j = direction.getStepX();
                int k = direction.getStepZ();
                Vec3 vec3 = context.getClickLocation();
                double d0 = vec3.x - (double)blockpos.getX();
                double d1 = vec3.z - (double)blockpos.getZ();
                return (j >= 0 || !(d1 < 0.5)) && (j <= 0 || !(d1 > 0.5)) && (k >= 0 || !(d0 > 0.5)) && (k <= 0 || !(d0 < 0.5))
                        ? DoorHingeSide.LEFT
                        : DoorHingeSide.RIGHT;
            } else {
                return DoorHingeSide.LEFT;
            }
        } else {
            return DoorHingeSide.RIGHT;
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (!this.type.canOpenByHand()) {
            return InteractionResult.PASS;
        } else {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof PeculiarDoorBlockEntity) {
                BlockPos placePos = pos;
                if (state.getValue(HALF).equals(DoubleBlockHalf.UPPER))
                {
                    placePos = placePos.below();
                }
                ((PeculiarDoorBlockEntity) blockentity).generateRoom(placePos, state);
            }
            state = state.cycle(OPEN);
            level.setBlock(pos, state, 10);
            this.playSound(player, level, pos, state.getValue(OPEN));
            level.gameEvent(player, this.isOpen(state) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
    }

    public boolean isOpen(BlockState p_52816_) {
        return p_52816_.getValue(OPEN);
    }

    public void setOpen(@Nullable Entity p_153166_, Level p_153167_, BlockState p_153168_, BlockPos p_153169_, boolean p_153170_) {
        if (p_153168_.is(this) && p_153168_.getValue(OPEN) != p_153170_) {
            p_153167_.setBlock(p_153169_, p_153168_.setValue(OPEN, Boolean.valueOf(p_153170_)), 10);
            this.playSound(p_153166_, p_153167_, p_153169_, p_153170_);
            p_153167_.gameEvent(p_153166_, p_153170_ ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, p_153169_);
        }
    }

    @Override
    public void neighborChanged(BlockState p_52776_, Level p_52777_, BlockPos p_52778_, Block p_52779_, BlockPos p_52780_, boolean p_52781_) {
        boolean flag = p_52777_.hasNeighborSignal(p_52778_)
                || p_52777_.hasNeighborSignal(p_52778_.relative(p_52776_.getValue(HALF) == DoubleBlockHalf.LOWER ? Direction.UP : Direction.DOWN));
        if (!this.defaultBlockState().is(p_52779_) && flag != p_52776_.getValue(POWERED)) {
            if (flag != p_52776_.getValue(OPEN)) {
                this.playSound(null, p_52777_, p_52778_, flag);
                p_52777_.gameEvent(null, flag ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, p_52778_);
            }

            p_52777_.setBlock(p_52778_, p_52776_.setValue(POWERED, Boolean.valueOf(flag)).setValue(OPEN, Boolean.valueOf(flag)), 2);
        }
    }

    @Override
    public boolean canSurvive(BlockState p_52783_, LevelReader p_52784_, BlockPos p_52785_) {
        BlockPos blockpos = p_52785_.below();
        BlockState blockstate = p_52784_.getBlockState(blockpos);
        return p_52783_.getValue(HALF) == DoubleBlockHalf.LOWER ? blockstate.isFaceSturdy(p_52784_, blockpos, Direction.UP) : blockstate.is(this);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_53840_) {
        return RenderShape.MODEL;
    }

    private void playSound(@Nullable Entity p_251616_, Level p_249656_, BlockPos p_249439_, boolean p_251628_) {
        p_249656_.playSound(
                p_251616_,
                p_249439_,
                p_251628_ ? this.type.doorOpen() : this.type.doorClose(),
                SoundSource.BLOCKS,
                1.0F,
                p_249656_.getRandom().nextFloat() * 0.1F + 0.9F
        );
    }

    @Override
    public BlockState rotate(BlockState p_52790_, Rotation p_52791_) {
        return p_52790_.setValue(FACING, p_52791_.rotate(p_52790_.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState p_52787_, Mirror p_52788_) {
        return p_52788_ == Mirror.NONE ? p_52787_ : p_52787_.rotate(p_52788_.getRotation(p_52787_.getValue(FACING))).cycle(HINGE);
    }

    @Override
    public long getSeed(BlockState p_52793_, BlockPos p_52794_) {
        return Mth.getSeed(p_52794_.getX(), p_52794_.below(p_52793_.getValue(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), p_52794_.getZ());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_52803_) {
        p_52803_.add(HALF, FACING, OPEN, HINGE, POWERED);
    }

    public static boolean isWoodenDoor(Level p_52746_, BlockPos p_52747_) {
        return isWoodenDoor(p_52746_.getBlockState(p_52747_));
    }

    public static boolean isWoodenDoor(BlockState state) {
        Block block = state.getBlock();
        if (block instanceof DoorBlock doorblock && doorblock.type().canOpenByHand()) {
            return true;
        }

        return false;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PeculiarDoorBlockEntity(pos, state);
    }
    
    
}