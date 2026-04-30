package com.sidden.flavored.registry;

import com.sidden.flavored.Flavored;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class FlavoredBlockTags {

    public static final TagKey<Block> HEAT_SOURCES = create("heat_sources");


    private static TagKey<Block> create(String name) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, name));
    }
}
