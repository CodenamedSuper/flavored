package com.sidden.flavored.registry;

import com.sidden.flavored.Flavored;
import com.sidden.flavored.worldgen.feature.TippedPillarFeature;
import com.sidden.flavored.worldgen.feature.config.TippedPillarConfiguration;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FlavoredFeatures<FC extends FeatureConfiguration> {


    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, Flavored.MOD_ID);

    public static final DeferredHolder TIPPED_PILLAR = FEATURES.register("tipped_pillar", () ->
            new TippedPillarFeature(TippedPillarConfiguration.CODEC));


    public static void init(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }
}
