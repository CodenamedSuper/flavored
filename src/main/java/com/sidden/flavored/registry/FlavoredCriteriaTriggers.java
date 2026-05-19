package com.sidden.flavored.registry;

import com.sidden.flavored.Flavored;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FlavoredCriteriaTriggers extends CriteriaTriggers {
    public static final DeferredRegister<CriterionTrigger<?>> CRITERIA_TRIGGERS = DeferredRegister.create(BuiltInRegistries.TRIGGER_TYPES, Flavored.MOD_ID);

    public static void init(IEventBus eventBus) {

        CRITERIA_TRIGGERS.register(eventBus);
    }


}