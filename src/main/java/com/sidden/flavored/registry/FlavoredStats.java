package com.sidden.flavored.registry;

import com.sidden.flavored.Flavored;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FlavoredStats {
    private static final DeferredRegister<ResourceLocation> CUSTOM_STATS =
            DeferredRegister.create(Registries.CUSTOM_STAT, Flavored.MOD_ID);

    public static final Holder<ResourceLocation> INTERACT_WITH_OVEN = registerCustomStat("interact_with_oven");

    private static Holder<ResourceLocation> registerCustomStat(String key) {
        ResourceLocation resourcelocation = ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, key);
        return CUSTOM_STATS.register(key, () -> resourcelocation);
    }

    public static void init(IEventBus eventBus) {
        CUSTOM_STATS.register(eventBus);
    }
}
