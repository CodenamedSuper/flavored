package com.codenamed.flavored.block_entity;

import com.codenamed.flavored.registry.FlavoredBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public class FermenterBlockEntity extends BlockEntity {

    public static final int INPUT_SLOT = 0;
    public static final int FERMENTING_SLOT = 1;
    public static final int OUTPUT_SLOT = 2;

    public FermenterBlockEntity(BlockPos pos, BlockState blockState) {
        super(FlavoredBlockEntities.FERMENTER.get(), pos, blockState);
    }
}
