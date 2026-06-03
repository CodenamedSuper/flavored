package com.sidden.flavored.recipe.recipe_book;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

public enum KegRecipeBookTab implements StringRepresentable
{
    FOODS("foods");

    public static final Codec<KegRecipeBookTab> CODEC = Codec.STRING.flatXmap(s -> {
        KegRecipeBookTab tab = findByName(s);
        if (tab == null) {
            return DataResult.error(() -> "Optional field 'recipe_book_tab' does not match any valid tab. If defined, must be one of the following: " + EnumSet.allOf(KegRecipeBookTab.class));
        }
        return DataResult.success(tab);
    }, tab -> DataResult.success(tab.toString()));

    public final String name;

    KegRecipeBookTab(String name) {
        this.name = name;
    }

    public static KegRecipeBookTab findByName(String name) {
        for (KegRecipeBookTab value : values()) {
            if (value.name.equals(name)) {
                return value;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }
}