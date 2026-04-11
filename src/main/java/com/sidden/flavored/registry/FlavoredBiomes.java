package com.sidden.flavored.registry;

import com.sidden.flavored.Flavored;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class FlavoredBiomes {
    public static final ResourceKey<Biome> SALT_CAVES = ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, "salt_caves"));

}
