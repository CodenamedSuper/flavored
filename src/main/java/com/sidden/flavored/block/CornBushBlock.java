package com.sidden.flavored.block;

import com.sidden.flavored.registry.FlavoredItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;

import com.mojang.serialization.MapCodec;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.CommonHooks;

public class CornBushBlock extends BushBlock implements BonemealableBlock {
    public static final MapCodec<CornBushBlock> CODEC = simpleCodec(CornBushBlock::new);
    public static final EnumProperty<DoubleBlockHalf> HALF;
    public static final IntegerProperty AGE;

    private static final VoxelShape SAPLING_SHAPE;
    private static final VoxelShape ADULT_SHAPE;

    public static int MAX_AGE = 5;

    public MapCodec<? extends CornBushBlock> codec() {
        return CODEC;
    }

    public CornBushBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(HALF, DoubleBlockHalf.LOWER));
    }

    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(AGE) <= 1) {
            return SAPLING_SHAPE;
        } else {
            return state.getValue(AGE) < MAX_AGE ? ADULT_SHAPE : super.getShape(state, level, pos, context);
        }
    }

    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        return new ItemStack(FlavoredItems.CORN_SEEDS.get());
    }

    protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        DoubleBlockHalf doubleblockhalf = (DoubleBlockHalf)state.getValue(HALF);
        if (facing.getAxis() != Axis.Y || doubleblockhalf == DoubleBlockHalf.LOWER != (facing == Direction.UP) || facingState.is(this) && facingState.getValue(HALF) != doubleblockhalf) {
            return doubleblockhalf == DoubleBlockHalf.LOWER && facing == Direction.DOWN && !state.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, facingState, level, currentPos, facingPos);
        } else {
            return Blocks.AIR.defaultBlockState();
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        Level level = context.getLevel();
        return blockpos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockpos.above()).canBeReplaced(context) ? super.getStateForPlacement(context) : null;
    }

    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int i = (Integer) state.getValue(AGE);
        if (i < MAX_AGE && level.getRawBrightness(pos.above(), 0) >= 9 && CommonHooks.canCropGrow(level, pos, state, random.nextInt(5) == 0)) {
            grow(level, state, pos);
        }

    }

    public void grow(Level level, BlockState state, BlockPos pos) {
        int age = state.getValue(AGE);
        if (age >= MAX_AGE) return;
        int newAge = age + 1;

        if (isLower(state)) {
            level.setBlock(pos, state.setValue(AGE, newAge), 2);

            if (newAge >= 2) {
                BlockPos above = pos.above();
                BlockState upper = this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER).setValue(AGE, newAge);

                level.setBlock(above, upper, 2);
            }
        }
        else {
            BlockPos below = pos.below();
            BlockState lower = level.getBlockState(below);

            if (lower.is(this)) {
                level.setBlock(below, lower.setValue(AGE, newAge), 2);level.setBlock(pos, state.setValue(AGE, newAge), 2);
            }
        }

        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(state));
        CommonHooks.fireCropGrowPost(level, pos, state);
    }

    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        int i = (Integer) state.getValue(AGE);
        boolean flag = i == MAX_AGE;
        return !flag && stack.is(Items.BONE_MEAL) ? ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION : super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        int age = (Integer) state.getValue(AGE);
        boolean flag = age == MAX_AGE;
        if (age >= 4) {
            BlockPos basePos = isLower(state) ? pos : pos.below();

            int drops = 1 + level.random.nextInt(2) + (age - 3);

            popResource(level, basePos, new ItemStack(FlavoredItems.CORN.get(), drops));
            level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
            BlockState newState = state.setValue(AGE, 3);

            level.setBlock(basePos, newState.setValue(HALF, DoubleBlockHalf.LOWER), 2);

            if (level.getBlockState(basePos.above()).is(this)) {
                level.setBlock(basePos.above(), newState.setValue(HALF, DoubleBlockHalf.UPPER), 2);
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        else {
            return super.useWithoutItem(state, level, pos, player, hitResult);
        }
    }

    public boolean isLower(BlockState state) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER;
    }

    public boolean isUpper(BlockState state) {
        return state.getValue(HALF) == DoubleBlockHalf.UPPER;
    }

    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getBlock() instanceof FarmBlock;
    }

    public boolean isYoung(BlockState state) {
        return  state.getValue(AGE) <= 1;
    }

    protected boolean isRandomlyTicking(BlockState state) {
        return (Integer) state.getValue(AGE) < MAX_AGE;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (state.getValue(AGE) >= 2) {
            BlockPos above = pos.above();
            level.setBlock(above, state.setValue(HALF, DoubleBlockHalf.UPPER), 3);
        }
    }

    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if (state.getValue(HALF) != DoubleBlockHalf.UPPER) {
            return super.canSurvive(state, level, pos);
        } else {
            BlockState blockstate = level.getBlockState(pos.below());
            if (state.getBlock() != this) {
                return super.canSurvive(state, level, pos);
            } else {
                return blockstate.is(this) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER;
            }
        }
    }


    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide) {
            if (player.isCreative()) {
                preventDropFromBottomPart(level, pos, state, player);
            } else {
                dropResources(state, level, pos, (BlockEntity)null, player, player.getMainHandItem());
            }
        }

        return super.playerWillDestroy(level, pos, state, player);
    }

    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity te, ItemStack stack) {
        super.playerDestroy(level, player, pos, Blocks.AIR.defaultBlockState(), te, stack);
    }

    protected static void preventDropFromBottomPart(Level level, BlockPos pos, BlockState state, Player player) {
        DoubleBlockHalf doubleblockhalf = (DoubleBlockHalf)state.getValue(HALF);
        if (doubleblockhalf == DoubleBlockHalf.UPPER) {
            BlockPos blockpos = pos.below();
            BlockState blockstate = level.getBlockState(blockpos);
            if (blockstate.is(state.getBlock()) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER) {
                BlockState blockstate1 = blockstate.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
                level.setBlock(blockpos, blockstate1, 35);
                level.levelEvent(player, 2001, blockpos, Block.getId(blockstate));
            }
        }

    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{HALF, AGE});
    }

    protected long getSeed(BlockState state, BlockPos pos) {
        return Mth.getSeed(pos.getX(), pos.below(state.getValue(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), pos.getZ());
    }

    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return (Integer) state.getValue(AGE) < MAX_AGE;
    }

    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        grow(level, state, pos);
    }

    static {
        HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
        AGE = BlockStateProperties.AGE_5;

        SAPLING_SHAPE = Block.box(3.0, 0.0, 3.0, 13.0, 8.0, 13.0);
        ADULT_SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);
    }

}