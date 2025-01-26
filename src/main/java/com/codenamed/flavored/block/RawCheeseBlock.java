package com.codenamed.flavored.block;

import com.codenamed.flavored.registry.FlavoredBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RawCheeseBlock extends Block {

    private final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 5, 15);

    public RawCheeseBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {


        level.setBlock(pos, FlavoredBlocks.CHEESE.get().defaultBlockState(), 2);

        super.randomTick(state, level, pos, random);
    }
}
