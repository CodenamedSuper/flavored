package com.sidden.flavored.registry;

import com.sidden.flavored.Flavored;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FlavoredSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Flavored.MOD_ID);

    public static DeferredHolder<SoundEvent, SoundEvent> registerVariableSound(String id) {

        return SOUND_EVENTS.register(id, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, id)));
    }

    public static DeferredHolder<SoundEvent, SoundEvent> registerFixedSound(String id, int range) {

        return SOUND_EVENTS.register(id, () -> SoundEvent.createFixedRangeEvent(ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, id), range));
    }

    public static void init(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
