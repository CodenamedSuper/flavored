package com.sidden.flavored.registry;

import com.mojang.serialization.Codec;
import com.sidden.flavored.Flavored;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class FlavoredDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Flavored.MOD_ID);

    public static final Supplier<DataComponentType<Integer>> SPICINESS = register("spiciness",
            ()-> DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());


    private static <T>DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, Supplier<DataComponentType<T>> builderOperator) {
        return DATA_COMPONENT_TYPES.register(name, builderOperator);
    }

    public static void init(IEventBus eventBus) {
        DATA_COMPONENT_TYPES.register(eventBus);
    }
}
