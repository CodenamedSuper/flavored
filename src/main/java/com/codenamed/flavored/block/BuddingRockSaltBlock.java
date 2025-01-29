package com.codenamed.flavored.block;

import com.codenamed.flavored.registry.FlavoredBlocks;
import net.minecraft.world.level.block.Block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

public class BuddingRockSaltBlock extends RockSaltBlock {
    public static final MapCodec<BuddingRockSaltBlock> CODEC = simpleCodec(BuddingRockSaltBlock::new);
    public static final int GROWTH_CHANCE = 5;
    private static final Direction[] DIRECTIONS = Direction.values();

    public MapCodec<BuddingRockSaltBlock> codec() {
        return CODEC;
    }

    public BuddingRockSaltBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextInt(5) == 0) {
            Direction direction = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
            BlockPos blockpos = pos.relative(direction);
            BlockState blockstate = level.getBlockState(blockpos);
            Block block = null;
            if (canClusterGrowAtState(blockstate)) {
                block = FlavoredBlocks.SMALL_SALT_BUD.get();
            } else if (blockstate.is(FlavoredBlocks.SMALL_SALT_BUD.get()) && blockstate.getValue(SaltClusterBlock.FACING) == direction) {
                block = FlavoredBlocks.MEDIUM_SALT_BUD.get();
            } else if (blockstate.is(FlavoredBlocks.MEDIUM_SALT_BUD.get()) && blockstate.getValue(SaltClusterBlock.FACING) == direction) {
                block = FlavoredBlocks.LARGE_SALT_BUD.get();
            } else if (blockstate.is(FlavoredBlocks.LARGE_SALT_BUD.get()) && blockstate.getValue(SaltClusterBlock.FACING) == direction) {
                block = FlavoredBlocks.SALT_CLUSTER.get();
            }

            if (block != null) {
                BlockState blockstate1 = (BlockState)((BlockState)block.defaultBlockState().setValue(SaltClusterBlock.FACING, direction)).setValue(SaltClusterBlock.WATERLOGGED, blockstate.getFluidState().getType() == Fluids.WATER);
                level.setBlockAndUpdate(blockpos, blockstate1);
            }
        }

    }

    public static boolean canClusterGrowAtState(BlockState state) {
        return state.isAir() || state.is(Blocks.WATER) && state.getFluidState().getAmount() == 8;
    }
}