package com.codenamed.flavored.block;

import com.codenamed.flavored.registry.FlavoredBlocks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RawCheeseBlock extends Block {

    private final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 5, 15);

    public static final BooleanProperty WAXED = BooleanProperty.create("waxed");

    public RawCheeseBlock(Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(WAXED, false)));

    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {

        if (stack.is(Items.HONEYCOMB) && !state.getValue(WAXED)) {
            level.setBlock(pos, state.setValue(WAXED, true), 2);
            player.getUseItem().shrink(1);

            return  ItemInteractionResult.SUCCESS;
        }

        if (stack.is(ItemTags.AXES) && state.getValue(WAXED)) {
            level.setBlock(pos, state.setValue(WAXED, false), 2);

            return  ItemInteractionResult.SUCCESS;
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {

        if (state.getValue(WAXED)) return;

        if (random.nextInt(0, 9) == 0) {
            level.setBlock(pos, FlavoredBlocks.CHEESE.get().defaultBlockState(), 2);
        }

        super.randomTick(state, level, pos, random);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {

        builder.add(WAXED);

        super.createBlockStateDefinition(builder);
    }
}
