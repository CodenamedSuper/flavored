package com.codenamed.flavored.registry;

import com.codenamed.flavored.Flavored;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class FlavoredBlockTags {
    public static final TagKey<Block> SALT_POOL_REPLACEABLE = create("salt_pool_replaceable");


    private static TagKey<Block> create(String name) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, name));
    }
}
