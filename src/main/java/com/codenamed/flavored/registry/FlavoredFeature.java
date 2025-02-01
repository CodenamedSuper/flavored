package com.codenamed.flavored.registry;

import com.codenamed.flavored.Flavored;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FlavoredFeature<FC extends FeatureConfiguration> {


    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, Flavored.MOD_ID);


    public static void init(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }
}
