package com.codenamed.flavored.recipe;

import com.codenamed.flavored.registry.FlavoredRecipes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public record FermentingRecipe(Ingredient mainInput, Ingredient fermentingInput, ItemStack output) implements Recipe<FermentingRecipeInput> {


    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(mainInput);
        list.add(fermentingInput);
        return list;
    }

    @Override
    public boolean matches(FermentingRecipeInput fermentingRecipeInput, Level level) {
        if (level.isClientSide()) return false;

        return mainInput.test(fermentingRecipeInput.getItem(0)) && fermentingInput.test(fermentingRecipeInput.getItem(1));
    }

    @Override
    public ItemStack assemble(FermentingRecipeInput fermentingRecipeInput, HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return FlavoredRecipes.FERMENTER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return FlavoredRecipes.FERMENTER_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<FermentingRecipe> {
        public static final MapCodec<FermentingRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(FermentingRecipe::mainInput),
                Ingredient.CODEC_NONEMPTY.fieldOf("fermenter").forGetter(FermentingRecipe::fermentingInput),
                ItemStack.CODEC.fieldOf("result").forGetter(FermentingRecipe::output)
        ).apply(inst, FermentingRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, FermentingRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, FermentingRecipe::mainInput,
                        Ingredient.CONTENTS_STREAM_CODEC, FermentingRecipe::fermentingInput,
                        ItemStack.STREAM_CODEC, FermentingRecipe::output,
                        FermentingRecipe::new);

        @Override
        public MapCodec<FermentingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, FermentingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
