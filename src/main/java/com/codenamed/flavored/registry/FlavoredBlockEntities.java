package com.codenamed.flavored.registry;

import com.codenamed.flavored.Flavored;
import com.codenamed.flavored.block_entity.FermenterBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FlavoredBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Flavored.MOD_ID);

    public static final Supplier<BlockEntityType<FermenterBlockEntity>> FERMENTER = BLOCK_ENTITIES.register("fermenter",
            () -> BlockEntityType.Builder.of(FermenterBlockEntity::new, FlavoredBlocks.FERMENTER.get()).build(null));
}
