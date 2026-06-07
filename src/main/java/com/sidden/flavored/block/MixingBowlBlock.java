package com.sidden.flavored.block;

import com.mojang.serialization.MapCodec;
import com.sidden.flavored.block.entity.MixingBowlBlockEntity;
import com.sidden.flavored.registry.FlavoredBlockEntities;
import com.sidden.flavored.registry.FlavoredItems;
import com.sidden.flavored.registry.FlavoredStats;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class MixingBowlBlock extends BaseEntityBlock {

    public MixingBowlBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Stream.of(
                Block.box(1, 0, 1, 15, 2, 15),
                Block.box(13, 2, 1, 15, 7, 15),
                Block.box(3, 2, 1, 13, 7, 3),
                Block.box(3, 2, 13, 13, 7, 15),
                Block.box(1, 2, 1, 3, 7, 15)
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new MixingBowlBlockEntity(blockPos, blockState);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.is(FlavoredItems.WHISK)) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof MixingBowlBlockEntity mixingBowlBlockEntity && mixingBowlBlockEntity.hasValidRecipe()) {
                mixingBowlBlockEntity.mix(1, player);

                stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));

                level.playSound(null, pos, SoundEvents.COMPOSTER_FILL, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);

                return ItemInteractionResult.SUCCESS;
            }
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }


    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.getBlock() != newState.getBlock()) {
            if (level.getBlockEntity(pos) instanceof MixingBowlBlockEntity mixingBowlBlockEntity) {
                Containers.dropContents(level, pos, mixingBowlBlockEntity);
                level.updateNeighbourForOutputSignal(pos, this);
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            this.openContainer(level, pos, player);
            return InteractionResult.CONSUME;
        }
    }

    protected void openContainer(Level level, BlockPos pos, Player player) {
        if (player.getMainHandItem().is(FlavoredItems.WHISK)) return;

        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof MixingBowlBlockEntity) {
            player.awardStat(FlavoredStats.INTERACT_WITH_MIXING_BOWL.value());
            player.openMenu((MenuProvider) blockentity, pos);
        }
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, FlavoredBlockEntities.MIXING_BOWL.get(), MixingBowlBlockEntity::tick);
    }
}
