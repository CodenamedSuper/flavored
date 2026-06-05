package com.sidden.flavored.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public record MixingBowlData( ResourceLocation textureLocation) {

    public static final Codec<MixingBowlData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("textureLocation").forGetter(MixingBowlData::textureLocation)
    ).apply(instance, MixingBowlData::new));

}
