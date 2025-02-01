package com.codenamed.flavored.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DoughBlock extends Block {

    public static final IntegerProperty STAGE = IntegerProperty.create("stage", 0, 2);

    protected static final VoxelShape[] SHAPE_BY_STAGE;


    public DoughBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return  SHAPE_BY_STAGE[state.getValue(STAGE)];
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {

        if (state.getValue(STAGE) < 2) {
            level.setBlock(pos, state.setValue(STAGE, state.getValue(STAGE) + 1), 2);
            level.playSound(player, pos, SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 0.3F, 0.5F);

            return InteractionResult.SUCCESS;
        }

        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {

        builder.add(STAGE);

        super.createBlockStateDefinition(builder);
    }

    static {
        SHAPE_BY_STAGE = new VoxelShape[]{Block.box(4, 0, 4, 12, 4, 12), Block.box(3, 0, 3, 13, 3, 13), Block.box(2, 0, 2, 14, 2, 14)};

    }
}
