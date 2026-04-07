package com.sidden.flavored.registry;

import com.sidden.flavored.Flavored;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FlavoredEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Flavored.MOD_ID);

    //public static final Supplier<EntityType<TemplateEntity>> WILDFIRE =
    //        ENTITY_TYPES.register("template", () -> EntityType.Builder.of(TemplateEntity::new, MobCategory.MONSTER)
    //                .sized(0.6F, 1.8F).fireImmune().build("template"));



    public static void init(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}