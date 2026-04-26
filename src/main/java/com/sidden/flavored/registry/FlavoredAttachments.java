package com.sidden.flavored.registry;

import com.mojang.serialization.Codec;
import com.sidden.flavored.Flavored;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class FlavoredAttachments {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Flavored.MOD_ID);


    public static void init(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);

    }
}
