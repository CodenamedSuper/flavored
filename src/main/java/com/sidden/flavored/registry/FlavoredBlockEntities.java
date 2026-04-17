package com.sidden.flavored.registry;


import com.sidden.flavored.Flavored;
import com.sidden.flavored.block.entity.KegBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FlavoredBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Flavored.MOD_ID);

    public static final Supplier<BlockEntityType<KegBlockEntity>> KEG = BLOCK_ENTITIES.register("keg",
            () -> BlockEntityType.Builder.of(KegBlockEntity::new, FlavoredBlocks.KEG.get()).build(null));

    public static void init(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
