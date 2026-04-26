package com.sidden.flavored.block;

import com.mojang.serialization.MapCodec;
import com.sidden.flavored.registry.FlavoredEffects;
import com.sidden.flavored.registry.FlavoredItems;
import com.sidden.flavored.registry.FlavoredStats;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class PizzaBlock extends Block {
    public static final MapCodec<PizzaBlock> CODEC = simpleCodec(PizzaBlock::new);
    public static final int MAX_BITES = 3;
    public static final IntegerProperty BITES;
    public static final int FULL_CAKE_SIGNAL;
    protected static final float AABB_OFFSET = 1.0F;
    protected static final float AABB_SIZE_PER_BITE = 2.0F;
    protected static final VoxelShape[] SHAPE_BY_BITE;

    public MapCodec<PizzaBlock> codec() {
        return CODEC;
    }

    public PizzaBlock(Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(BITES, 0));
    }

    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_BITE[(Integer)state.getValue(BITES)];
    }

    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            if (eat(level, pos, state, player, InteractionHand.MAIN_HAND).consumesAction()) {
                return InteractionResult.SUCCESS;
            }

            if (player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
                return InteractionResult.CONSUME;
            }
        }

        return eat(level, pos, state, player, InteractionHand.MAIN_HAND);
    }

    protected static InteractionResult eat(LevelAccessor level, BlockPos pos, BlockState state, Player player, InteractionHand hand) {
        if (!player.canEat(false)) {
            return InteractionResult.PASS;
        } else {
            player.awardStat(FlavoredStats.TAKE_PIZZA_SLICE.value());

            ItemStack stack = FlavoredItems.PIZZA_SLICE.toStack();
            if (player.getItemInHand(hand).isEmpty()) {
                player.setItemInHand(hand, stack);
            } else {
                player.addItem(stack);
            }

            int i = state.getValue(BITES);
            level.gameEvent(player, GameEvent.EAT, pos);

            if (i < MAX_BITES) {
                level.setBlock(pos, state.setValue(BITES, i + 1), 3);
            } else {
                level.removeBlock(pos, false);
                level.gameEvent(player, GameEvent.BLOCK_DESTROY, pos);
            }

            return InteractionResult.SUCCESS;
        }
    }

    protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        return facing == Direction.DOWN && !state.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).isSolid();
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{BITES});
    }

    protected int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
        return getOutputSignal((Integer)blockState.getValue(BITES));
    }

    public static int getOutputSignal(int eaten) {
        return (MAX_BITES + 1 - eaten) * 2;
    }

    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    static {
        BITES = IntegerProperty.create("bites", 0, MAX_BITES);
        FULL_CAKE_SIGNAL = getOutputSignal(0);
        SHAPE_BY_BITE = new VoxelShape[]{
                Stream.of(
                        Block.box(1, 0, 8, 8, 3, 15),
                        Block.box(8, 0, 8, 15, 3, 15),
                        Block.box(1, 0, 1, 8, 3, 8),
                        Block.box(8, 0, 1, 15, 3, 8)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                Stream.of(
                        Block.box(1, 0, 8, 8, 3, 15),
                        Block.box(8, 0, 8, 15, 3, 15),
                        Block.box(8, 0, 1, 15, 3, 8)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                Shapes.join(Block.box(1, 0, 8, 8, 3, 15), Block.box(8, 0, 8, 15, 3, 15), BooleanOp.OR),
                Block.box(8, 0, 8, 15, 3, 15)
        };
    }
}