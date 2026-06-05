package com.sidden.flavored.datagen;

import com.sidden.flavored.Flavored;
import com.sidden.flavored.data.MixingBowlData;
import com.sidden.flavored.registry.FlavoredDataMapTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.DataMapProvider;

import java.util.concurrent.CompletableFuture;

public class FlavoredDataMapProvider extends DataMapProvider {
    public FlavoredDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }


    @Override
    protected void gather(HolderLookup.Provider provider) {
        super.gather(provider);

        // We create a builder for the EXAMPLE_DATA data map and add our entries using #add.
        builder(FlavoredDataMapTypes.MIXING_BOWL_DATA_MAP)
                .add(Items.WATER_BUCKET.getDefaultInstance().getItemHolder(), new MixingBowlData( ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, "textures/entity/mixing_liquid/water.png")), false)
                .add(Items.MILK_BUCKET.getDefaultInstance().getItemHolder(), new MixingBowlData( ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, "textures/entity/mixing_liquid/milk.png")), false);

    }
}