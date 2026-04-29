package com.sidden.flavored.registry;

import com.sidden.flavored.Flavored;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.util.DeferredSoundType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FlavoredSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Flavored.MOD_ID);

    public static final Supplier<SoundEvent> CHOCOLATE_BREAK = register("block.chocolate.break");
    public static final Supplier<SoundEvent> CHOCOLATE_STEP = register("block.chocolate.step");
    public static final Supplier<SoundEvent> CHOCOLATE_PLACE = register("block.chocolate.place");
    public static final Supplier<SoundEvent> CHOCOLATE_HIT = register("block.chocolate.hit");
    public static final Supplier<SoundEvent> CHOCOLATE_FALL = register("block.chocolate.fall");

    public static final DeferredSoundType CHOCOLATE = new DeferredSoundType(1f, 1f,
            FlavoredSoundEvents.CHOCOLATE_BREAK, FlavoredSoundEvents.CHOCOLATE_STEP, FlavoredSoundEvents.CHOCOLATE_PLACE,
            FlavoredSoundEvents.CHOCOLATE_HIT, FlavoredSoundEvents.CHOCOLATE_FALL);


    private static Supplier<SoundEvent> register(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void init(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
