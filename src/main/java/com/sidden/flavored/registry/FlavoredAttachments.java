package com.sidden.flavored.registry;

import com.mojang.serialization.Codec;
import com.sidden.flavored.Flavored;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class FlavoredAttachments {
    // Create the DeferredRegister for attachment types
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Flavored.MOD_ID);

    private static final Supplier<AttachmentType<Integer>> SPICE_TOLERANCE = ATTACHMENT_TYPES.register(
            "spice_tolerance", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build()
    );


    public static void init(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);

    }
}
