package com.sidden.flavored.registry;

import com.mojang.serialization.Codec;
import com.sidden.flavored.Flavored;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class FlavoredDataAttachments {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Flavored.MOD_ID);

    public static final Supplier<AttachmentType<Integer>> CHOCOLATE_ADDICTION = ATTACHMENT_TYPES.register(
            "chocolate_addiction", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build());

    public static final Supplier<AttachmentType<Long>> LAST_DRAIN_TICK = ATTACHMENT_TYPES.register(
            "last_drain_tick", () -> AttachmentType.builder(() -> 0L).serialize(Codec.LONG).build());

    public static void init(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);

    }
}
