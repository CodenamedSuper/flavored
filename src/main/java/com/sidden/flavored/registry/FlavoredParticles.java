package com.sidden.flavored.registry;

import com.mojang.serialization.MapCodec;
import com.sidden.flavored.Flavored;
import com.sidden.flavored.particle.type.option.FlavoredColorParticleOption;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FlavoredParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Flavored.MOD_ID);

    public static final Supplier<SimpleParticleType> CHEESE_AGING =
            PARTICLE_TYPES.register("cheese_aging", () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> DRIPPING_CHOCOLATE =
            PARTICLE_TYPES.register("dripping_chocolate", () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> FALLING_CHOCOLATE =
            PARTICLE_TYPES.register("falling_chocolate", () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> LANDING_CHOCOLATE =
            PARTICLE_TYPES.register("landing_chocolate", () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> POPCORN_POPS =
            PARTICLE_TYPES.register("popcorn_pops", () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> FLAME_BUNCH =
            PARTICLE_TYPES.register("flame_bunch", () -> new SimpleParticleType(true));


    public static final DeferredHolder<ParticleType<?>, ParticleType<FlavoredColorParticleOption>> FERMENTATION_BUBBLES =
            PARTICLE_TYPES.register("fermentation_bubbles", () -> new ParticleType<FlavoredColorParticleOption>(true) {
                @Override
                public MapCodec<FlavoredColorParticleOption> codec() {
                    return FlavoredColorParticleOption.CODEC;
                }

                @Override
                public StreamCodec<? super RegistryFriendlyByteBuf, FlavoredColorParticleOption> streamCodec() {
                    return FlavoredColorParticleOption.STREAM_CODEC;
                }
            });

    public static void init(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
