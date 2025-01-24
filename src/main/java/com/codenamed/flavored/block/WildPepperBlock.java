package com.codenamed.flavored.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.BushBlock;

public class WildPepperBlock extends BushBlock {
    public WildPepperBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return null;
    }
}
