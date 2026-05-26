package com.sidden.flavored.block;

import com.mojang.serialization.MapCodec;
import com.sidden.flavored.registry.FlavoredBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CinnamonSproutBlock extends BushBlock implements BonemealableBlock {
    public CinnamonSproutBlock(Properties properties) {
        super(properties);
    }

    public static final MapCodec<CinnamonSproutBlock> CODEC = simpleCodec(CinnamonSproutBlock::new);


    public MapCodec<CinnamonSproutBlock> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Block.box(3.0, 0.0, 3.0, 13.0, 8.0, 13.0);
    }


    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return super.mayPlaceOn(state, level, pos) || state.is(FlavoredBlocks.CINNAMON_STALK) || state.is(FlavoredBlocks.STRIPPED_CINNAMON_STALK);
    }


    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return randomSource.nextInt(4) == 0;
    }

    @Override
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        return false;
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        serverLevel.setBlock(blockPos, FlavoredBlocks.CINNAMON_STALK.get().defaultBlockState(), 2);

        if (serverLevel.getBlockState(blockPos.above()).isAir()) {
            serverLevel.setBlock(blockPos.above(), FlavoredBlocks.CINNAMON_SPROUT.get().defaultBlockState(), 2);
        }

    }
}
