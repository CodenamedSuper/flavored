package com.sidden.flavored.block;

import com.sidden.flavored.registry.FlavoredBlocks;
import com.sidden.flavored.registry.FlavoredParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.checkerframework.checker.units.qual.A;

public class SoftCheeseBlock extends Block {
    public static final BooleanProperty WAXED = BooleanProperty.create("waxed");
    public static final IntegerProperty AGE;

    public SoftCheeseBlock(Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(WAXED, false)));

    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Block.box(0, 0, 0, 16, 8, 16);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {

        if (stack.is(Items.HONEYCOMB) && !state.getValue(WAXED)) {
            level.setBlock(pos, state.setValue(WAXED, true), 2);
            player.getUseItem().shrink(1);
            level.playSound((Player)null, pos, SoundEvents.HONEYCOMB_WAX_ON, SoundSource.BLOCKS, 1.0F, 1.0F);

            return  ItemInteractionResult.SUCCESS;
        }

        if (stack.is(ItemTags.AXES) && state.getValue(WAXED)) {
            level.setBlock(pos, state.setValue(WAXED, false), 2);
            level.playSound((Player)null, pos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);

            return  ItemInteractionResult.SUCCESS;
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {

        if (state.getValue(WAXED)) return;

        if (random.nextInt(0, 10) == 0) {

            if (state.getValue(AGE) >= 1) {

                level.setBlock(pos, FlavoredBlocks.AGED_CHEESE.get().defaultBlockState(), 2);
            }
            else {
                level.setBlock(pos, state.setValue(AGE, 1), 2);

            }

            level.playSound((Player)null, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);

            ((ServerLevel) level).sendParticles(FlavoredParticles.CHEESE_AGING.get(),
                    pos.getX() + (double) random.nextInt(5) / 10,
                    pos.getY() + 1.0,
                    pos.getZ() + (double) random.nextInt(5) / 10,
                    5, 0, 0, 0, 3);
        }

        super.randomTick(state, level, pos, random);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {

        builder.add(WAXED);
        builder.add(AGE);

        super.createBlockStateDefinition(builder);
    }

    static {
        AGE = BlockStateProperties.AGE_1;
    }


}
