package com.sidden.flavored.registry;

import com.sidden.flavored.Flavored;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class FlavoredItemTags {

    public static final TagKey<Item> CHOCOLATY = create("chocolaty");
    public static final TagKey<Item> TOMATOES = create("tomatoes");

    private static TagKey<Item> create(String name) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, name));
    }
}
