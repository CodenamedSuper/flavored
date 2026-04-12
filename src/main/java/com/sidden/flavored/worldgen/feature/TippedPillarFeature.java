package com.sidden.flavored.worldgen.feature;

import com.mojang.serialization.Codec;
import com.sidden.flavored.worldgen.feature.config.TippedPillarConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class TippedPillarFeature extends Feature<TippedPillarConfiguration> {
    public TippedPillarFeature(Codec<TippedPillarConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<TippedPillarConfiguration> featurePlaceContext) {
        WorldGenLevel level = featurePlaceContext.level();
        BlockPos pos = featurePlaceContext.origin();

        if (!level.getBlockState(pos.below()).is(Blocks.DIRT) ) return false;

        final int height = featurePlaceContext.config().height.sample(featurePlaceContext.random());

        for (int i = 0; i < height; i++) {
            level.setBlock(pos, featurePlaceContext.config().pillarProvider.getState(featurePlaceContext.random(), pos), 2);
            pos = pos.above();
        }

        level.setBlock(pos, featurePlaceContext.config().tipProvider.getState(featurePlaceContext.random(), pos), 2);

        return true;

    }
}