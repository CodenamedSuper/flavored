package com.sidden.flavored.registry;

import com.sidden.flavored.Flavored;
import com.sidden.flavored.data.MixingBowlData;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.datamaps.DataMapType;

public class FlavoredDataMapTypes {
    public static final DataMapType<Item, MixingBowlData> MIXING_BOWL_DATA_MAP = DataMapType.builder(
                    ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, "mixing_bowl"),
                    Registries.ITEM,
                    MixingBowlData.CODEC
            ).synced(MixingBowlData.CODEC, true)
            .build();
}
