package com.sidden.flavored.block;

import com.sidden.flavored.registry.FlavoredBlocks;
import com.sidden.flavored.registry.FlavoredItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import javax.annotation.Nullable;

public class CinnamonStalkBlock extends RotatedPillarBlock {
    public CinnamonStalkBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility toolAction, boolean simulate) {
        if (toolAction == ItemAbilities.AXE_STRIP) {
            Level level = context.getLevel();
            BlockPos pos = context.getClickedPos();
            Player player = context.getPlayer();

            if (!simulate) {

                ItemEntity itemEntity = new ItemEntity(level,
                        pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        new ItemStack(FlavoredItems.CINNAMON.get(), context.getLevel().random.nextInt(2, 5)));
                level.addFreshEntity(itemEntity);

                level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
            }

            return FlavoredBlocks.STRIPPED_CINNAMON_STALK.get().defaultBlockState();
        }

        return super.getToolModifiedState(state, context, toolAction, simulate);
    }
}
