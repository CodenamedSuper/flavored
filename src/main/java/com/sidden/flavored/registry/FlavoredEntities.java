package com.sidden.flavored.registry;

import com.sidden.flavored.Flavored;
import com.sidden.flavored.entity.Chocken;
import com.sidden.flavored.entity.ThrownChocolateEgg;
import com.sidden.flavored.entity.ThrownTomato;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FlavoredEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Flavored.MOD_ID);

    public static final Supplier<EntityType<Chocken>> CHOCKEN =
            ENTITY_TYPES.register("chocken", () -> EntityType.Builder.of(Chocken::new, MobCategory.CREATURE)
                    .sized(0.4F, 0.7F).eyeHeight(0.644F).build("chocken"));


    public static final Supplier<EntityType<ThrownTomato>> TOMATO =
            ENTITY_TYPES.register("tomato",
                    () -> EntityType.Builder
                            .<ThrownTomato>of(ThrownTomato::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(4)
                            .updateInterval(10)
                            .build("tomato"));

    public static final Supplier<EntityType<ThrownChocolateEgg>> CHOCOLATE_EGG =
            ENTITY_TYPES.register("chocolate_egg",
                    () -> EntityType.Builder
                            .<ThrownChocolateEgg>of(ThrownChocolateEgg::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(4)
                            .updateInterval(10)
                            .build("chocolate_egg"));


    public static void init(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}