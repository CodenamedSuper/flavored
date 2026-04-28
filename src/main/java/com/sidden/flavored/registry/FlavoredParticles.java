package com.sidden.flavored.registry;

import com.sidden.flavored.Flavored;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
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

    public static void init(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
