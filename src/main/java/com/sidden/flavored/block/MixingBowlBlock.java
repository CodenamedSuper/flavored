package com.sidden.flavored.block;

import com.mojang.serialization.MapCodec;
import com.sidden.flavored.Flavored;
import com.sidden.flavored.block.entity.KegBlockEntity;
import com.sidden.flavored.block.entity.MixingBowlBlockEntity;
import com.sidden.flavored.block.property.MixingBowlLiquid;
import com.sidden.flavored.registry.FlavoredBlockEntities;
import com.sidden.flavored.registry.FlavoredItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class MixingBowlBlock extends BaseEntityBlock {

    public static final EnumProperty<MixingBowlLiquid> LIQUID;

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

        if (stack.is(Items.WATER_BUCKET)) {

            if (!level.isClientSide) {
                putLiquid(level, state, pos, MixingBowlLiquid.WATER);
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));

                ItemStack result = ItemUtils.createFilledResult(stack, player, new ItemStack(Items.BUCKET));
                level.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0f);


                player.setItemInHand(hand, result);
            }

            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        else if (stack.is(Items.POTION) && stack.get(DataComponents.POTION_CONTENTS).is(Potions.WATER)) {
            if (!level.isClientSide) {
                putLiquid(level, state, pos, MixingBowlLiquid.WATER);
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));

                ItemStack result = ItemUtils.createFilledResult(stack, player, new ItemStack(Items.GLASS_BOTTLE));
                level.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0f);

                player.setItemInHand(hand, result);
            }

            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        else if (stack.is(Items.MILK_BUCKET)) {
            if (!level.isClientSide) {
                putLiquid(level, state, pos, MixingBowlLiquid.MILK);
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));

                ItemStack result = ItemUtils.createFilledResult(stack, player, new ItemStack(Items.BUCKET));
                level.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0f);


                player.setItemInHand(hand, result);
            }

            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        else if (stack.is(Items.BUCKET)) {

            level.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0F, 1.0f);
            return takeLiquid(level, state, pos, player);

        }
        else if (stack.is(FlavoredItems.WHISK)) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof MixingBowlBlockEntity mixingBowlBlockEntity && mixingBowlBlockEntity.hasRecipe()) {
                mixingBowlBlockEntity.mix(3);

                level.playSound(null, pos, SoundEvents.COMPOSTER_FILL, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);

                return ItemInteractionResult.SUCCESS;
            }
        }



        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }



    public void putLiquid(Level level, BlockState state, BlockPos pos, MixingBowlLiquid liquid) {
        if (liquid == MixingBowlLiquid.WATER) {
            level.setBlock(pos, state.setValue(LIQUID, MixingBowlLiquid.WATER), 2);

        }
        else if(liquid == MixingBowlLiquid.MILK) {
            level.setBlock(pos, state.setValue(LIQUID, MixingBowlLiquid.MILK), 2);

        }
    }

    public ItemInteractionResult takeLiquid(Level level, BlockState state, BlockPos pos, Player player) {

        MixingBowlLiquid liquid = state.getValue(LIQUID);
        ItemStack result = ItemStack.EMPTY;

        if (liquid == MixingBowlLiquid.NONE) return ItemInteractionResult.FAIL;

        if (liquid == MixingBowlLiquid.WATER) {
            result = ItemUtils.createFilledResult(result, player, new ItemStack(Items.WATER_BUCKET));

        } else if (liquid == MixingBowlLiquid.MILK) {
            result = ItemUtils.createFilledResult(result, player, new ItemStack(Items.MILK_BUCKET));
        }

        level.setBlock(pos, state.setValue(LIQUID, MixingBowlLiquid.NONE), 2);

        return ItemInteractionResult.sidedSuccess(level.isClientSide);

    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if(state.getBlock() != newState.getBlock()) {
            if(level.getBlockEntity(pos) instanceof MixingBowlBlockEntity mixingBowlBlockEntity) {
                mixingBowlBlockEntity.drops();
                level.updateNeighbourForOutputSignal(pos, this);
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{LIQUID});
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
            player.openMenu((MenuProvider)blockentity, pos);
        }

    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            Level level, BlockState state, BlockEntityType<T> blockEntityType) {

        return createTickerHelper(blockEntityType, FlavoredBlockEntities.MIXING_BOWL.get(),
                (lvl, pos, st, be) -> be.tick(lvl, st, pos));
    }


    static {
        LIQUID = EnumProperty.create("liquid", MixingBowlLiquid.class);
    }

}
