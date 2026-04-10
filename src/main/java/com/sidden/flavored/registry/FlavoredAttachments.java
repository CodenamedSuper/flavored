package com.sidden.flavored.registry;

import com.mojang.serialization.Codec;
import com.sidden.flavored.Flavored;
import net.neoforged.bus.EventBus;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class FlavoredAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, Flavored.MOD_ID);

    public static final Supplier<AttachmentType<Integer>> CONVERSION_TIME = ATTACHMENT_TYPES.register(
            "conversion_time", () -> AttachmentType.builder(() -> -1).serialize(Codec.INT).build()
    );

    public static void init(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }

}
