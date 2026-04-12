package com.sidden.flavored.worldgen.feature.config;

import java.util.ArrayList;
import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;

public class TippedPillarConfiguration implements FeatureConfiguration {
    public static final Codec<TippedPillarConfiguration> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            BlockStateProvider.CODEC.fieldOf("pillar_provider").forGetter(configuration -> configuration.pillarProvider),
                            BlockStateProvider.CODEC.fieldOf("tip_provider").forGetter(configuration -> configuration.tipProvider),
                            IntProvider.codec(1, 16).fieldOf("height").forGetter(configuration -> configuration.height)
                    )
                    .apply(instance, TippedPillarConfiguration::new)
    );

    public final BlockStateProvider pillarProvider;
    public final BlockStateProvider tipProvider;
    public final IntProvider height;

    protected TippedPillarConfiguration(BlockStateProvider pillarProvider, BlockStateProvider tipProvider,  IntProvider height) {
        this.pillarProvider = pillarProvider;
        this.tipProvider = tipProvider;
        this.height = height;
    }

    public static class TippedPillarConfigurationBuilder {
        private final BlockStateProvider pillarProvider;
        private final BlockStateProvider tipProvider;
        private final IntProvider height;

        public TippedPillarConfigurationBuilder(BlockStateProvider pillarProvider, BlockStateProvider tipProvider, IntProvider height) {
            this.pillarProvider = pillarProvider;
            this.tipProvider = tipProvider;
            this.height = height;
        }

        public TippedPillarConfiguration build() {
            return new TippedPillarConfiguration(this.pillarProvider, tipProvider, height);
        }
    }
}