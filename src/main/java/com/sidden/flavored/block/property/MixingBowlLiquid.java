package com.sidden.flavored.block.property;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;

import java.util.Arrays;

public enum MixingBowlLiquid implements StringRepresentable {
    NONE("none"),
    WATER("water"),
    MILK("milk");

    private final String name;

    MixingBowlLiquid(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public static final Codec<MixingBowlLiquid> CODEC =
            StringRepresentable.fromEnum(MixingBowlLiquid::values);

    public static final StreamCodec<ByteBuf, MixingBowlLiquid> STREAM_CODEC =
            ByteBufCodecs.STRING_UTF8.map(
                    name -> Arrays.stream(MixingBowlLiquid.values())
                            .filter(v -> v.getSerializedName().equals(name))
                            .findFirst()
                            .orElse(NONE),
                    MixingBowlLiquid::getSerializedName
            );
}
