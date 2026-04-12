package com.sidden.flavored.block;

import com.sidden.flavored.registry.FlavoredBlocks;
import com.sidden.flavored.registry.FlavoredItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class StrippedCinnamonStalkBlock extends RotatedPillarBlock {

    public StrippedCinnamonStalkBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {

        if (random.nextInt(0, 7) == 0) {
            level.setBlock(pos, FlavoredBlocks.CINNAMON_STALK.get().defaultBlockState(), 2);
        }


        super.randomTick(state, level, pos, random);
    }
}
